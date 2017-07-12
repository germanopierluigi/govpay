package it.agid.pap.ws.rest.rpt;

import it.gov.digitpa.schemas._2011.ws.paa.FaultBean;
import it.gov.digitpa.schemas._2011.ws.paa.NodoInviaRPTRisposta;
import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.AnagraficaManager;
import it.govpay.model.Applicazione;
import it.govpay.model.Portale;
import it.govpay.core.business.Pagamento;
import it.govpay.core.business.model.AvviaTransazioneDTO;
import it.govpay.core.business.model.AvviaTransazioneDTOResponse;
import it.govpay.core.exceptions.GovPayException;
import it.govpay.core.utils.GovpayConfig;
import it.govpay.core.utils.GpContext;
import it.govpay.core.utils.GpThreadLocal;
import it.govpay.servizi.commons.EsitoOperazione;
import it.govpay.servizi.commons.TipoAutenticazione;
import it.govpay.servizi.commons.TipoVersamento;
import it.govpay.servizi.commons.Versamento;
import it.govpay.servizi.commons.Versamento.SingoloVersamento;
import it.govpay.servizi.commons.Versamento.SingoloVersamento.Tributo;
import it.govpay.servizi.commons.TipoContabilita;
import it.govpay.servizi.gpprt.GpAvviaTransazionePagamento;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import it.govpay.servizi.commons.Anagrafica;
import it.govpay.servizi.commons.Canale;
import it.agid.pap.model.DatiSingoloVersamento;
import it.agid.pap.model.RPT;
import it.agid.pap.util.FaultCodes;
import it.agid.pap.util.PapConstants;
import it.agid.pap.ws.rest.BasePapRsService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.utils.logger.beans.Property;

import javax.ws.rs.core.Response.Status;


@Path("/pap/{codDominio}/rpts")
public class RptRestController extends BasePapRsService {
	
	public RptRestController() {
		super("PapRest");
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response papRestInviaRpt(InputStream is, @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @PathParam("codDominio") String codDominio) {

		GpContext ctx = null;

		BasicBD bd = null;
		try {
			
			ByteArrayOutputStream baosRequest = logRequest(uriInfo, httpHeaders,"papRestInviaRpt", is);
			ctx = GpThreadLocal.get();
			JsonConfig jsonConfig = new JsonConfig();
			Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("datiSingoloVersamento", DatiSingoloVersamento.class); 
			jsonConfig.setClassMap(classMap);
			
			JSONObject jsonObject = JSONObject.fromObject( baosRequest.toString() );  
			jsonConfig.setRootClass(RPT.class);
			RPT rpt = (RPT) JSONObject.toBean( jsonObject, jsonConfig );
			
			ctx.log("pap.ricevutaRichiesta");
			
			ctx.getContext().getRequest().addGenericProperty(new Property("codDominio", codDominio));
			
			if(rpt.getDatiVersamento().getIdentificativoUnivocoVersamento() == null || rpt.getDatiVersamento().getIdentificativoUnivocoVersamento().isEmpty()) 
				throw new Exception("IUV non valorizzato");
				
			ctx.getContext().getRequest().addGenericProperty(new Property("iuv", rpt.getDatiVersamento().getIdentificativoUnivocoVersamento()));
			ctx.getContext().getRequest().addGenericProperty(new Property("codPsp", rpt.getIdentificativoPSP()));
			ctx.getContext().getRequest().addGenericProperty(new Property("codCanale", rpt.getIdentificativoCanale()));
			ctx.getContext().getRequest().addGenericProperty(new Property("tipoVersamento", rpt.getDatiVersamento().getTipoVersamento()));
			ctx.getContext().getRequest().addGenericProperty(new Property("codSessionePortale", "--Non fornito--"));
			
			ctx.log("pap.inviaRpt");
			
			
			bd = BasicBD.newInstance(ctx.getTransactionId());

			Portale portale = getPortaleAutenticato(bd);
			Applicazione applicazione = getApplicazioneAutenticata(bd);

			GpAvviaTransazionePagamento richiesta = new GpAvviaTransazionePagamento();
			richiesta.setAggiornaSeEsiste(false);
			richiesta.setAutenticazione(TipoAutenticazione.valueOf(rpt.getAutenticazioneSoggetto()));
			Canale canale = new Canale();
			canale.setCodCanale(rpt.getIdentificativoCanale());
			canale.setCodPsp(rpt.getIdentificativoPSP());
			canale.setTipoVersamento(TipoVersamento.fromValue(rpt.getDatiVersamento().getTipoVersamento()));
			richiesta.setCanale(canale);
			richiesta.setCodPortale(portale.getCodPortale());
			richiesta.setIbanAddebito(rpt.getDatiVersamento().getDatiSingoloVersamento().get(0).getIbanAccredito());
			richiesta.setUrlRitorno(GovpayConfig.getInstance().getBackurlPap());

			if(rpt.getSoggettoVersante() != null) {
				Anagrafica versante = new Anagrafica();
				versante.setRagioneSociale(rpt.getSoggettoVersante().getAnagraficaVersante());
				versante.setCap(rpt.getSoggettoVersante().getCapVersante());
				versante.setCivico(rpt.getSoggettoVersante().getCivicoVersante());
				versante.setEmail(rpt.getSoggettoVersante().getEMailVersante());
				versante.setCodUnivoco(rpt.getSoggettoVersante().getIdentificativoUnivocoVersante().getCodiceIdentificativoUnivoco());
				versante.setIndirizzo(rpt.getSoggettoVersante().getIndirizzoVersante());
				versante.setLocalita(rpt.getSoggettoVersante().getLocalitaVersante());
				versante.setNazione(rpt.getSoggettoVersante().getNazioneVersante());
				versante.setProvincia(rpt.getSoggettoVersante().getProvinciaVersante());
				richiesta.setVersante(versante);
			}

			Versamento versamento = new Versamento();
			versamento.setAggiornabile(false);
			versamento.setAnnoTributario(null);
			versamento.setBundlekey(null);
			versamento.setCausale(null);
			versamento.setCodApplicazione(applicazione.getCodApplicazione());
			versamento.setCodDebito(null);
			versamento.setCodDominio(codDominio);
			versamento.setCodUnitaOperativa(rpt.getEnteBeneficiario().getCodiceUnitOperBeneficiario());
			versamento.setCodVersamentoEnte(rpt.getDatiVersamento().getIdentificativoUnivocoVersamento());
			versamento.setDataScadenza(null);
			
			int index = 1;
			for(DatiSingoloVersamento dsv : rpt.getDatiVersamento().getDatiSingoloVersamento()) {
				SingoloVersamento sv = new SingoloVersamento();
				sv.setCodSingoloVersamentoEnte(rpt.getDatiVersamento().getIdentificativoUnivocoVersamento() + "_" + index);
				sv.setImporto(dsv.getImportoSingoloVersamento());
				Tributo svt = new Tributo();
				svt.setCodContabilita(dsv.getDatiSpecificiRiscossione().substring(2));
				if(dsv.getDatiSpecificiRiscossione().substring(0, 1).equals("0"))
					svt.setTipoContabilita(TipoContabilita.CAPITOLO);
				else if(dsv.getDatiSpecificiRiscossione().substring(0, 1).equals("1"))
					svt.setTipoContabilita(TipoContabilita.SPECIALE);
				else if(dsv.getDatiSpecificiRiscossione().substring(0, 1).equals("2"))
					svt.setTipoContabilita(TipoContabilita.SIOPE);
				else 
					svt.setTipoContabilita(TipoContabilita.ALTRO);
				svt.setIbanAccredito(dsv.getIbanAccredito());
				sv.setTributo(svt);
				versamento.getSingoloVersamento().add(sv);
			}

			Anagrafica debitore = new Anagrafica();
			debitore.setRagioneSociale(rpt.getSoggettoPagatore().getAnagraficaPagatore());
			debitore.setCap(rpt.getSoggettoPagatore().getCapPagatore());
			debitore.setCivico(rpt.getSoggettoPagatore().getCivicoPagatore());
			debitore.setEmail(rpt.getSoggettoPagatore().getEmailPagatore());
			debitore.setCodUnivoco(rpt.getSoggettoPagatore().getIdentificativoUnivocoPagatore().getCodiceIdentificativoUnivoco());
			debitore.setIndirizzo(rpt.getSoggettoPagatore().getIndirizzoPagatore());
			debitore.setLocalita(rpt.getSoggettoPagatore().getLocalitaPagatore());
			debitore.setNazione(rpt.getSoggettoPagatore().getNazionePagatore());
			debitore.setProvincia(rpt.getSoggettoPagatore().getProvinciaPagatore());
			versamento.setDebitore(debitore);

			versamento.setIuv(rpt.getDatiVersamento().getIdentificativoUnivocoVersamento());
			versamento.setImportoTotale(rpt.getDatiVersamento().getImportoTotaleDaVersare());
			richiesta.getVersamentoOrVersamentoRef().add(versamento);

			it.govpay.bd.model.Canale.TipoVersamento tipoVersamento = it.govpay.bd.model.Canale.TipoVersamento.toEnum(canale.getTipoVersamento().toString());
			it.govpay.bd.model.Canale canaleModel = null;
			try {
				canaleModel = AnagraficaManager.getCanale(bd, canale.getCodPsp(), canale.getCodCanale(), tipoVersamento);
			} catch (NotFoundException e) {
				throw new GovPayException(EsitoOperazione.PSP_000, canale.getCodPsp(), canale.getCodCanale(), tipoVersamento.name());
			}

			Pagamento pagamento = new Pagamento(bd);
			
			AvviaTransazioneDTO dto = new AvviaTransazioneDTO();
			dto.setAggiornaSeEsisteB(richiesta.isAggiornaSeEsiste());
			dto.setAutenticazione(richiesta.getAutenticazione().value());
			dto.setCanale(canaleModel);
			dto.setIbanAddebito(richiesta.getIbanAddebito());
			dto.setPortale(portale);
			dto.setUrlRitorno(richiesta.getUrlRitorno());
			dto.setVersamentoOrVersamentoRef(richiesta.getVersamentoOrVersamentoRef());
			dto.setVersante(richiesta.getVersante());
			
			AvviaTransazioneDTOResponse dtoResponse = pagamento.avviaTransazione(dto);
			
			ctx.getContext().getRequest().addGenericProperty(new Property("ccp", dtoResponse.getRifTransazioni().get(0).getCcp()));
			
			NodoInviaRPTRisposta wsResponse = new NodoInviaRPTRisposta();
			wsResponse.setEsito("OK");
			
			if(dtoResponse.getPspRedirectURL() != null) {
				ctx.getContext().getResponse().addGenericProperty(new Property("codPspSession", dtoResponse.getCodSessione()));
				ctx.log("pap.inviaRptOk");
			} else {
				ctx.log("pap.inviaRptOkNoRedirect");
			}
			wsResponse.setRedirect(dtoResponse.getPspRedirectURL() != null ? 1 : 0);
			wsResponse.setUrl(dtoResponse.getPspRedirectURL());

			ctx.log("pap.ricevutaRichiestaOk");
			logResponse(uriInfo, httpHeaders,"papRestInviaRpt", toOutputStream(wsResponse));
			return Response.status(Status.CREATED).entity(wsResponse).build();
		} catch (GovPayException e) {
			e.log(log);
			ctx.log("pap.ricevutaRichiestaKo", e.getCodEsito().toString(), e.getMessage());
			FaultBean fault = new FaultBean();
			fault.setFaultCode(FaultCodes.PAP_UNEXPECTED_ERROR.name());
			fault.setFaultString(e.getMessage());
			logResponse(uriInfo, httpHeaders,"papRestInviaRpt", toOutputStream(fault));
			
			ctx.log("pap.inviaRptKo", fault.getFaultCode(), fault.getFaultString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(fault).build();
		} catch (Exception e) {
			new GovPayException(e).log(log);
			ctx.log("pap.ricevutaRichiestaKo", EsitoOperazione.INTERNAL.toString(), e.getMessage());
			FaultBean fault = new FaultBean();
			fault.setFaultCode(FaultCodes.PAP_UNEXPECTED_ERROR.name());
			fault.setFaultString(e.getMessage());
			logResponse(uriInfo, httpHeaders,"papRestInviaRpt", toOutputStream(fault));
			ctx.log("pap.inviaRptKo", fault.getFaultCode(), fault.getFaultString());
			return Response.status(Status.BAD_REQUEST).entity(fault).build();
		} finally {
			if(ctx != null) {
				ctx.log();
			}
			if(bd != null) bd.closeConnection();
		}
	}

	@POST
	@Path("/pagamentodiretto")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response papRestCaricaPagamentoDiretto(RPT rpt) {
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response papRestChiediStoricoRPT(
			@QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate,
			@QueryParam("idUtente") String idUtente,
			@QueryParam("statoPagamento") String statoPagamento,
			@DefaultValue(PapConstants.LIST_RESULTS_PARAM) @QueryParam("risultati") Integer risultati,
			@DefaultValue(PapConstants.LIST_PAGE_PARAM) @QueryParam("pagina") Integer pagina) {

		return Response.status(Status.BAD_REQUEST)
				.entity("{\"esito\":\"" + PapConstants.ESITO_KO + "\"}")
				.build();
	}

	@PUT
	@Path("/pagamentodiretto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response papRestAnnullaPagamentoDirettoDummy() {

		return Response.status(Status.BAD_REQUEST)
				.entity("{\"esito\":\"" + PapConstants.ESITO_KO + "\"}")
				.build();

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pagamentodiretto/{iuv}")
	public Response papRestAnnullaPagamentoDiretto(@PathParam("iuv") String iuv) {

		return Response.status(Status.BAD_REQUEST)
				.entity("{\"esito\":\"" + PapConstants.ESITO_KO + "\"}")
				.build();
	}
}
