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
package it.govpay.orm.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import it.govpay.orm.TipoVersamentoDominio;


/**     
 * TipoVersamentoDominioFieldConverter
 *
 * @author Giovanni Bussu (bussu@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TipoVersamentoDominioFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public TipoVersamentoDominioFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public TipoVersamentoDominioFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return TipoVersamentoDominio.model();
	}
	
	@Override
	public TipiDatabase getDatabaseType() throws ExpressionException {
		return this.databaseType;
	}
	


	@Override
	public String toColumn(IField field,boolean returnAlias,boolean appendTablePrefix) throws ExpressionException {
		
		// In the case of columns with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the column containing the alias
		
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.COD_TIPO_VERSAMENTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cod_tipo_versamento";
			}else{
				return "cod_tipo_versamento";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.DESCRIZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".descrizione";
			}else{
				return "descrizione";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.CODIFICA_IUV)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codifica_iuv";
			}else{
				return "codifica_iuv";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.TIPO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo";
			}else{
				return "tipo";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.PAGA_TERZI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".paga_terzi";
			}else{
				return "paga_terzi";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.ABILITATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".abilitato";
			}else{
				return "abilitato";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.JSON_SCHEMA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".json_schema";
			}else{
				return "json_schema";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.DATI_ALLEGATI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dati_allegati";
			}else{
				return "dati_allegati";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().ID_DOMINIO.COD_DOMINIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".cod_dominio";
			}else{
				return "cod_dominio";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().CODIFICA_IUV)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".codifica_iuv";
			}else{
				return "codifica_iuv";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo";
			}else{
				return "tipo";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().PAGA_TERZI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".paga_terzi";
			}else{
				return "paga_terzi";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().ABILITATO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".abilitato";
			}else{
				return "abilitato";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().JSON_SCHEMA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".json_schema";
			}else{
				return "json_schema";
			}
		}
		if(field.equals(TipoVersamentoDominio.model().DATI_ALLEGATI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".dati_allegati";
			}else{
				return "dati_allegati";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.COD_TIPO_VERSAMENTO)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.DESCRIZIONE)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.CODIFICA_IUV)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.TIPO)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.PAGA_TERZI)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.ABILITATO)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.JSON_SCHEMA)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO.DATI_ALLEGATI)){
			return this.toTable(TipoVersamentoDominio.model().TIPO_VERSAMENTO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().ID_DOMINIO.COD_DOMINIO)){
			return this.toTable(TipoVersamentoDominio.model().ID_DOMINIO, returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().CODIFICA_IUV)){
			return this.toTable(TipoVersamentoDominio.model(), returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().TIPO)){
			return this.toTable(TipoVersamentoDominio.model(), returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().PAGA_TERZI)){
			return this.toTable(TipoVersamentoDominio.model(), returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().ABILITATO)){
			return this.toTable(TipoVersamentoDominio.model(), returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().JSON_SCHEMA)){
			return this.toTable(TipoVersamentoDominio.model(), returnAlias);
		}
		if(field.equals(TipoVersamentoDominio.model().DATI_ALLEGATI)){
			return this.toTable(TipoVersamentoDominio.model(), returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(TipoVersamentoDominio.model())){
			return "tipi_vers_domini";
		}
		if(model.equals(TipoVersamentoDominio.model().TIPO_VERSAMENTO)){
			return "tipi_versamento";
		}
		if(model.equals(TipoVersamentoDominio.model().ID_DOMINIO)){
			return "domini";
		}


		return super.toTable(model,returnAlias);
		
	}

}
