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
package it.govpay.bd.model;

import java.util.List;

import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.AnagraficaManager;
import it.govpay.bd.pagamento.PagamentiBD;
import it.govpay.bd.pagamento.PagamentiPortaleBD;
import it.govpay.bd.pagamento.VersamentiBD;
import it.govpay.bd.viste.VersamentiIncassiBD;
import it.govpay.bd.viste.model.VersamentoIncasso;
import it.govpay.bd.viste.model.converter.VersamentoIncassoConverter;
import it.govpay.model.Intermediario;

public class Rpt extends it.govpay.model.Rpt{
	
	private static final long serialVersionUID = 1L;
	 
	// Business
	
	private transient VersamentoIncasso versamento;
	private transient Dominio dominio;
	private transient List<Pagamento> pagamenti;
	private transient PagamentoPortale pagamentoPortale;
	
	public VersamentoIncasso getVersamento(BasicBD bd) throws ServiceException {
		if(this.versamento == null) {
			VersamentiIncassiBD versamentiIncassiBD = new VersamentiIncassiBD(bd);
			try {
				this.versamento = versamentiIncassiBD.getVersamento(this.getIdVersamento());
			}catch(NotFoundException e) { // se non e' stato ancora incassato non verra' trovato  
				VersamentiBD versamentiBD = new VersamentiBD(bd);
				Versamento versamento = versamentiBD.getVersamento(this.getIdVersamento());
				if(versamento != null)
					this.versamento = VersamentoIncassoConverter.fromVersamento(versamento);
			} // puo' essere null perche' nella vista non e' presente
		}
		return this.versamento;
	}

	public void setVersamento(VersamentoIncasso versamento) {
		this.versamento = versamento;
	}

	public Dominio getDominio(BasicBD bd) throws ServiceException {
		if(this.dominio == null) {
			try {
				this.dominio = AnagraficaManager.getDominio(bd, this.getCodDominio());
			} catch (NotFoundException e) {
				throw new ServiceException(e);
			}
		}
		return this.dominio;
	}
	
	public List<Pagamento> getPagamenti(BasicBD bd) throws ServiceException {
		if(this.pagamenti == null) {
			PagamentiBD pagamentiBD = new PagamentiBD(bd);
			this.pagamenti = pagamentiBD.getPagamenti(this.getId());
		}
		return this.pagamenti;
	}
	
	public Pagamento getPagamento(String iur, BasicBD bd) throws ServiceException, NotFoundException {
		List<Pagamento> pagamenti = this.getPagamenti(bd);
		for(Pagamento pagamento : pagamenti) {
			if(pagamento.getIur().equals(iur))
				return pagamento;
		}
		throw new NotFoundException();
	}
	
	public void setPagamenti(List<Pagamento> pagamenti) {
		this.pagamenti = pagamenti;
	}

	public Stazione getStazione(BasicBD bd) throws ServiceException {
		return this.getDominio(bd).getStazione();
	}


	public Intermediario getIntermediario(BasicBD bd) throws ServiceException {
		return this.getDominio(bd).getStazione().getIntermediario(bd);
	}

	public PagamentoPortale getPagamentoPortale(BasicBD bd) throws ServiceException, NotFoundException  {
		if(this.pagamentoPortale == null && this.getIdPagamentoPortale() != null) {
			PagamentiPortaleBD versamentiBD = new PagamentiPortaleBD(bd);
			this.pagamentoPortale = versamentiBD.getPagamento(this.getIdPagamentoPortale());
		}
		return this.pagamentoPortale;
	}
	
	public void setPagamentoPortale(PagamentoPortale pagamentoPortale) {
		this.pagamentoPortale = pagamentoPortale;
	}
}
