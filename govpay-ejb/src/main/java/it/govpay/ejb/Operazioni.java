/*
 * GovPay - Porta di Accesso al Nodo dei Pagamenti SPC 
 * http://www.gov4j.it/govpay
 * 
 * Copyright (c) 2014-2016 Link.it srl (http://www.link.it).
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
package it.govpay.ejb;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import it.govpay.core.utils.GovpayConfig;


@Singleton
public class Operazioni{

	@Resource
	TimerService timerservice;

	@Schedule(hour="4,8,16,20", persistent=false)
	@AccessTimeout(value=10, unit=TimeUnit.MINUTES)
	public static String acquisizioneRendicontazioni(){
		if(!GovpayConfig.getInstance().isBatchOn()) {
			return "Batch non attivi";
		}
		return it.govpay.core.business.Operazioni.acquisizioneRendicontazioni("Batch");
	}

	@Schedule(hour="0,12", persistent=false)
	@AccessTimeout(value=10, unit=TimeUnit.MINUTES)
	public static String aggiornamentoRegistroPsp(){
		if(!GovpayConfig.getInstance().isBatchOn()) {
			return "Batch non attivi";
		}
		return it.govpay.core.business.Operazioni.aggiornamentoRegistroPsp("Batch");
	}

	@Schedule(hour="2,6,10,14,18,22", persistent=false)
	@AccessTimeout(value=10, unit=TimeUnit.MINUTES)
	public static String recuperoRptPendenti(){
		if(!GovpayConfig.getInstance().isBatchOn()) {
			return "Batch non attivi";
		}
		return it.govpay.core.business.Operazioni.recuperoRptPendenti("Batch");
	}

	@Schedule(hour="*", minute="*", persistent=false)
	@AccessTimeout(value=20, unit=TimeUnit.MINUTES)
	public static String spedizioneNotifiche(){
		if(!GovpayConfig.getInstance().isBatchOn()) {
			return "Batch non attivi";
		}
		return it.govpay.core.business.Operazioni.spedizioneNotifiche("Batch");
	}

	public static String resetCacheAnagrafica(){
		if(!GovpayConfig.getInstance().isBatchOn()) {
			return "Batch non attivi";
		}
		return it.govpay.core.business.Operazioni.resetCacheAnagrafica();
	}
	
	@Schedule(hour="0,12", persistent=false)
	@AccessTimeout(value=5, unit=TimeUnit.MINUTES)
	public void generaEstrattoConto(Timer timer) {
		it.govpay.core.business.Operazioni.estrattoConto("Batch");
	}

}