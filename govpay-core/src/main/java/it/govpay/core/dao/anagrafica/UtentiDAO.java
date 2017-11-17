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
package it.govpay.core.dao.anagrafica;


import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.AnagraficaManager;
import it.govpay.bd.model.Operatore;
import it.govpay.core.exceptions.NotAuthenticatedException;
import it.govpay.core.utils.GpThreadLocal;
import it.govpay.model.Applicazione;
import it.govpay.model.Autorizzato;
import it.govpay.model.IAutorizzato;
import it.govpay.model.Portale;


public class UtentiDAO {

	public enum TipoUtenza {
		PORTALE, OPERATORE, APPLICAZIONE;
	}

	public IAutorizzato getUser(String principal) throws NotAuthenticatedException, ServiceException {
		BasicBD bd = BasicBD.newInstance(GpThreadLocal.get().getTransactionId());
		try {
			Autorizzato user = new Autorizzato();
			boolean autenticated = false;
			try {
				Portale portale = AnagraficaManager.getPortaleByPrincipal(bd, principal);
				user.addAllAcls(portale.getAcls());
				autenticated = true;
			} catch (org.openspcoop2.generic_project.exception.NotFoundException e) { }
		
			try {
				Applicazione applicazione = AnagraficaManager.getApplicazioneByPrincipal(bd, principal);
				user.addAllAcls(applicazione.getAcls());
				autenticated = true;
			} catch (org.openspcoop2.generic_project.exception.NotFoundException e) { }

			try {
				Operatore operatore = AnagraficaManager.getOperatore(bd, principal);
				user.addAllAcls(operatore.getAcls());
				autenticated = true;
			} catch (org.openspcoop2.generic_project.exception.NotFoundException e) { }
			
			if(!autenticated) throw new NotAuthenticatedException();
			
			return user;
		} finally {
			bd.closeConnection();
		}
	}
	
	public Applicazione getApplicazione(String principal) throws NotAuthenticatedException, ServiceException {
		BasicBD bd = BasicBD.newInstance(GpThreadLocal.get().getTransactionId());
		try {
			Applicazione applicazione = AnagraficaManager.getApplicazioneByPrincipal(bd, principal);
			return applicazione;
		} catch (org.openspcoop2.generic_project.exception.NotFoundException e) {
			throw new NotAuthenticatedException();
		} finally {
			bd.closeConnection();
		}
	}
	
	public Operatore getOperatore(String principal) throws NotAuthenticatedException, ServiceException {
		BasicBD bd = BasicBD.newInstance(GpThreadLocal.get().getTransactionId());
		try {
			Operatore operatore = AnagraficaManager.getOperatore(bd, principal);
			return operatore;
		} catch (org.openspcoop2.generic_project.exception.NotFoundException e3) {
			throw new NotAuthenticatedException();
		} finally {
			bd.closeConnection();
		}
	}
	
	public Portale getPortale(String principal) throws NotAuthenticatedException, ServiceException {
		BasicBD bd = BasicBD.newInstance(GpThreadLocal.get().getTransactionId());
		try {
			Portale portale = AnagraficaManager.getPortaleByPrincipal(bd, principal);
			return portale;
		} catch (org.openspcoop2.generic_project.exception.NotFoundException e) {
			throw new NotAuthenticatedException();
		} finally {
			bd.closeConnection();
		}
	}
}


