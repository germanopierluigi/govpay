/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2015 Link.it srl (http://www.link.it).
 * Copyright (c) 2014-2015 TAS S.p.A. (http://www.tasgroup.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package it.govpay.ndp.model;

public abstract class DocumentoModel {
	
	public enum TipoDocumento {
	/** Richiesta Pagamento Telematico*/ RP, 
	/** Ricevuta Telematica */           RT,
	/** Richiesta Revoca */              RR,
	/** Esito Revoca */             	 ER,
	/** Flusso Rendicontazione */        FR;
	}
	
	private String idDominio;
	private TipoDocumento tipoDocumento;
	private byte[] bytes;
	
	protected DocumentoModel(TipoDocumento tipoDocumento, String idDominio, byte[] bytes) {
		this.setIdDominio(idDominio);
		this.tipoDocumento = tipoDocumento;
		this.bytes = bytes;
	}
	
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	
}
