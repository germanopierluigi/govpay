package it.govpay.core.rs.v1.beans.base;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.openspcoop2.generic_project.exception.ValidationException;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.govpay.core.utils.validator.IValidable;
import it.govpay.core.utils.validator.ValidatorFactory;

/**
 * informazioni sulla codifica e decodifica degli iuv
 **/@com.fasterxml.jackson.annotation.JsonPropertyOrder({
"codificaIuv",
"regExpIuv",
"generazioneIuvInterna",
})
public class CodificaAvvisi extends it.govpay.core.rs.v1.beans.JSONSerializable implements IValidable {
  
  @JsonProperty("codificaIuv")
  private String codificaIuv = null;
  
  @JsonProperty("regExpIuv")
  private String regExpIuv = null;
  
  @JsonProperty("generazioneIuvInterna")
  private Boolean generazioneIuvInterna = null;
  
  /**
   * Cifra identificativa negli IUV. Deve essere un codice numerico.
   **/
  public CodificaAvvisi codificaIuv(String codificaIuv) {
    this.codificaIuv = codificaIuv;
    return this;
  }

  @JsonProperty("codificaIuv")
  public String getCodificaIuv() {
    return codificaIuv;
  }
  public void setCodificaIuv(String codificaIuv) {
    this.codificaIuv = codificaIuv;
  }

  /**
   * Espressione regolare di verifica del numero avviso
   **/
  public CodificaAvvisi regExpIuv(String regExpIuv) {
    this.regExpIuv = regExpIuv;
    return this;
  }

  @JsonProperty("regExpIuv")
  public String getRegExpIuv() {
    return regExpIuv;
  }
  public void setRegExpIuv(String regExpIuv) {
    this.regExpIuv = regExpIuv;
  }

  /**
   * Indicazione se l'applicazione genera in autonomia gli iuv
   **/
  public CodificaAvvisi generazioneIuvInterna(Boolean generazioneIuvInterna) {
    this.generazioneIuvInterna = generazioneIuvInterna;
    return this;
  }

  @JsonProperty("generazioneIuvInterna")
  public Boolean isGenerazioneIuvInterna() {
    return generazioneIuvInterna;
  }
  public void setGenerazioneIuvInterna(Boolean generazioneIuvInterna) {
    this.generazioneIuvInterna = generazioneIuvInterna;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodificaAvvisi codificaAvvisi = (CodificaAvvisi) o;
    return Objects.equals(codificaIuv, codificaAvvisi.codificaIuv) &&
        Objects.equals(regExpIuv, codificaAvvisi.regExpIuv) &&
        Objects.equals(generazioneIuvInterna, codificaAvvisi.generazioneIuvInterna);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codificaIuv, regExpIuv, generazioneIuvInterna);
  }

  public static CodificaAvvisi parse(String json) throws org.openspcoop2.generic_project.exception.ServiceException, org.openspcoop2.utils.json.ValidationException {
    return (CodificaAvvisi) parse(json, CodificaAvvisi.class);
  }

  @Override
  public String getJsonIdFilter() {
    return "codificaAvvisi";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodificaAvvisi {\n");
    
    sb.append("    codificaIuv: ").append(toIndentedString(codificaIuv)).append("\n");
    sb.append("    regExpIuv: ").append(toIndentedString(regExpIuv)).append("\n");
    sb.append("    generazioneIuvInterna: ").append(toIndentedString(generazioneIuvInterna)).append("\n");
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
  
  public void validate() throws ValidationException {
		ValidatorFactory vf = ValidatorFactory.newInstance();
		if(codificaIuv != null)
			vf.getValidator("codificaIuv", codificaIuv).pattern("[0-9]{1,15}");
		
		if(regExpIuv != null)
			try {
				Pattern.compile(regExpIuv);
			} catch(PatternSyntaxException e) {
			    throw new ValidationException("Il valore [" + regExpIuv + "] del campo regExpIuv non e' una espressione regolare valida.");
			}
		
		vf.getValidator("generazioneIuvInterna", generazioneIuvInterna).notNull();
	}
}


