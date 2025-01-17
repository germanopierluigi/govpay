package it.govpay.bd.model.converter;

import java.util.List;

import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.utils.certificate.CertificateUtils;
import org.openspcoop2.utils.certificate.PrincipalType;

import it.govpay.bd.BasicBD;
import it.govpay.bd.model.Utenza;

public class UtenzaConverter {

	public static Utenza toDTO(it.govpay.orm.Utenza vo, List<Long> utenzaDominioLst, List<Long> utenzaTipiVersamentoLst, BasicBD bd) throws ServiceException {
		Utenza dto = new Utenza();
		dto.setPrincipal(vo.getPrincipal());
		dto.setPrincipalOriginale(vo.getPrincipalOriginale());
		dto.setAutorizzazioneDominiStar(vo.isAutorizzazioneDominiStar());
		dto.setAutorizzazioneTipiVersamentoStar(vo.isAutorizzazioneTipiVersStar());
		dto.setId(vo.getId());
		dto.setAbilitato(vo.isAbilitato());
		dto.setIdTipiVersamento(utenzaTipiVersamentoLst);
		dto.setIdDomini(utenzaDominioLst);
		dto.getDomini(bd);
		dto.getTipiVersamento(bd);

		return dto;
	}

	public static it.govpay.orm.Utenza toVO(it.govpay.model.Utenza dto)  {
		it.govpay.orm.Utenza vo = new it.govpay.orm.Utenza();
		vo.setId(dto.getId());
		try {
			vo.setPrincipal(CertificateUtils.formatPrincipal(dto.getPrincipal(), PrincipalType.subject));
		} catch (Exception e) {
			vo.setPrincipal(dto.getPrincipal());
		}
		vo.setPrincipalOriginale(dto.getPrincipalOriginale());
		vo.setAbilitato(dto.isAbilitato());
		vo.setAutorizzazioneDominiStar(dto.isAutorizzazioneDominiStar());
		vo.setAutorizzazioneTipiVersStar(dto.isAutorizzazioneTipiVersamentoStar());
		return vo;
	}

}
