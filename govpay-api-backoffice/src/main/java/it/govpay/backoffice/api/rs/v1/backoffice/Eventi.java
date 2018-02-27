package it.govpay.backoffice.api.rs.v1.backoffice;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.govpay.rs.v1.BaseRsServiceV1;
import it.govpay.rs.v1.controllers.base.EventiController;
import it.govpay.rs.v1.costanti.Costanti;


@Path("/eventi")

public class Eventi extends BaseRsServiceV1{


	private EventiController controller = null;

	public Eventi() {
		super("eventi");
		this.controller = new EventiController(this.nomeServizio,this.log);
	}




	@GET
	@Path("/")

	@Produces({ "application/json" })
	public Response eventiGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @QueryParam(value=Costanti.PARAMETRO_PAGINA) @DefaultValue(value="1") int pagina,
			@QueryParam(value=Costanti.PARAMETRO_RISULTATI_PER_PAGINA) @DefaultValue(value="25") int risultatiPerPagina, @QueryParam("idDominio") String idDominio, @QueryParam("iuv") String iuv){
		this.controller.setRequestResponse(this.request, this.response);
		return this.controller.eventiGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, idDominio, iuv);
	}


}