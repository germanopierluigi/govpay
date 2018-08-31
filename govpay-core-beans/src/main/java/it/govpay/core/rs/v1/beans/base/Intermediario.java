package it.govpay.core.rs.v1.beans.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openspcoop2.utils.json.ValidationException;

import com.fasterxml.jackson.annotation.JsonProperty;
@com.fasterxml.jackson.annotation.JsonPropertyOrder({
"denominazione",
"principalPagoPa",
"servizioPagoPa",
"servizioFtp",
"abilitato",
"idIntermediario",
"stazioni",
})
public class Intermediario extends it.govpay.core.rs.v1.beans.JSONSerializable {
  
  @JsonProperty("denominazione")
  private String denominazione = null;
  
  @JsonProperty("principalPagoPa")
  private String principalPagoPa = null;
  
  @JsonProperty("servizioPagoPa")
  private ConnettorePagopa servizioPagoPa = null;
  
  @JsonProperty("servizioFtp")
  private ServizioFtp servizioFtp = null;
  
  @JsonProperty("abilitato")
  private Boolean abilitato = null;
  
  @JsonProperty("idIntermediario")
  private String idIntermediario = null;
  
  @JsonProperty("stazioni")
  private List<StazioneIndex> stazioni = new ArrayList<StazioneIndex>();
  
  /**
   * Ragione sociale dell'intermediario PagoPA
   **/
  public Intermediario denominazione(String denominazione) {
    this.denominazione = denominazione;
    return this;
  }

  @JsonProperty("denominazione")
  public String getDenominazione() {
    return denominazione;
  }
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  /**
   * Principal autenticato le richieste ricevute da PagoPA
   **/
  public Intermediario principalPagoPa(String principalPagoPa) {
    this.principalPagoPa = principalPagoPa;
    return this;
  }

  @JsonProperty("principalPagoPa")
  public String getPrincipalPagoPa() {
    return principalPagoPa;
  }
  public void setPrincipalPagoPa(String principalPagoPa) {
    this.principalPagoPa = principalPagoPa;
  }

  /**
   **/
  public Intermediario servizioPagoPa(ConnettorePagopa servizioPagoPa) {
    this.servizioPagoPa = servizioPagoPa;
    return this;
  }

  @JsonProperty("servizioPagoPa")
  public ConnettorePagopa getServizioPagoPa() {
    return servizioPagoPa;
  }
  public void setServizioPagoPa(ConnettorePagopa servizioPagoPa) {
    this.servizioPagoPa = servizioPagoPa;
  }

  /**
   **/
  public Intermediario servizioFtp(ServizioFtp servizioFtp) {
    this.servizioFtp = servizioFtp;
    return this;
  }

  @JsonProperty("servizioFtp")
  public ServizioFtp getServizioFtp() {
    return servizioFtp;
  }
  public void setServizioFtp(ServizioFtp servizioFtp) {
    this.servizioFtp = servizioFtp;
  }

  /**
   * Indica lo stato di abilitazione
   **/
  public Intermediario abilitato(Boolean abilitato) {
    this.abilitato = abilitato;
    return this;
  }

  @JsonProperty("abilitato")
  public Boolean isAbilitato() {
    return abilitato;
  }
  public void setAbilitato(Boolean abilitato) {
    this.abilitato = abilitato;
  }

  /**
   * Identificativo dell'intermediario
   **/
  public Intermediario idIntermediario(String idIntermediario) {
    this.idIntermediario = idIntermediario;
    return this;
  }

  @JsonProperty("idIntermediario")
  public String getIdIntermediario() {
    return idIntermediario;
  }
  public void setIdIntermediario(String idIntermediario) {
    this.idIntermediario = idIntermediario;
  }

  /**
   * Url alla lista delle stazioni dell'intermediario
   **/
  public Intermediario stazioni(List<StazioneIndex> stazioni) {
    this.stazioni = stazioni;
    return this;
  }

  @JsonProperty("stazioni")
  public List<StazioneIndex> getStazioni() {
    return stazioni;
  }
  public void setStazioni(List<StazioneIndex> stazioni) {
    this.stazioni = stazioni;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Intermediario intermediario = (Intermediario) o;
    return Objects.equals(denominazione, intermediario.denominazione) &&
        Objects.equals(principalPagoPa, intermediario.principalPagoPa) &&
        Objects.equals(servizioPagoPa, intermediario.servizioPagoPa) &&
        Objects.equals(servizioFtp, intermediario.servizioFtp) &&
        Objects.equals(abilitato, intermediario.abilitato) &&
        Objects.equals(idIntermediario, intermediario.idIntermediario) &&
        Objects.equals(stazioni, intermediario.stazioni);
  }

  @Override
  public int hashCode() {
    return Objects.hash(denominazione, principalPagoPa, servizioPagoPa, servizioFtp, abilitato, idIntermediario, stazioni);
  }

  public static Intermediario parse(String json) throws org.openspcoop2.generic_project.exception.ServiceException, ValidationException {
    return (Intermediario) parse(json, Intermediario.class);
  }

  @Override
  public String getJsonIdFilter() {
    return "intermediario";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Intermediario {\n");
    
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    principalPagoPa: ").append(toIndentedString(principalPagoPa)).append("\n");
    sb.append("    servizioPagoPa: ").append(toIndentedString(servizioPagoPa)).append("\n");
    sb.append("    servizioFtp: ").append(toIndentedString(servizioFtp)).append("\n");
    sb.append("    abilitato: ").append(toIndentedString(abilitato)).append("\n");
    sb.append("    idIntermediario: ").append(toIndentedString(idIntermediario)).append("\n");
    sb.append("    stazioni: ").append(toIndentedString(stazioni)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


