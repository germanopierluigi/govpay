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
package it.govpay.orm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for TipoVersamentoDominio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipoVersamentoDominio">
 * 		&lt;sequence>
 * 			&lt;element name="tipoVersamento" type="{http://www.govpay.it/orm}TipoVersamento" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="idDominio" type="{http://www.govpay.it/orm}id-dominio" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="codificaIuv" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="tipo" type="{http://www.govpay.it/orm}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="pagaTerzi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="abilitato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="jsonSchema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 			&lt;element name="datiAllegati" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Giovanni Bussu (bussu@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoVersamentoDominio", 
  propOrder = {
  	"tipoVersamento",
  	"idDominio",
  	"codificaIuv",
  	"tipo",
  	"pagaTerzi",
  	"abilitato",
  	"jsonSchema",
  	"datiAllegati"
  }
)

@XmlRootElement(name = "TipoVersamentoDominio")

public class TipoVersamentoDominio extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public TipoVersamentoDominio() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public TipoVersamento getTipoVersamento() {
    return this.tipoVersamento;
  }

  public void setTipoVersamento(TipoVersamento tipoVersamento) {
    this.tipoVersamento = tipoVersamento;
  }

  public IdDominio getIdDominio() {
    return this.idDominio;
  }

  public void setIdDominio(IdDominio idDominio) {
    this.idDominio = idDominio;
  }

  public java.lang.String getCodificaIuv() {
    return this.codificaIuv;
  }

  public void setCodificaIuv(java.lang.String codificaIuv) {
    this.codificaIuv = codificaIuv;
  }

  public java.lang.String getTipo() {
    return this.tipo;
  }

  public void setTipo(java.lang.String tipo) {
    this.tipo = tipo;
  }

  public Boolean getPagaTerzi() {
    return this.pagaTerzi;
  }

  public void setPagaTerzi(Boolean pagaTerzi) {
    this.pagaTerzi = pagaTerzi;
  }

  public Boolean getAbilitato() {
    return this.abilitato;
  }

  public void setAbilitato(Boolean abilitato) {
    this.abilitato = abilitato;
  }

  public java.lang.String getJsonSchema() {
    return this.jsonSchema;
  }

  public void setJsonSchema(java.lang.String jsonSchema) {
    this.jsonSchema = jsonSchema;
  }

  public java.lang.String getDatiAllegati() {
    return this.datiAllegati;
  }

  public void setDatiAllegati(java.lang.String datiAllegati) {
    this.datiAllegati = datiAllegati;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;

  private static it.govpay.orm.model.TipoVersamentoDominioModel modelStaticInstance = null;
  private static synchronized void initModelStaticInstance(){
	  if(it.govpay.orm.TipoVersamentoDominio.modelStaticInstance==null){
  			it.govpay.orm.TipoVersamentoDominio.modelStaticInstance = new it.govpay.orm.model.TipoVersamentoDominioModel();
	  }
  }
  public static it.govpay.orm.model.TipoVersamentoDominioModel model(){
	  if(it.govpay.orm.TipoVersamentoDominio.modelStaticInstance==null){
	  		initModelStaticInstance();
	  }
	  return it.govpay.orm.TipoVersamentoDominio.modelStaticInstance;
  }


  @XmlElement(name="tipoVersamento",required=true,nillable=false)
  protected TipoVersamento tipoVersamento;

  @XmlElement(name="idDominio",required=true,nillable=false)
  protected IdDominio idDominio;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="codificaIuv",required=false,nillable=false)
  protected java.lang.String codificaIuv;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="tipo",required=false,nillable=false)
  protected java.lang.String tipo;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="pagaTerzi",required=false,nillable=false)
  protected Boolean pagaTerzi;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="abilitato",required=false,nillable=false)
  protected Boolean abilitato;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="jsonSchema",required=false,nillable=false)
  protected java.lang.String jsonSchema;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="datiAllegati",required=false,nillable=false)
  protected java.lang.String datiAllegati;

}
