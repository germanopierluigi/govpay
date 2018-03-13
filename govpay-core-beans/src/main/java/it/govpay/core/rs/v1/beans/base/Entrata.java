package it.govpay.core.rs.v1.beans.base;

import java.math.BigDecimal;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonValue;
@org.codehaus.jackson.annotate.JsonPropertyOrder({
"ibanAccreditoBancario",
"ibanAccreditoPostale",
"tipoContabilita",
"codiceContabilita",
"codificaIUV",
"abilitato",
"idEntrata",
"tipoEntrata",
})
public class Entrata extends it.govpay.core.rs.v1.beans.JSONSerializable {
  
  @JsonProperty("ibanAccreditoBancario")
  private String ibanAccreditoBancario = null;
  
  @JsonProperty("ibanAccreditoPostale")
  private String ibanAccreditoPostale = null;
  
    
  /**
   * Tipologia di codifica del capitolo di bilancio
   */
  public enum TipoContabilitaEnum {
    
    
        
            
    ENTRATA("Entrata"),
    
            
    SPECIALE("Speciale"),
    
            
    SIOPE("SIOPE"),
    
            
    ALTRO("Altro");
            
        
    

    private String value;

    TipoContabilitaEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    public static TipoContabilitaEnum fromValue(String text) {
      for (TipoContabilitaEnum b : TipoContabilitaEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

    
    
  @JsonProperty("tipoContabilita")
  private TipoContabilitaEnum tipoContabilita = null;
  
  @JsonProperty("codiceContabilita")
  private String codiceContabilita = null;
  
  @JsonProperty("codificaIUV")
  private BigDecimal codificaIUV = null;
  
  @JsonProperty("abilitato")
  private Boolean abilitato = true;
  
  @JsonProperty("idEntrata")
  private String idEntrata = null;
  
  @JsonProperty("tipoEntrata")
  private TipoEntrata tipoEntrata = null;
  
  /**
   **/
  public Entrata ibanAccreditoBancario(String ibanAccreditoBancario) {
    this.ibanAccreditoBancario = ibanAccreditoBancario;
    return this;
  }

  @JsonProperty("ibanAccreditoBancario")
  public String getIbanAccreditoBancario() {
    return ibanAccreditoBancario;
  }
  public void setIbanAccreditoBancario(String ibanAccreditoBancario) {
    this.ibanAccreditoBancario = ibanAccreditoBancario;
  }

  /**
   **/
  public Entrata ibanAccreditoPostale(String ibanAccreditoPostale) {
    this.ibanAccreditoPostale = ibanAccreditoPostale;
    return this;
  }

  @JsonProperty("ibanAccreditoPostale")
  public String getIbanAccreditoPostale() {
    return ibanAccreditoPostale;
  }
  public void setIbanAccreditoPostale(String ibanAccreditoPostale) {
    this.ibanAccreditoPostale = ibanAccreditoPostale;
  }

  /**
   * Tipologia di codifica del capitolo di bilancio
   **/
  public Entrata tipoContabilita(TipoContabilitaEnum tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
    return this;
  }

  @JsonProperty("tipoContabilita")
  public TipoContabilitaEnum getTipoContabilita() {
    return tipoContabilita;
  }
  public void setTipoContabilita(TipoContabilitaEnum tipoContabilita) {
    this.tipoContabilita = tipoContabilita;
  }

  /**
   * Codifica del capitolo di bilancio
   **/
  public Entrata codiceContabilita(String codiceContabilita) {
    this.codiceContabilita = codiceContabilita;
    return this;
  }

  @JsonProperty("codiceContabilita")
  public String getCodiceContabilita() {
    return codiceContabilita;
  }
  public void setCodiceContabilita(String codiceContabilita) {
    this.codiceContabilita = codiceContabilita;
  }

  /**
   * Cifra identificativa negli IUV
   **/
  public Entrata codificaIUV(BigDecimal codificaIUV) {
    this.codificaIUV = codificaIUV;
    return this;
  }

  @JsonProperty("codificaIUV")
  public BigDecimal getCodificaIUV() {
    return codificaIUV;
  }
  public void setCodificaIUV(BigDecimal codificaIUV) {
    this.codificaIUV = codificaIUV;
  }

  /**
   * Indicazione l'entrata e' abilitata
   **/
  public Entrata abilitato(Boolean abilitato) {
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
   **/
  public Entrata idEntrata(String idEntrata) {
    this.idEntrata = idEntrata;
    return this;
  }

  @JsonProperty("idEntrata")
  public String getIdEntrata() {
    return idEntrata;
  }
  public void setIdEntrata(String idEntrata) {
    this.idEntrata = idEntrata;
  }

  /**
   **/
  public Entrata tipoEntrata(TipoEntrata tipoEntrata) {
    this.tipoEntrata = tipoEntrata;
    return this;
  }

  @JsonProperty("tipoEntrata")
  public TipoEntrata getTipoEntrata() {
    return tipoEntrata;
  }
  public void setTipoEntrata(TipoEntrata tipoEntrata) {
    this.tipoEntrata = tipoEntrata;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entrata entrata = (Entrata) o;
    return Objects.equals(ibanAccreditoBancario, entrata.ibanAccreditoBancario) &&
        Objects.equals(ibanAccreditoPostale, entrata.ibanAccreditoPostale) &&
        Objects.equals(tipoContabilita, entrata.tipoContabilita) &&
        Objects.equals(codiceContabilita, entrata.codiceContabilita) &&
        Objects.equals(codificaIUV, entrata.codificaIUV) &&
        Objects.equals(abilitato, entrata.abilitato) &&
        Objects.equals(idEntrata, entrata.idEntrata) &&
        Objects.equals(tipoEntrata, entrata.tipoEntrata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibanAccreditoBancario, ibanAccreditoPostale, tipoContabilita, codiceContabilita, codificaIUV, abilitato, idEntrata, tipoEntrata);
  }

  public static Entrata parse(String json) {
    return (Entrata) parse(json, Entrata.class);
  }

  @Override
  public String getJsonIdFilter() {
    return "entrata";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Entrata {\n");
    
    sb.append("    ibanAccreditoBancario: ").append(toIndentedString(ibanAccreditoBancario)).append("\n");
    sb.append("    ibanAccreditoPostale: ").append(toIndentedString(ibanAccreditoPostale)).append("\n");
    sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
    sb.append("    codiceContabilita: ").append(toIndentedString(codiceContabilita)).append("\n");
    sb.append("    codificaIUV: ").append(toIndentedString(codificaIUV)).append("\n");
    sb.append("    abilitato: ").append(toIndentedString(abilitato)).append("\n");
    sb.append("    idEntrata: ").append(toIndentedString(idEntrata)).append("\n");
    sb.append("    tipoEntrata: ").append(toIndentedString(tipoEntrata)).append("\n");
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


