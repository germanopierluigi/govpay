package it.govpay.core.rs.v1.beans.pagamenti;

import java.math.BigDecimal;
import java.util.Objects;

import org.openspcoop2.generic_project.exception.ValidationException;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.govpay.core.rs.v1.beans.JSONSerializable;
import it.govpay.core.utils.validator.IValidable;
import it.govpay.core.utils.validator.ValidatorFactory;
@com.fasterxml.jackson.annotation.JsonPropertyOrder({
	"indice",
	"idVocePendenza",
	"importo",
	"descrizione",
	"stato",
	"datiAllegati",
	"hashDocumento",
	"tipoBollo",
	"provinciaResidenza",
	"codEntrata",
	"codiceContabilita",
	"ibanAccredito",
	"ibanAppoggio",
	"tipoContabilita",
})
public class VocePendenza extends JSONSerializable implements IValidable {

	@JsonProperty("indice")
	private BigDecimal indice = null;

	@JsonProperty("idVocePendenza")
	private String idVocePendenza = null;

	@JsonProperty("importo")
	private BigDecimal importo = null;

	@JsonProperty("descrizione")
	private String descrizione = null;


	/**
	 * Stato della voce di pagamento
	 */
	public enum StatoEnum {




		ESEGUITO("Eseguito"),


		NON_ESEGUITO("Non eseguito"),


		ANOMALO("Anomalo");




		private String value;

		StatoEnum(String value) {
			this.value = value;
		}

		@Override
		@com.fasterxml.jackson.annotation.JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		public static StatoEnum fromValue(String text) {
			for (StatoEnum b : StatoEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}



	@JsonProperty("stato")
	private StatoEnum stato = null;

	@JsonProperty("datiAllegati")
	private String datiAllegati = null;

	@JsonProperty("hashDocumento")
	private String hashDocumento= null;

	public enum TipoBolloEnum {


		IMPOSTA_DI_BOLLO("Imposta di bollo");


		private String value;


		TipoBolloEnum(String value) {
			this.value = value;
		}

		@Override
		@com.fasterxml.jackson.annotation.JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		public String getCodifica() {
			switch (this) {
			case IMPOSTA_DI_BOLLO:
				return "01";
			default:
				return "01";
			}
		}

		public static TipoBolloEnum fromValue(String text) {
			for (TipoBolloEnum b : TipoBolloEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("tipoBollo")
	private TipoBolloEnum tipoBollo= null;

	@JsonProperty("provinciaResidenza")
	private String provinciaResidenza= null;

	@JsonProperty("codEntrata")
	private String codEntrata= null;

	@JsonProperty("codiceContabilita")
	private String codiceContabilita= null;

	@JsonProperty("ibanAccredito")
	private String ibanAccredito= null;

	@JsonProperty("ibanAppoggio")
	private String ibanAppoggio= null;

	public enum TipoContabilitaEnum {


		CAPITOLO("CAPITOLO"), SPECIALE("SPECIALE"), SIOPE("SIOPE"), ALTRO("ALTRO");


		private String value;

		TipoContabilitaEnum(String value) {
			this.value = value;
		}

		@Override
		@com.fasterxml.jackson.annotation.JsonValue
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
	private TipoContabilitaEnum tipoContabilita= null;

	/**
	 * indice di voce all'interno della pendenza
	 **/
	public VocePendenza indice(BigDecimal indice) {
		this.indice = indice;
		return this;
	}

	@JsonProperty("indice")
	public BigDecimal getIndice() {
		return indice;
	}
	public void setIndice(BigDecimal indice) {
		this.indice = indice;
	}

	/**
	 * Identificativo della voce di pedenza nel gestionale proprietario
	 **/
	public VocePendenza idVocePendenza(String idVocePendenza) {
		this.idVocePendenza = idVocePendenza;
		return this;
	}

	@JsonProperty("idVocePendenza")
	public String getIdVocePendenza() {
		return idVocePendenza;
	}
	public void setIdVocePendenza(String idVocePendenza) {
		this.idVocePendenza = idVocePendenza;
	}

	/**
	 * Importo della voce
	 **/
	public VocePendenza importo(BigDecimal importo) {
		this.importo = importo;
		return this;
	}

	@JsonProperty("importo")
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	/**
	 * descrizione della voce di pagamento
	 **/
	public VocePendenza descrizione(String descrizione) {
		this.descrizione = descrizione;
		return this;
	}

	@JsonProperty("descrizione")
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * Stato della voce di pagamento
	 **/
	public VocePendenza stato(StatoEnum stato) {
		this.stato = stato;
		return this;
	}

	@JsonProperty("stato")
	public StatoEnum getStato() {
		return stato;
	}
	public void setStato(StatoEnum stato) {
		this.stato = stato;
	}

	/**
	 * Dati applicativi allegati dal gestionale secondo un formato proprietario.
	 **/
	public VocePendenza datiAllegati(String datiAllegati) {
		this.datiAllegati = datiAllegati;
		return this;
	}

	@JsonProperty("datiAllegati")
	public String getDatiAllegati() {
		return datiAllegati;
	}
	public void setDatiAllegati(String datiAllegati) {
		this.datiAllegati = datiAllegati;
	}

	public VocePendenza hashDocumento(String hashDocumento) {
		this.hashDocumento = hashDocumento;
		return this;
	}

	@JsonProperty("hashDocumento")
	public String getHashDocumento() {
		return hashDocumento;
	}
	public void setHashDocumento(String hashDocumento) {
		this.hashDocumento = hashDocumento;
	}

	/**
	 * Stato della voce di pagamento
	 **/
	public VocePendenza tipoBollo(TipoBolloEnum tipoBollo) {
		this.tipoBollo = tipoBollo;
		return this;
	}

	@JsonProperty("tipoBollo")
	public TipoBolloEnum getTipoBollo() {
		return tipoBollo;
	}
	public void setTipoBollo(TipoBolloEnum tipoBollo) {
		this.tipoBollo = tipoBollo;
	}

	public VocePendenza codEntrata(String codEntrata) {
		this.codEntrata = codEntrata;
		return this;
	}

	@JsonProperty("codEntrata")
	public String getCodEntrata() {
		return codEntrata;
	}
	public void setCodEntrata(String codEntrata) {
		this.codEntrata= codEntrata;
	}

	public VocePendenza provinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
		return this;
	}

	@JsonProperty("provinciaResidenza")
	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public VocePendenza codiceContabilita(String codiceContabilita) {
		this.codiceContabilita= codiceContabilita;
		return this;
	}

	@JsonProperty("codiceContabilita")
	public String getCodiceContabilita() {
		return codiceContabilita;
	}
	public void setCodiceContabilita(String CodiceContabilita) {
		this.codiceContabilita = CodiceContabilita;
	}

	public VocePendenza ibanAccredito(String ibanAccredito) {
		this.ibanAccredito= ibanAccredito;
		return this;
	}

	@JsonProperty("ibanAccredito")
	public String getIbanAccredito() {
		return ibanAccredito;
	}
	public void setIbanAccredito(String ibanAccredito) {
		this.ibanAccredito = ibanAccredito;
	}

	public VocePendenza ibanAppoggio(String ibanAppoggio) {
		this.ibanAppoggio= ibanAppoggio;
		return this;
	}

	@JsonProperty("ibanAppoggio")
	public String getIbanAppoggio() {
		return ibanAppoggio;
	}
	public void setIbanAppoggio(String ibanAppoggio) {
		this.ibanAppoggio = ibanAppoggio;
	}

	public VocePendenza tipoContabilita(TipoContabilitaEnum tipoContabilita) {
		this.tipoContabilita= tipoContabilita;
		return this;
	}

	@JsonProperty("tipoContabilita")
	public TipoContabilitaEnum getTipoContabilita() {
		return tipoContabilita;
	}
	public void setTipoContabilita(TipoContabilitaEnum tipoContabilita) {
		this.tipoContabilita = tipoContabilita;
	}
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		VocePendenza vocePendenza = (VocePendenza) o;
		return Objects.equals(indice, vocePendenza.indice) &&
				Objects.equals(idVocePendenza, vocePendenza.idVocePendenza) &&
				Objects.equals(importo, vocePendenza.importo) &&
				Objects.equals(descrizione, vocePendenza.descrizione) &&
				Objects.equals(stato, vocePendenza.stato) &&
				Objects.equals(datiAllegati, vocePendenza.datiAllegati) &&
				Objects.equals(hashDocumento, vocePendenza.hashDocumento) &&
				Objects.equals(tipoBollo, vocePendenza.tipoBollo) &&
				Objects.equals(provinciaResidenza, vocePendenza.provinciaResidenza) &&
				Objects.equals(codiceContabilita, vocePendenza.codiceContabilita) &&
				Objects.equals(ibanAccredito, vocePendenza.ibanAccredito) &&
				Objects.equals(ibanAppoggio, vocePendenza.ibanAppoggio) &&
				Objects.equals(tipoContabilita, vocePendenza.tipoContabilita);
	}

	@Override
	public int hashCode() {
		return Objects.hash(indice, idVocePendenza, importo, descrizione, stato, datiAllegati, hashDocumento, tipoBollo, provinciaResidenza, codiceContabilita, ibanAccredito, ibanAppoggio, tipoContabilita);
	}

	public static VocePendenza parse(String json) throws org.openspcoop2.generic_project.exception.ServiceException, org.openspcoop2.utils.json.ValidationException {
		return parse(json, VocePendenza.class);
	}

	@Override
	public String getJsonIdFilter() {
		return "vocePendenza";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class VocePendenza {\n");

		sb.append("    indice: ").append(toIndentedString(indice)).append("\n");
		sb.append("    idVocePendenza: ").append(toIndentedString(idVocePendenza)).append("\n");
		sb.append("    importo: ").append(toIndentedString(importo)).append("\n");
		sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
		sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
		sb.append("    datiAllegati: ").append(toIndentedString(datiAllegati)).append("\n");
		sb.append("    hashDocumento: ").append(toIndentedString(hashDocumento)).append("\n");
		sb.append("    tipoBollo: ").append(toIndentedString(tipoBollo)).append("\n");
		sb.append("    provinciaResidenza: ").append(toIndentedString(provinciaResidenza)).append("\n");
		sb.append("    codiceContabilita: ").append(toIndentedString(codiceContabilita)).append("\n");
		sb.append("    ibanAccredito: ").append(toIndentedString(ibanAccredito)).append("\n");
		sb.append("    ibanAppoggio: ").append(toIndentedString(ibanAppoggio)).append("\n");
		sb.append("    tipoContabilita: ").append(toIndentedString(tipoContabilita)).append("\n");
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

	@Override
	public void validate() throws ValidationException {
		ValidatorFactory vf = ValidatorFactory.newInstance();
		vf.getValidator("idVocePendenza", idVocePendenza).notNull().minLength(1).maxLength(35);
		vf.getValidator("importo", importo).notNull().minOrEquals(BigDecimal.ZERO).maxOrEquals(BigDecimal.valueOf(999999.99));

		if(codEntrata != null) {
			vf.getValidator("codEntrata", codEntrata).notNull().minLength(1).maxLength(35);
			try {
				vf.getValidator("tipoBollo", tipoBollo).isNull();
				vf.getValidator("hashDocumento", hashDocumento).isNull();
				vf.getValidator("provinciaResidenza", provinciaResidenza).isNull();
				vf.getValidator("ibanAccredito", ibanAccredito).isNull();
				vf.getValidator("ibanAppoggio", ibanAppoggio).isNull();
				vf.getValidator("tipoContabilita", tipoContabilita).isNull();
				vf.getValidator("codiceContabilita", codiceContabilita).isNull();
			} catch (ValidationException ve) {
				throw new ValidationException("Valorizzato codEntrata. " + ve.getMessage());
			}

			return;
		}

		if(tipoBollo != null) {
			vf.getValidator("tipoBollo", tipoBollo).notNull();
			vf.getValidator("hashDocumento", hashDocumento).notNull().minLength(1).maxLength(70);
			vf.getValidator("provinciaResidenza", provinciaResidenza).notNull().pattern("[A-Z]{2,2}");

			try {
				vf.getValidator("ibanAccredito", ibanAccredito).isNull();
				vf.getValidator("ibanAppoggio", ibanAppoggio).isNull();
				vf.getValidator("tipoContabilita", tipoContabilita).isNull();
				vf.getValidator("codiceContabilita", codiceContabilita).isNull();
			} catch (ValidationException ve) {
				throw new ValidationException("Valorizzato tipoBollo. " + ve.getMessage());
			}

			return;
		}


		if(ibanAccredito != null) {
			vf.getValidator("ibanAccredito", ibanAccredito).notNull().pattern("[a-zA-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}");
			vf.getValidator("ibanAppoggio", ibanAppoggio).pattern("[a-zA-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}");;
			vf.getValidator("tipoContabilita", tipoContabilita).notNull();
			vf.getValidator("codiceContabilita", codiceContabilita).notNull().pattern("\\S{3,138}");;

			try {
				vf.getValidator("hashDocumento", hashDocumento).isNull();
				vf.getValidator("provinciaResidenza", provinciaResidenza).isNull();
			} catch (ValidationException ve) {
				throw new ValidationException("Valorizzato ibanAccredito. " + ve.getMessage());
			}
		}

	}
}


