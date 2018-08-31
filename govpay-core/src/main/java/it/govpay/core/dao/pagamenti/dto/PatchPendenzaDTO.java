package it.govpay.core.dao.pagamenti.dto;

import it.govpay.model.IAutorizzato;

public class PatchPendenzaDTO extends AbstractPatchDTO  {
	
	public enum STATO_PENDENZA { ANNULLATO, DA_PAGARE}
	
	private String idA2a;
	private String idPendenza;

	public PatchPendenzaDTO(IAutorizzato user) {
		super(user);
	}

	public String getIdA2a() {
		return idA2a;
	}

	public void setIdA2a(String idA2a) {
		this.idA2a = idA2a;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

}