package it.govpay.core.rs.v1.beans.pagamenti;

import java.util.Objects;

import org.openspcoop2.generic_project.exception.ValidationException;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.govpay.core.rs.v1.beans.JSONSerializable;
import it.govpay.core.utils.validator.IValidable;
import it.govpay.core.utils.validator.ValidatorFactory;

/**
 * Dati necessari alla realizzazione dei pagamenti per Addebito Diretto, se previsto dal profilo del versante.
 **/

@com.fasterxml.jackson.annotation.JsonPropertyOrder({
"iban",
"bic",
})
public class ContoAddebito extends JSONSerializable implements IValidable {
  
  @JsonProperty("iban")
  private String iban = null;
  
  @JsonProperty("bic")
  private String bic = null;
  
  /**
   * Iban di addebito del pagatore.
   **/
  public ContoAddebito iban(String iban) {
    this.iban = iban;
    return this;
  }

  @JsonProperty("iban")
  public String getIban() {
    return iban;
  }
  public void setIban(String iban) {
    this.iban = iban;
  }

  /**
   * Bic della banca di addebito del pagatore.
   **/
  public ContoAddebito bic(String bic) {
    this.bic = bic;
    return this;
  }

  @JsonProperty("bic")
  public String getBic() {
    return bic;
  }
  public void setBic(String bic) {
    this.bic = bic;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContoAddebito contoAddebito = (ContoAddebito) o;
    return Objects.equals(iban, contoAddebito.iban) &&
        Objects.equals(bic, contoAddebito.bic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iban, bic);
  }

  public static ContoAddebito parse(String json) throws org.openspcoop2.generic_project.exception.ServiceException, org.openspcoop2.utils.json.ValidationException {
    return parse(json, ContoAddebito.class);
  }

  @Override
  public String getJsonIdFilter() {
    return "contoAddebito";
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContoAddebito {\n");
    
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    bic: ").append(toIndentedString(bic)).append("\n");
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
	  vf.getValidator("iban", iban).notNull().pattern("[a-zA-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}");
	  vf.getValidator("iban", iban).pattern("[A-Z]{6,6}[A-Z2-9][A-NP-Z0-9]([A-Z0-9]{3,3}){0,1}");
  }
  
}


