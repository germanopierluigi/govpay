package it.govpay.core.business;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

import it.govpay.bd.BasicBD;
import it.govpay.bd.model.Dominio;
import it.govpay.bd.model.SingoloVersamento;
import it.govpay.bd.model.Versamento;
import it.govpay.bd.pagamento.StampeBD;
import it.govpay.bd.pagamento.filters.StampaFilter;
import it.govpay.core.business.model.ListaAvvisiDTO;
import it.govpay.core.business.model.ListaAvvisiDTOResponse;
import it.govpay.core.business.model.PrintAvvisoDTO;
import it.govpay.core.business.model.PrintAvvisoDTOResponse;
import it.govpay.core.exceptions.GovPayException;
import it.govpay.core.utils.IuvUtils;
import it.govpay.model.Anagrafica;
import it.govpay.model.IbanAccredito;
import it.govpay.model.Stampa;
import it.govpay.model.Stampa.TIPO;
import it.govpay.stampe.model.AvvisoPagamentoInput;
import it.govpay.stampe.pdf.avvisoPagamento.AvvisoPagamentoCostanti;
import it.govpay.stampe.pdf.avvisoPagamento.AvvisoPagamentoPdf;
import it.govpay.stampe.pdf.avvisoPagamento.utils.AvvisoPagamentoProperties;

public class AvvisoPagamento extends BasicBD {


	private SimpleDateFormat sdfDataScadenza = new SimpleDateFormat("dd/MM/yyyy");
	private static Logger log = LoggerWrapperFactory.getLogger(AvvisoPagamento.class);

	public AvvisoPagamento(BasicBD basicBD) {
		super(basicBD);
	}

	public void cancellaAvviso(Versamento versamento) throws GovPayException {
		try {
			log.debug("Delete Avviso Pagamento per la pendenza [IDA2A: " + versamento.getApplicazione(this).getCodApplicazione() 
					+" | Id: " + versamento.getCodVersamentoEnte() + "]");

			StampeBD avvisiBD = new StampeBD(this);
			avvisiBD.cancellaAvviso(versamento.getId());
		} catch (ServiceException e) {
			log.error("Delete Avviso Pagamento fallito", e);
			throw new GovPayException(e);
		} catch (NotFoundException e) {
		}
	}


	public ListaAvvisiDTOResponse getAvvisi(ListaAvvisiDTO listaAvvisi) throws ServiceException {
		ListaAvvisiDTOResponse response = new ListaAvvisiDTOResponse();

		StampeBD avvisiBD = new StampeBD(this);
		StampaFilter filter = avvisiBD.newFilter();
		filter.setTipo(TIPO.AVVISO.toString());
		filter.setOffset(listaAvvisi.getOffset());
		filter.setLimit(listaAvvisi.getLimit());
		filter.setIdVersamento(listaAvvisi.getVersamento().getId());

		List<Stampa> avvisi = avvisiBD.findAll(filter);
		response.setAvvisi(avvisi);

		return response;
	}

	public PrintAvvisoDTOResponse printAvviso(PrintAvvisoDTO printAvviso) throws ServiceException{
		PrintAvvisoDTOResponse response = new PrintAvvisoDTOResponse();

		StampeBD avvisiBD = new StampeBD(this);
		Stampa avviso = null;
		try {
			log.debug("Lettura PDF Avviso Pagamento Pendenza [IDA2A: " + printAvviso.getVersamento().getApplicazione(this).getCodApplicazione() 
					+" | IdPendenza: " + printAvviso.getVersamento().getCodVersamentoEnte() + "]");
			avviso = avvisiBD.getAvviso(printAvviso.getVersamento().getId());
		}catch (NotFoundException e) {
		}

		// se non c'e' allora vien inserito
		if(avviso == null) {
			try {
				log.debug("Creazione PDF Avviso Pagamento [Dominio: " + printAvviso.getCodDominio() +" | IUV: " + printAvviso.getIuv() + "]");
				AvvisoPagamentoInput input = this.fromVersamento(printAvviso.getVersamento());
				AvvisoPagamentoProperties avProperties = AvvisoPagamentoProperties.getInstance();

				byte[]  pdfBytes = AvvisoPagamentoPdf.getInstance().creaAvviso(log, input, printAvviso.getCodDominio(), avProperties);

				avviso = new Stampa();
				avviso.setDataCreazione(new Date());
				avviso.setIdVersamento(printAvviso.getVersamento().getId());
				avviso.setTipo(TIPO.AVVISO);
				avviso.setPdf(pdfBytes);
				avvisiBD.insertStampa(avviso);
				log.debug("Salvataggio PDF Avviso Pagamento [Dominio: " + printAvviso.getCodDominio() +" | IUV: " + printAvviso.getIuv() + "] sul db completato.");
			} catch (Exception e) {
				log.error("Creazione Pdf Avviso Pagamento fallito", e);
			}
		} else if(printAvviso.isUpdate()) { // se ho fatto l'update della pendenza allora viene aggiornato
			try {
				log.debug("Aggiornamento PDF Avviso Pagamento [Dominio: " + printAvviso.getCodDominio() +" | IUV: " + printAvviso.getIuv() + "]");
				AvvisoPagamentoInput input = this.fromVersamento(printAvviso.getVersamento());
				AvvisoPagamentoProperties avProperties = AvvisoPagamentoProperties.getInstance();

				byte[]  pdfBytes = AvvisoPagamentoPdf.getInstance().creaAvviso(log, input, printAvviso.getCodDominio(), avProperties);

				avviso.setDataCreazione(new Date());
				avviso.setPdf(pdfBytes);
				avvisiBD.updatePdfStampa(avviso);
				log.debug("Aggiornamento PDF Avviso Pagamento [Dominio: " + printAvviso.getCodDominio() +" | IUV: " + printAvviso.getIuv() + "] sul db completato.");
			} catch (Exception e) {
				log.error("Aggiornamento Pdf Avviso Pagamento fallito", e);
			}
		}

		response.setAvviso(avviso);
		return response;
	}

	public AvvisoPagamentoInput fromVersamento(it.govpay.bd.model.Versamento versamento) throws ServiceException {
		AvvisoPagamentoInput input = new AvvisoPagamentoInput();

		Dominio dominio = this.impostaAnagraficaEnteCreditore(versamento, input);

		this.impostaAnagraficaDebitore(versamento, input);

		it.govpay.core.business.model.Iuv iuvGenerato = IuvUtils.toIuv(versamento, versamento.getApplicazione(this), versamento.getUo(this).getDominio(this));

		List<SingoloVersamento> singoliVersamenti = versamento.getSingoliVersamenti(this);
		SingoloVersamento sv = singoliVersamenti.get(0);

		String causaleVersamento = "";
		if(versamento.getCausaleVersamento() != null) {
			try {
				causaleVersamento = versamento.getCausaleVersamento().getSimple();
				input.setOggettoDelPagamento(causaleVersamento);

				if(causaleVersamento.length() > AvvisoPagamentoCostanti.AVVISO_LUNGHEZZA_CAMPO_CAUSALE) {
					String causaleTroncata = causaleVersamento.substring(0, AvvisoPagamentoCostanti.AVVISO_LUNGHEZZA_CAMPO_CAUSALE);
					input.setOggettoDelPagamentoRata(causaleTroncata);
					input.setOggettoDelPagamentoBollettino(causaleTroncata);
				} else {

					input.setOggettoDelPagamentoRata(causaleVersamento);
					input.setOggettoDelPagamentoBollettino(causaleVersamento);
				}
			}catch (UnsupportedEncodingException e) {
				throw new ServiceException(e);
			}
		}

		IbanAccredito postale = null;

		if(sv.getIbanAccredito(this) != null && sv.getIbanAccredito(this).isPostale())
			postale = sv.getIbanAccredito(this);
		else if(sv.getIbanAppoggio(this) != null && sv.getIbanAppoggio(this).isPostale())
			postale = sv.getIbanAppoggio(this);


		if(postale != null) {
			input.setDiPoste(AvvisoPagamentoCostanti.DI_POSTE);
			input.setDataMatrix(this.creaDataMatrix(versamento.getNumeroAvviso(), this.getNumeroCCDaIban(postale.getCodIban()), 
					versamento.getImportoTotale().doubleValue(),
					dominio.getCodDominio(),
					input.getCfDestinatario(),
					input.getNomeCognomeDestinatario(),
					causaleVersamento));
			input.setNumeroCcPostale(this.getNumeroCCDaIban(postale.getCodIban()));
			input.setIntestatarioContoCorrentePostale(dominio.getRagioneSociale());
			input.setCodiceAvvisoPostale(versamento.getNumeroAvviso()); 
		} else {
			input.setDelTuoEnte(AvvisoPagamentoCostanti.DEL_TUO_ENTE_CREDITORE);
		}

		if(versamento.getImportoTotale() != null)
			input.setImporto(versamento.getImportoTotale().doubleValue());

		if(versamento.getDataValidita() != null)
			input.setData(this.sdfDataScadenza.format(versamento.getDataValidita()));

		if(versamento.getNumeroAvviso() != null) {
			// split del numero avviso a gruppi di 4 cifre
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < versamento.getNumeroAvviso().length(); i++) {
				if(sb.length() > 0 && (i % 4 == 0)) {
					sb.append(" ");
				}

				sb.append(versamento.getNumeroAvviso().charAt(i));
			}

			input.setCodiceAvviso(sb.toString());
		}

		if(iuvGenerato.getQrCode() != null)
			input.setQrCode(new String(iuvGenerato.getQrCode()));

		return input;
	}

	private Dominio impostaAnagraficaEnteCreditore(it.govpay.bd.model.Versamento versamento, AvvisoPagamentoInput input)
			throws ServiceException {
		Dominio dominio = versamento.getUo(this).getDominio(this);
		String codDominio = dominio.getCodDominio();
		Anagrafica anagraficaDominio = dominio.getAnagrafica();

		input.setEnteCreditore(dominio.getRagioneSociale());
		input.setCfEnte(codDominio);
		input.setCbill(dominio.getCbill() != null ? dominio.getCbill()  : " ");

		String infoEnte = null;
		if(anagraficaDominio != null) {
			input.setSettoreEnte(anagraficaDominio.getArea());
			StringBuilder sb = new StringBuilder();

			if(StringUtils.isNotEmpty(anagraficaDominio.getUrlSitoWeb())) {
				sb.append("sito web: ").append(anagraficaDominio.getUrlSitoWeb());
			}

			if(StringUtils.isNotEmpty(anagraficaDominio.getEmail())){
				if(sb.length() > 0)
					sb.append("<br/>");

				sb.append("email: ").append(anagraficaDominio.getEmail());
			}

			if(StringUtils.isNotEmpty(anagraficaDominio.getPec())) {
				if(sb.length() > 0)
					sb.append("<br/>");

				sb.append("PEC: ").append(anagraficaDominio.getPec());
			}

			infoEnte = sb.toString();
		}

		input.setAutorizzazione(dominio.getAutStampaPoste());
		input.setInfoEnte(infoEnte);
		// se e' presente un logo lo inserisco altrimemti verra' caricato il logo di default.
		if(dominio.getLogo() != null && dominio.getLogo().length > 0)
			input.setLogoEnte(new String(dominio.getLogo()));
		return dominio;
	}

	private void impostaAnagraficaDebitore(it.govpay.bd.model.Versamento versamento, AvvisoPagamentoInput input) {
		Anagrafica anagraficaDebitore = versamento.getAnagraficaDebitore();
		if(anagraficaDebitore != null) {
			String indirizzoDebitore = StringUtils.isNotEmpty(anagraficaDebitore.getIndirizzo()) ? anagraficaDebitore.getIndirizzo() : "";
			String civicoDebitore = StringUtils.isNotEmpty(anagraficaDebitore.getCivico()) ? anagraficaDebitore.getCivico() : "";
			String capDebitore = StringUtils.isNotEmpty(anagraficaDebitore.getCap()) ? anagraficaDebitore.getCap() : "";
			String localitaDebitore = StringUtils.isNotEmpty(anagraficaDebitore.getLocalita()) ? anagraficaDebitore.getLocalita() : "";
			String provinciaDebitore = StringUtils.isNotEmpty(anagraficaDebitore.getProvincia()) ? (" (" +anagraficaDebitore.getProvincia() +")" ) : "";
			String indirizzoCivicoDebitore = indirizzoDebitore + " " + civicoDebitore;
			String capCittaDebitore = capDebitore + " " + localitaDebitore + provinciaDebitore;

			String indirizzoDestinatario = indirizzoCivicoDebitore + ",";
			input.setNomeCognomeDestinatario(anagraficaDebitore.getRagioneSociale());
			input.setCfDestinatario(anagraficaDebitore.getCodUnivoco());

			if(indirizzoDestinatario.length() > AvvisoPagamentoCostanti.AVVISO_LUNGHEZZA_CAMPO_INDIRIZZO_DESTINATARIO) {
				input.setIndirizzoDestinatario1(indirizzoDestinatario);
			}else {
				input.setIndirizzoDestinatario1(indirizzoDestinatario);
			}

			if(capCittaDebitore.length() > AvvisoPagamentoCostanti.AVVISO_LUNGHEZZA_CAMPO_INDIRIZZO_DESTINATARIO) {
				input.setIndirizzoDestinatario2(capCittaDebitore);
			}else {
				input.setIndirizzoDestinatario2(capCittaDebitore);
			}
		}
	}

	public String splitString(String start) {
		if(start == null || start.length() <= 4)
			return start;

		int length = start.length();
		int bonusSpace = length / 4;
		int charCount = 0;
		int iteration = 1;
		char [] tmp = new char[length + bonusSpace];

		for (int i = length -1; i >= 0; i --) {
			char c = start.charAt(i);
			tmp[charCount ++] = c;

			if(iteration % 4 == 0) {
				tmp[charCount ++] = ' ';
			}

			iteration ++;
		}
		if(length % 4 == 0)
			charCount --;

		String toRet = new String(tmp, 0, charCount); 
		toRet = StringUtils.reverse(toRet);

		return toRet;
	}


	private String creaDataMatrix(String numeroAvviso, String numeroCC, double importo, String codDominio, String cfDebitore, String denominazioneDebitore, String causale) {

		String importoInCentesimi = getImportoInCentesimi(importo);
		String codeLine = createCodeLine(numeroAvviso, numeroCC, importoInCentesimi);
		//		log.debug("CodeLine ["+codeLine+"] Lunghezza["+codeLine.length()+"]");
		String cfDebitoreFilled = getCfDebitoreFilled(cfDebitore);
		String denominazioneDebitoreFilled = getDenominazioneDebitoreFilled(denominazioneDebitore);
		String causaleFilled = getCausaleFilled(causale);

		String dataMatrix = MessageFormat.format(AvvisoPagamentoCostanti.PATTERN_DATAMATRIX, codeLine, codDominio, cfDebitoreFilled, denominazioneDebitoreFilled, causaleFilled, AvvisoPagamentoCostanti.FILLER_DATAMATRIX);
		//		log.debug("DataMatrix ["+dataMatrix+"] Lunghezza["+dataMatrix.length()+"]"); 
		return dataMatrix;
	}

	private String createCodeLine(String numeroAvviso, String numeroCC, String importoInCentesimi) {
		return MessageFormat.format(AvvisoPagamentoCostanti.PATTERN_CODELINE, numeroAvviso,numeroCC,importoInCentesimi);
	}

	private String fillSx(String start, String charToFillWith, int lunghezza) {
		int iterazioni = lunghezza - start.length();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < iterazioni; i++) {
			sb.append(charToFillWith);
		}
		sb.append(start);

		return sb.toString();
	}

	private String fillDx(String start, String charToFillWith, int lunghezza) {
		int iterazioni = lunghezza - start.length();

		StringBuilder sb = new StringBuilder();

		sb.append(start);
		for (int i = 0; i < iterazioni; i++) {
			sb.append(charToFillWith);
		}

		return sb.toString();
	}

	private String getNumeroCCDaIban(String iban) {
		return iban.substring(iban.length() - 12, iban.length());
	}

	private String getImportoInCentesimi(double importo) {
		int tmpImporto = (int) (importo  * 100);
		String stringImporto = Integer.toString(tmpImporto);

		if(stringImporto.length() == AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_IMPORTO)
			return stringImporto.toUpperCase();

		if(stringImporto.length() > AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_IMPORTO) {
			return stringImporto.substring(0, AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_IMPORTO).toUpperCase();
		}


		return fillSx(stringImporto, "0", AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_IMPORTO).toUpperCase();
	}

	private String getCfDebitoreFilled(String cfDebitore) {
		if(cfDebitore.length() == AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE)
			return cfDebitore.toUpperCase();

		if(cfDebitore.length() > AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE) {
			return cfDebitore.substring(0, AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE).toUpperCase();
		}


		return fillDx(cfDebitore, " ", AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE).toUpperCase();
	}

	/***
	 * numero caratteri denominazione debitore 40
	 * @param denominazioneDebitore
	 * @return
	 */
	private String getDenominazioneDebitoreFilled(String denominazioneDebitore) {
		if(denominazioneDebitore.length() == AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE)
			return denominazioneDebitore.toUpperCase();

		if(denominazioneDebitore.length() > AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE) {
			return denominazioneDebitore.substring(0, AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE).toUpperCase();
		}


		return fillDx(denominazioneDebitore, " ", AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE).toUpperCase();
	}

	/**
	 * numero caratteri del campo causale 110
	 * @param causale
	 * @return
	 */
	private String getCausaleFilled(String causale) {
		if(causale.length() == AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE)
			return causale.toUpperCase();

		if(causale.length() > AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE) {
			return causale.substring(0, AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE).toUpperCase();
		}


		return fillDx(causale, " ", AvvisoPagamentoCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE).toUpperCase();
	}
}
