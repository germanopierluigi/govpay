package it.govpay.rs.v1.controllers.base;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;

import it.govpay.core.dao.anagrafica.ApplicazioniDAO;
import it.govpay.core.dao.anagrafica.dto.FindApplicazioniDTO;
import it.govpay.core.dao.anagrafica.dto.FindApplicazioniDTOResponse;
import it.govpay.core.dao.anagrafica.dto.GetApplicazioneDTO;
import it.govpay.core.dao.anagrafica.dto.GetApplicazioneDTOResponse;
import it.govpay.core.utils.GpContext;
import it.govpay.core.utils.GpThreadLocal;
import it.govpay.model.Ruolo;
import it.govpay.rs.v1.beans.Applicazione;
import it.govpay.rs.v1.beans.ListaApplicazioni;
import it.govpay.rs.v1.beans.base.FaultBean;
import it.govpay.rs.v1.beans.base.FaultBean.CategoriaEnum;



public class ApplicazioniController extends it.govpay.rs.BaseController {

     public ApplicazioniController(String nomeServizio,Logger log) {
		super(nomeServizio,log);
     }



    public Response applicazioniIdA2AGET(String principal, List<Ruolo> listaRuoli, UriInfo uriInfo, HttpHeaders httpHeaders , String idA2A) {
    	String methodName = "applicazioniIdA2AGET";  
		GpContext ctx = null;
		ByteArrayOutputStream baos= null;
		this.log.info("Esecuzione " + methodName + " in corso..."); 
		try{
			baos = new ByteArrayOutputStream();
			this.logRequest(uriInfo, httpHeaders, methodName, baos);
			
			ctx =  GpThreadLocal.get();
			
			// Parametri - > DTO Input
			
			GetApplicazioneDTO getApplicazioneDTO = new GetApplicazioneDTO(null, idA2A); //TODO IAutorizzato
			
			// INIT DAO
			
			ApplicazioniDAO dominiDAO = new ApplicazioniDAO();
			
			// CHIAMATA AL DAO
			
			GetApplicazioneDTOResponse getApplicazioneDTOResponse = dominiDAO.getApplicazione(getApplicazioneDTO);
			
			// CONVERT TO JSON DELLA RISPOSTA
			
			Applicazione response = new it.govpay.rs.v1.beans.Applicazione(getApplicazioneDTOResponse.getApplicazione());
			
			this.logResponse(uriInfo, httpHeaders, methodName, response.toJSON(null), 200);
			this.log.info("Esecuzione " + methodName + " completata."); 
			return Response.status(Status.OK).entity(response.toJSON(null)).build();
			
		}catch (Exception e) {
			log.error("Errore interno durante la ricerca delle Applicazioni: " + e.getMessage(), e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.INTERNO);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo, 500);
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(respKo).build();
		} finally {
			if(ctx != null) ctx.log();
		}
    }



    public Response applicazioniIdA2APUT(String principal, List<Ruolo> listaRuoli, UriInfo uriInfo, HttpHeaders httpHeaders , String idA2A, java.io.InputStream is) {
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity( "Not implemented" ).build();
    }



    public Response applicazioniGET(String principal, List<Ruolo> listaRuoli, UriInfo uriInfo, HttpHeaders httpHeaders , Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato) {
    	String methodName = "applicazioniGET";  
		GpContext ctx = null;
		ByteArrayOutputStream baos= null;
		this.log.info("Esecuzione " + methodName + " in corso..."); 
		try{
			baos = new ByteArrayOutputStream();
			this.logRequest(uriInfo, httpHeaders, methodName, baos);
			
			ctx =  GpThreadLocal.get();
			
			// Parametri - > DTO Input
			
			FindApplicazioniDTO listaDominiDTO = new FindApplicazioniDTO(null); //TODO IAutorizzato
			
			listaDominiDTO.setPagina(pagina);
			listaDominiDTO.setLimit(risultatiPerPagina);
			listaDominiDTO.setOrderBy(ordinamento);
			listaDominiDTO.setAbilitato(abilitato);
			
			// INIT DAO
			
			ApplicazioniDAO dominiDAO = new ApplicazioniDAO();
			
			// CHIAMATA AL DAO
			
			FindApplicazioniDTOResponse listaApplicazioniDTOResponse = dominiDAO.findApplicazioni(listaDominiDTO);
			
			// CONVERT TO JSON DELLA RISPOSTA
			
			List<it.govpay.rs.v1.beans.Applicazione> results = new ArrayList<it.govpay.rs.v1.beans.Applicazione>();
			for(it.govpay.model.Applicazione applicazione: listaApplicazioniDTOResponse.getResults()) {
				results.add(new it.govpay.rs.v1.beans.Applicazione(applicazione));
			}
			
			ListaApplicazioni response = new ListaApplicazioni(results, uriInfo.getRequestUri(),
					listaApplicazioniDTOResponse.getTotalResults(), pagina, risultatiPerPagina);
			
			this.logResponse(uriInfo, httpHeaders, methodName, response.toJSON(campi), 200);
			this.log.info("Esecuzione " + methodName + " completata."); 
			return Response.status(Status.OK).entity(response.toJSON(campi)).build();
			
		}catch (Exception e) {
			log.error("Errore interno durante la ricerca delle Applicazioni: " + e.getMessage(), e);
			FaultBean respKo = new FaultBean();
			respKo.setCategoria(CategoriaEnum.INTERNO);
			respKo.setCodice("");
			respKo.setDescrizione(e.getMessage());
			try {
				this.logResponse(uriInfo, httpHeaders, methodName, respKo, 500);
			}catch(Exception e1) {
				log.error("Errore durante il log della risposta", e1);
			}
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(respKo).build();
		} finally {
			if(ctx != null) ctx.log();
		}
    }


}

