package it.govpay.bd.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.openspcoop2.utils.serialization.IDeserializer;
import org.openspcoop2.utils.serialization.IOException;
import org.openspcoop2.utils.serialization.ISerializer;
import org.openspcoop2.utils.serialization.SerializationConfig;
import org.openspcoop2.utils.serialization.SerializationFactory;
import org.openspcoop2.utils.serialization.SerializationFactory.SERIALIZATION_TYPE;

import it.govpay.bd.model.eventi.DatiPagoPA;
import it.govpay.bd.model.eventi.DettaglioRichiesta;
import it.govpay.bd.model.eventi.DettaglioRisposta;
import it.govpay.core.utils.SimpleDateFormatUtils;

public class Evento extends it.govpay.model.Evento{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum TipoEventoCooperazione {
		nodoInviaRPT,
		nodoInviaCarrelloRPT, 
		nodoChiediStatoRPT, 
		paaInviaRT, 
		nodoChiediCopiaRT, 
		paaVerificaRPT, 
		paaAttivaRPT,
		nodoInviaRichiestaStorno,
		paaInviaEsitoStorno,
		nodoInviaAvvisoDigitale;
	}

	public static final String COMPONENTE_COOPERAZIONE = "FESP";
	public static final String NDP = "NodoDeiPagamentiSPC";

	public Evento() {
		super();
	}

	// Business
//
//	private transient Versamento versamento;
//	private transient PagamentoPortale pagamentoPortale;
//	private transient Rpt rpt;
//
//	public void setVersamento(Versamento versamento) {
//		this.versamento = versamento;
//		if(versamento != null && versamento.getId() != null)
//			this.setIdVersamento(versamento.getId());
//	}
//
//	public Versamento getVersamento(BasicBD bd) throws ServiceException {
//		if(this.versamento == null && this.getIdVersamento() != null && bd != null) {
//			VersamentiBD versamentiBD = new VersamentiBD(bd);
//			this.versamento = versamentiBD.getVersamento(this.getIdVersamento());
//		}
//		return this.versamento;
//	}
//
//	public PagamentoPortale getPagamentoPortale(BasicBD bd) throws ServiceException {
//		if(this.pagamentoPortale == null && this.getIdPagamentoPortale() != null && bd != null) {
//			PagamentiPortaleBD pagamentiPortaleBD = new PagamentiPortaleBD(bd);
//			try {
//				this.pagamentoPortale = pagamentiPortaleBD.getPagamento(this.getIdPagamentoPortale());
//			}catch (NotFoundException e) {
//			}
//		}
//		return pagamentoPortale;
//	}
//
//	public void setPagamentoPortale(PagamentoPortale pagamentoPortale) {
//		this.pagamentoPortale = pagamentoPortale;
//		if(pagamentoPortale != null && pagamentoPortale.getId() != null)
//			this.setIdPagamentoPortale(pagamentoPortale.getId());
//	}
//
//	public void setPagamentoPortale(Rpt rpt) {
//		this.rpt = rpt;
//		if(rpt != null && rpt.getId() != null)
//			this.setIdRpt(rpt.getId());
//	}
//
//	public Rpt getRpt(BasicBD bd) throws ServiceException {
//		if(this.rpt == null && this.getIdRpt() != null && bd != null) {
//			RptBD rptBD = new RptBD(bd);
//			this.rpt = rptBD.getRpt(this.getIdRpt());
//		}
//		return rpt;
//	}

	private DettaglioRichiesta dettaglioRichiesta;
	private DettaglioRisposta dettaglioRisposta;
	private DatiPagoPA datiPagoPA;


	public DettaglioRichiesta getDettaglioRichiesta() {
		if(this.dettaglioRichiesta == null) {
			try {
				this.dettaglioRichiesta = this.getDettaglioObject(this.getParametriRichiesta(), DettaglioRichiesta.class);
			}catch (IOException e) {
			}
		}

		return dettaglioRichiesta;
	}

	public void setDettaglioRichiesta(DettaglioRichiesta dettaglioRichiesta) {
		this.dettaglioRichiesta = dettaglioRichiesta;
	}

	public DettaglioRisposta getDettaglioRisposta() {
		if(this.dettaglioRisposta == null) {
			try {
				this.dettaglioRisposta = this.getDettaglioObject(this.getParametriRisposta(), DettaglioRisposta.class);
			}catch (IOException e) {
			}
		}

		return dettaglioRisposta;
	}

	public void setDettaglioRisposta(DettaglioRisposta dettaglioRisposta) {
		this.dettaglioRisposta = dettaglioRisposta;
	}

	public DatiPagoPA getPagoPA() {
		if(this.datiPagoPA == null) {
			try {
				this.datiPagoPA = this.getDettaglioObject(this.getDatiPagoPA(), DatiPagoPA.class);
			}catch (IOException e) {
			}
		}

		return datiPagoPA;
	}

	public void setDatiPagoPA(DatiPagoPA datiPagoPA) {
		this.datiPagoPA = datiPagoPA;
	}

	public <T> T getDettaglioObject(String json, Class<T> tClass) throws IOException {
		if(json != null && tClass != null) {
			SerializationConfig serializationConfig = new SerializationConfig();
			serializationConfig.setDf(SimpleDateFormatUtils.newSimpleDateFormatDataOreMinutiSecondi());
			serializationConfig.setIgnoreNullValues(true);
			IDeserializer deserializer = SerializationFactory.getDeserializer(SERIALIZATION_TYPE.JSON_JACKSON, serializationConfig);
			return tClass.cast(deserializer.getObject(json, tClass));
		}

		return null;
	}

	public String getDettaglioAsString(Object obj) throws IOException {
		if(obj != null) {
			SerializationConfig serializationConfig = new SerializationConfig();
			serializationConfig.setExcludes(Arrays.asList("jsonIdFilter"));
			serializationConfig.setDf(SimpleDateFormatUtils.newSimpleDateFormatDataOreMinutiSecondi());
			ISerializer serializer = SerializationFactory.getSerializer(SERIALIZATION_TYPE.JSON_JACKSON, serializationConfig);
			return serializer.getObject(obj); 
		}
		return null;
	}

	public <T> T getDettaglioObject(byte [] objBytes, Class<T> tClass) throws IOException {
		if(objBytes != null && tClass != null) {
			SerializationConfig serializationConfig = new SerializationConfig();
			serializationConfig.setDf(SimpleDateFormatUtils.newSimpleDateFormatDataOreMinutiSecondi());
			serializationConfig.setIgnoreNullValues(true);
			IDeserializer deserializer = SerializationFactory.getDeserializer(SERIALIZATION_TYPE.JSON_JACKSON, serializationConfig);
			try (ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);){
				return tClass.cast(deserializer.readObject(bais, tClass));
			} catch (java.io.IOException e) {
				throw new IOException(e);
			}  
		}

		return null;
	}

	public byte [] getDettaglioAsBytes(Object obj) throws IOException {
		if(obj != null) {
			SerializationConfig serializationConfig = new SerializationConfig();
			serializationConfig.setExcludes(Arrays.asList("jsonIdFilter"));
			serializationConfig.setDf(SimpleDateFormatUtils.newSimpleDateFormatDataOreMinutiSecondi());
			ISerializer serializer = SerializationFactory.getSerializer(SERIALIZATION_TYPE.JSON_JACKSON, serializationConfig);
			try(ByteArrayOutputStream baos = new ByteArrayOutputStream();){
				serializer.writeObject(obj, baos);
				return baos.toByteArray();
			} catch (java.io.IOException e) {
				throw new IOException(e);
			}  
		}
		return null;
	}
	
	
}
