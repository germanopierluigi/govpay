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
package it.govpay.bd.model.converter;

import java.util.ArrayList;
import java.util.List;

import org.openspcoop2.generic_project.exception.ServiceException;

import it.govpay.model.TipoVersamento;
import it.govpay.model.TipoVersamento.Tipo;

public class TipoVersamentoConverter {

	public static List<TipoVersamento> toDTOList(List<it.govpay.orm.TipoVersamento> lstVO) throws ServiceException {
		List<TipoVersamento> lst = new ArrayList<>();
		if(lstVO != null && !lstVO.isEmpty()) {
			for(it.govpay.orm.TipoVersamento vo: lstVO) {
				lst.add(toDTO(vo));
			}
		}
		return lst;
	}

	public static TipoVersamento toDTO(it.govpay.orm.TipoVersamento vo) throws ServiceException {
		TipoVersamento dto = new TipoVersamento();
		dto.setId(vo.getId());
		dto.setCodTipoVersamento(vo.getCodTipoVersamento());
		dto.setDescrizione(vo.getDescrizione());
		dto.setCodificaIuvDefault(vo.getCodificaIuv());
		if(vo.getTipo() != null)
			dto.setTipoDefault(Tipo.toEnum(vo.getTipo()));
		dto.setPagaTerziDefault(vo.isPagaTerzi());
		dto.setAbilitatoDefault(vo.getAbilitato());
		dto.setJsonSchemaDefault(vo.getJsonSchema());
		dto.setDatiAllegatiDefault(vo.getDatiAllegati());
		
		return dto;
	}

	public static it.govpay.orm.TipoVersamento toVO(TipoVersamento dto) {
		it.govpay.orm.TipoVersamento vo = new it.govpay.orm.TipoVersamento();
		vo.setId(dto.getId());
		vo.setCodTipoVersamento(dto.getCodTipoVersamento());
		vo.setDescrizione(dto.getDescrizione());
		vo.setCodificaIuv(dto.getCodificaIuvDefault());
		if(dto.getTipoDefault() != null)
			vo.setTipo(dto.getTipoDefault().getCodifica());
		vo.setPagaTerzi(dto.getPagaTerziDefault());
		vo.setAbilitato(dto.isAbilitatoDefault());
		vo.setJsonSchema(dto.getJsonSchemaDefault());
		vo.setDatiAllegati(dto.getDatiAllegatiDefault());
		return vo;
	}


}
