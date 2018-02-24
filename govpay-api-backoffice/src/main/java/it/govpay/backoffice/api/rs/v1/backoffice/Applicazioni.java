package it.govpay.backoffice.api.rs.v1.backoffice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.govpay.rs.v1.BaseRsServiceV1;
import it.govpay.rs.v1.controllers.base.ApplicazioniController;


@Path("/applicazioni")

public class Applicazioni extends BaseRsServiceV1{


	private ApplicazioniController controller = null;

	public Applicazioni() {
		super("applicazioni");
		this.controller = new ApplicazioniController(this.nomeServizio,this.log);
	}




    @GET
    @Path("/")
    
    @Produces({ "application/json" })
    public Response applicazioniGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @QueryParam("pagina") Integer pagina, @QueryParam("risultatiPerPagina") Integer risultatiPerPagina, @QueryParam("ordinamento") String ordinamento, @QueryParam("campi") String campi, @QueryParam("abilitato") Boolean abilitato){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.applicazioniGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, campi, abilitato);
    }

    
    @GET
    @Path("/{idA2A}")
    
    @Produces({ "application/json" })
    public Response applicazioniIdA2AGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @PathParam("idA2A") String idA2A){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.applicazioniIdA2AGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders,  idA2A);
    }

    
    @PUT
    @Path("/{idA2A}")
    @Consumes({ "application/json" })
    
    public Response applicazioniIdA2APUT(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @PathParam("idA2A") String idA2A, java.io.InputStream is){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.applicazioniIdA2APUT(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders,  idA2A, is);
    }

    
}
