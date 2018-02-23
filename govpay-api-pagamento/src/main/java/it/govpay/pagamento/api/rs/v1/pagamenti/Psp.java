package it.govpay.pagamento.api.rs.v1.pagamenti;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.govpay.rs.v1.beans.base.ModelloPagamento;
import it.govpay.rs.v1.beans.base.TipoVersamento;
import it.govpay.rs.v1.controllers.base.PspController;



@Path("/psp")

public class Psp extends it.govpay.rs.v1.BaseRsServiceV1 {


	private PspController controller = null;

	public Psp() {
		super("psp");
		this.controller = new PspController(this.nomeServizio,this.log);
	}


/*

    @GET
    
    @Path("/")


    public Response pspGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato, Boolean bollo, Boolean storno){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, campi, abilitato, bollo, storno);
    }


    @GET
    
    @Path("/")


    public Response pspGET_1(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato, Boolean bollo, Boolean storno){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspGET_1(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, campi, abilitato, bollo, storno);
    }


    @GET
    @Path("/{idPsp}/canali")
    


    public Response pspIdPspCanaliGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato, ModelloPagamento modello, TipoVersamento abilitato2){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspIdPspCanaliGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, campi, abilitato, modello, abilitato2);
    }


    @GET
    @Path("/{idPsp}/canali")
    


    public Response pspIdPspCanaliGET_2(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, Integer pagina, Integer risultatiPerPagina, String ordinamento, String campi, Boolean abilitato, ModelloPagamento modello, TipoVersamento abilitato2){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspIdPspCanaliGET_2(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, campi, abilitato, modello, abilitato2);
    }


    @GET
    @Path("/{idPsp}/canali/{idCanale}")
    


    public Response pspIdPspCanaliIdCanaleGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String idPsp, String idCanale){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspIdPspCanaliIdCanaleGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders,  idPsp,  idCanale);
    }


    @GET
    @Path("/{idPsp}/canali/{idCanale}")
    


    public Response pspIdPspCanaliIdCanaleGET_3(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String idPsp, String idCanale){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspIdPspCanaliIdCanaleGET_3(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders,  idPsp,  idCanale);
    }


    @GET
    @Path("/{idPsp}")
    


    public Response pspIdPspGET(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String idPsp){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspIdPspGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders,  idPsp);
    }


    @GET
    @Path("/{idPsp}")
    


    public Response pspIdPspGET_4(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, String idPsp){
        this.controller.setRequestResponse(this.request, this.response);
	return this.controller.pspIdPspGET_4(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders,  idPsp);
    }
*/
}


