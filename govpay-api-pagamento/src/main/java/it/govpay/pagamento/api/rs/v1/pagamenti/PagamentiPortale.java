package it.govpay.pagamento.api.rs.v1.pagamenti;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.govpay.rs.v1.BaseRsServiceV1;
import it.govpay.rs.v1.controllers.base.PagamentiController;
import it.govpay.rs.v1.costanti.Costanti;

@Path("/pagamenti")
public class PagamentiPortale extends BaseRsServiceV1{
	
	private PagamentiController controller = null;
	
	public PagamentiPortale() {
		super("pagamentiPortale");
		this.controller = new PagamentiController(this.nomeServizio,this.log);
	}

	@POST
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response inserisciPagamenti(InputStream is , @Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @QueryParam("idSessionePortale") String idSessionePortale) {
		return this.controller.pagamentiPOST(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, is, idSessionePortale);
	}
	
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public Response get(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders,
			@QueryParam(value=Costanti.PARAMETRO_PAGINA) @DefaultValue(value="1") int pagina,
			@QueryParam(value=Costanti.PARAMETRO_RISULTATI_PER_PAGINA) @DefaultValue(value="25") int risultatiPerPagina,@QueryParam("idSessionePortale") String idSessionePortale,
			@QueryParam("stato") String stato,@QueryParam("versante") String versante,@QueryParam("ordinamento") String ordinamento, @QueryParam("campi") String campi) {
		return this.controller.pagamentiGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, pagina, risultatiPerPagina, ordinamento, campi, stato, versante, idSessionePortale);
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPagamentoPortaleById(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders, @PathParam("id") String id) {
		return this.controller.pagamentiIdGET(this.getPrincipal(), this.getListaRuoli(), uriInfo, httpHeaders, id);
	}
}
