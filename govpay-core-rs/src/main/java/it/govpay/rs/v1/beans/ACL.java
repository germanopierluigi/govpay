/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2017 Link.it srl (http://www.link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package it.govpay.rs.v1.beans;

import java.util.stream.Collectors;

import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter(value="ACL")  
public class ACL extends it.govpay.rs.v1.beans.base.Acl {

	@Override
	public String getJsonIdFilter() {
		return "ACL";
	}
	
	public static ACL parse(String json) {
		return (ACL) parse(json, ACL.class);
	}
	
	public ACL(it.govpay.model.Acl acl) {
		this.principal(acl.getPrincipal())
		.ruolo(acl.getRuolo())
		.servizio(ServizioEnum.fromValue(acl.getServizio().toString()));
		
		if(acl.getListaDiritti() != null)
			this.autorizzazioni(acl.getListaDiritti().stream().map(a -> AutorizzazioniEnum.fromValue(a.getCodifica())).collect(Collectors.toList()));
		
	}

}