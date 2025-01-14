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
package it.govpay.core.utils.thread;


import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.logger.beans.Property;
import org.openspcoop2.utils.service.context.ContextThreadLocal;
import org.openspcoop2.utils.service.context.IContext;
import org.openspcoop2.utils.service.context.MD5Constants;
import org.slf4j.Logger;
import org.slf4j.MDC;

import gov.telematici.pagamenti.ws.rpt.FaultBean;
import gov.telematici.pagamenti.ws.rpt.NodoInviaRPT;
import it.govpay.bd.BasicBD;
import it.govpay.bd.anagrafica.AnagraficaManager;
import it.govpay.bd.configurazione.model.Giornale;
import it.govpay.bd.model.Applicazione;
import it.govpay.bd.model.Notifica;
import it.govpay.bd.model.PagamentoPortale;
import it.govpay.bd.model.Rpt;
import it.govpay.bd.model.Stazione;
import it.govpay.bd.model.Versamento;
import it.govpay.bd.pagamento.RptBD;
import it.govpay.core.beans.EsitoOperazione;
import it.govpay.core.business.GiornaleEventi;
import it.govpay.core.business.model.Risposta;
import it.govpay.core.exceptions.GovPayException;
import it.govpay.core.utils.EventoContext.Esito;
import it.govpay.core.utils.GpContext;
import it.govpay.core.utils.RptUtils;
import it.govpay.core.utils.client.BasicClient.ClientException;
import it.govpay.core.utils.client.NodoClient;
import it.govpay.core.utils.client.NodoClient.Azione;
import it.govpay.model.Intermediario;
import it.govpay.model.Notifica.TipoNotifica;
import it.govpay.model.Rpt.StatoRpt;

public class InviaRptThread implements Runnable {
	
	private Rpt rpt;
	private static Logger log = LoggerWrapperFactory.getLogger(InviaRptThread.class);
	private IContext ctx = null;
	private Giornale giornale;
	private Intermediario intermediario = null;
	private Stazione stazione = null;
	private Applicazione applicazione = null;
	private PagamentoPortale pagamentoPortale = null;
	private Versamento versamento = null;
	
	public InviaRptThread(Rpt rpt, BasicBD bd, IContext ctx) throws ServiceException {
		this.rpt = rpt;
		this.intermediario = this.rpt.getIntermediario(bd);
		this.stazione = this.rpt.getStazione(bd);
		this.ctx = ctx;
		this.giornale = AnagraficaManager.getConfigurazione(bd).getGiornale();
		this.versamento = this.rpt.getVersamento(bd);
		this.applicazione = this.versamento.getApplicazione(bd);
		try {
			this.pagamentoPortale = this.rpt.getPagamentoPortale(bd);
		} catch (NotFoundException e) {
		}
	}
	
	@Override
	public void run() {
		ContextThreadLocal.set(this.ctx);
		BasicBD bd = null;
		IContext ctx = ContextThreadLocal.get();
		GpContext appContext = (GpContext) ctx.getApplicationContext();
		MDC.put(MD5Constants.TRANSACTION_ID, ctx.getTransactionId());
		NodoClient client = null;
		try {
			String operationId = appContext.setupNodoClient(this.stazione.getCodStazione(), this.rpt.getCodDominio(), Azione.nodoInviaCarrelloRPT);
			log.info("Id Server: [" + operationId + "]");
			log.info("Spedizione RPT al Nodo [CodMsgRichiesta: " + this.rpt.getCodMsgRichiesta() + "]");
			
			appContext.getServerByOperationId(operationId).addGenericProperty(new Property("codDominio", this.rpt.getCodDominio()));
			appContext.getServerByOperationId(operationId).addGenericProperty(new Property("iuv", this.rpt.getIuv()));
			appContext.getServerByOperationId(operationId).addGenericProperty(new Property("ccp", this.rpt.getCcp()));
			
			ctx.getApplicationLogger().log("pagamento.invioRptAttivata");
				
			client = new it.govpay.core.utils.client.NodoClient(this.intermediario, operationId, this.giornale, bd);
			// salvataggio id Rpt/ versamento/ pagamento
			client.getEventoCtx().setCodDominio(this.rpt.getCodDominio());
			client.getEventoCtx().setIuv(this.rpt.getIuv());
			client.getEventoCtx().setCcp(this.rpt.getCcp());
			client.getEventoCtx().setIdA2A(this.applicazione.getCodApplicazione());
			client.getEventoCtx().setIdPendenza(this.versamento.getCodVersamentoEnte());
			if(this.pagamentoPortale != null)
				client.getEventoCtx().setIdPagamento(this.pagamentoPortale.getIdSessione());
			
			RptUtils.popolaEventoCooperazione(client, this.rpt, this.intermediario, this.stazione);
			
			NodoInviaRPT inviaRPT = new NodoInviaRPT();
			inviaRPT.setIdentificativoCanale(this.rpt.getCodCanale());
			inviaRPT.setIdentificativoIntermediarioPSP(this.rpt.getCodIntermediarioPsp());
			inviaRPT.setIdentificativoPSP(this.rpt.getCodPsp());
			inviaRPT.setPassword(this.stazione.getPassword());
			inviaRPT.setRpt(this.rpt.getXmlRpt());
			// FIX Bug Nodo che richiede firma vuota in caso di NESSUNA
			inviaRPT.setTipoFirma("");
			
			Risposta risposta = new it.govpay.core.business.model.Risposta(client.nodoInviaRPT(this.intermediario, this.stazione, this.rpt, inviaRPT)); 

			if(bd == null) {
				bd = BasicBD.newInstance(ContextThreadLocal.get().getTransactionId());
			}
			
			RptBD rptBD = new RptBD(bd);
			
			// Prima di procedere allo'aggiornamento dello stato verifico che nel frattempo non sia arrivato una RT
			this.rpt = rptBD.getRpt(this.rpt.getId());
			if(this.rpt.getStato().equals(StatoRpt.RT_ACCETTATA_PA)) {
				// E' arrivata l'RT nel frattempo. Non aggiornare.
				log.info("RPT inviata, ma nel frattempo e' arrivata l'RT. Non aggiorno lo stato");
				ctx.getApplicationLogger().log("pagamento.invioRptAttivataRTricevuta");
				return;
			}
				
			
			if(!risposta.getEsito().equals("OK") && !risposta.getFaultBean().getFaultCode().equals("PPT_RPT_DUPLICATA")) {
				// RPT rifiutata dal Nodo
				// Loggo l'errore ma lascio lo stato invariato. 
				// v3.1: Perche' non cambiare lo stato a fronte di un rifiuto? Lo aggiorno e evito la rispedizione.
				// Redo: Perche' e' difficile capire se e' un errore temporaneo o meno. Essendo un'attivazione di RPT, non devo smettere di riprovare.
				FaultBean fb = risposta.getFaultBean();
				String descrizione = null; 
				if(fb != null)
					descrizione = fb.getFaultCode() + ": " + fb.getFaultString();
				rptBD.updateRpt(this.rpt.getId(), null, descrizione, null, null,null);
				log.warn("RPT rifiutata dal nodo con fault " + descrizione);
				ctx.getApplicationLogger().log("pagamento.invioRptAttivataKo", fb.getFaultCode(), fb.getFaultString(), fb.getDescription() != null ? fb.getDescription() : "[-- Nessuna descrizione --]");
				if(client != null) {
					client.getEventoCtx().setSottotipoEsito(fb.getFaultCode());
					client.getEventoCtx().setEsito(Esito.KO);
					client.getEventoCtx().setDescrizioneEsito(descrizione);
				}
			} else {
				// RPT accettata dal Nodo
				// Invio la notifica e aggiorno lo stato
				Notifica notifica = new Notifica(this.rpt, TipoNotifica.ATTIVAZIONE, bd);
				it.govpay.core.business.Notifica notificaBD = new it.govpay.core.business.Notifica(bd);
				
				
				bd.setAutoCommit(false);
				rptBD.updateRpt(this.rpt.getId(), StatoRpt.RPT_ACCETTATA_NODO, null, null, null,null);
				boolean schedulaThreadInvio = notificaBD.inserisciNotifica(notifica);
				bd.commit();
				
				if(schedulaThreadInvio)
					ThreadExecutorManager.getClientPoolExecutorNotifica().execute(new InviaNotificaThread(notifica, bd,ctx));
				log.info("RPT inviata correttamente al nodo");
				ctx.getApplicationLogger().log("pagamento.invioRptAttivataOk");
				client.getEventoCtx().setEsito(Esito.OK);
			}
		} catch (ClientException e) {
			log.error("Errore nella spedizione della RPT", e);
			if(client != null) {
				client.getEventoCtx().setSottotipoEsito(e.getResponseCode() + "");
				client.getEventoCtx().setEsito(Esito.FAIL);
				client.getEventoCtx().setDescrizioneEsito(e.getMessage());
			}	
			try {
				ctx.getApplicationLogger().log("pagamento.invioRptAttivataFail", e.getMessage());
			} catch (UtilsException e1) {
				log.error("Errore durante il log dell'operazione: " + e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error("Errore nella spedizione della RPT", e);
			if(client != null) {
				if(e instanceof GovPayException) {
					client.getEventoCtx().setSottotipoEsito(((GovPayException)e).getCodEsito().toString());
				} else {
					client.getEventoCtx().setSottotipoEsito(EsitoOperazione.INTERNAL.toString());
				}
				client.getEventoCtx().setEsito(Esito.FAIL);
				client.getEventoCtx().setDescrizioneEsito(e.getMessage());
			}	
			try {
				ctx.getApplicationLogger().log("pagamento.invioRptAttivataFail", e.getMessage());
			} catch (UtilsException e1) {
				log.error("Errore durante il log dell'operazione: " + e.getMessage(), e);
			}
			if(bd != null) bd.rollback();
		} finally {
			if(client != null && client.getEventoCtx().isRegistraEvento()) {
				if(bd == null) {
					try {
						bd = BasicBD.newInstance(ContextThreadLocal.get().getTransactionId());
					} catch (ServiceException e) {
						log.error("Errore durante la init della connessione: " + e.getMessage(), e);
					}
				}
				GiornaleEventi giornaleEventi = new GiornaleEventi(bd);
				giornaleEventi.registraEvento(client.getEventoCtx().toEventoDTO());
			}
			if(bd != null) bd.closeConnection();
			ContextThreadLocal.unset();
		}
	}
}
