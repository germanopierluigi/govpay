package it.govpay.rs.v1.beans;

import java.net.URI;
import java.util.List;

public class ListaDomini extends Lista<Dominio> {
	
	public ListaDomini(List<Dominio> domini, URI requestUri, long count, long pagina, long risultatiPerPagina) {
		super(domini, requestUri, count, pagina, risultatiPerPagina);
	}
	
}