-- Viene indicato come utente 'govpay'. Modificarlo secondo le proprie esigenze.
revoke delete on public.applicazioni from govpay;
revoke delete on public.authorities from govpay;
revoke delete on public.canali from govpay;
revoke delete on public.connettori from govpay;
revoke delete on public.domini from govpay;
revoke delete on public.eventi from govpay;
revoke delete on public.fr from govpay;
revoke delete on public.fr_applicazioni from govpay;
revoke delete on public.iban_accredito from govpay;
revoke delete on public.id_messaggio_relativo from govpay;
revoke delete on public.intermediari from govpay;
revoke delete on public.iuv from govpay;
revoke delete on public.notifiche from govpay;
revoke delete on public.notifiche_eventi from govpay;
revoke delete on public.operatori from govpay;
revoke delete on public.pagamenti from govpay;
revoke delete on public.portali from govpay;
revoke delete on public.psp from govpay;
revoke delete on public.rendicontazioni_senza_rpt from govpay;
revoke delete on public.rpt from govpay;
revoke delete on public.rr from govpay;
revoke delete on public.rt from govpay;
revoke delete on public.singoli_versamenti from govpay;
revoke delete on public.stazioni from govpay;
revoke delete on public.tipi_tributo from govpay;
revoke delete on public.transazioni from govpay;
revoke delete on public.tributi from govpay;
revoke delete on public.uo from govpay;
revoke delete on public.versamenti from govpay;
 
ALTER TABLE canali DROP CONSTRAINT fk_canali_1;
ALTER TABLE stazioni DROP CONSTRAINT fk_stazioni_1;
ALTER TABLE domini DROP CONSTRAINT fk_domini_1;
ALTER TABLE domini DROP CONSTRAINT fk_domini_2;
ALTER TABLE uo DROP CONSTRAINT fk_uo_1;
ALTER TABLE iban_accredito DROP CONSTRAINT fk_iban_accredito_1;
ALTER TABLE tributi DROP CONSTRAINT fk_tributi_1;
ALTER TABLE tributi DROP CONSTRAINT fk_tributi_2;
ALTER TABLE tributi DROP CONSTRAINT fk_tributi_3;
ALTER TABLE acl DROP CONSTRAINT fk_acl_1;
ALTER TABLE acl DROP CONSTRAINT fk_acl_2;
ALTER TABLE acl DROP CONSTRAINT fk_acl_3;
ALTER TABLE acl DROP CONSTRAINT fk_acl_4;
ALTER TABLE acl DROP CONSTRAINT fk_acl_5;
ALTER TABLE rr DROP CONSTRAINT fk_rr_1;
ALTER TABLE fr DROP CONSTRAINT fk_fr_1;
ALTER TABLE fr DROP CONSTRAINT fk_fr_2;
ALTER TABLE fr_applicazioni DROP CONSTRAINT fk_fr_applicazioni_1;
ALTER TABLE fr_applicazioni DROP CONSTRAINT fk_fr_applicazioni_2;
ALTER TABLE pagamenti DROP CONSTRAINT fk_pagamenti_1;
ALTER TABLE pagamenti DROP CONSTRAINT fk_pagamenti_2;
ALTER TABLE pagamenti DROP CONSTRAINT fk_pagamenti_3;
ALTER TABLE pagamenti DROP CONSTRAINT fk_pagamenti_4;
ALTER TABLE pagamenti DROP CONSTRAINT fk_pagamenti_5;
ALTER TABLE rendicontazioni_senza_rpt DROP CONSTRAINT fk_rendicontazioni_senza_rpt_1;
ALTER TABLE rendicontazioni_senza_rpt DROP CONSTRAINT fk_rendicontazioni_senza_rpt_2;
ALTER TABLE rendicontazioni_senza_rpt DROP CONSTRAINT fk_rendicontazioni_senza_rpt_3;

ALTER TABLE canali ADD CONSTRAINT fk_canali_1 FOREIGN KEY (id_psp) REFERENCES psp(id);
ALTER TABLE stazioni ADD CONSTRAINT fk_stazioni_1 FOREIGN KEY (id_intermediario) REFERENCES intermediari(id);
ALTER TABLE domini ADD CONSTRAINT fk_domini_1 FOREIGN KEY (id_stazione) REFERENCES stazioni(id);
ALTER TABLE domini ADD CONSTRAINT fk_domini_2 FOREIGN KEY (id_applicazione_default) REFERENCES applicazioni(id);
ALTER TABLE uo ADD CONSTRAINT fk_uo_1 FOREIGN KEY (id_dominio) REFERENCES domini(id);
ALTER TABLE iban_accredito ADD CONSTRAINT fk_iban_accredito_1 FOREIGN KEY (id_dominio) REFERENCES domini(id);
ALTER TABLE tributi ADD CONSTRAINT fk_tributi_1 FOREIGN KEY (id_dominio) REFERENCES domini(id);
ALTER TABLE tributi ADD CONSTRAINT fk_tributi_2 FOREIGN KEY (id_iban_accredito) REFERENCES iban_accredito(id);
ALTER TABLE tributi ADD CONSTRAINT fk_tributi_3 FOREIGN KEY (id_tipo_tributo) REFERENCES tipi_tributo(id);
ALTER TABLE acl ADD CONSTRAINT fk_acl_1 FOREIGN KEY (id_applicazione) REFERENCES applicazioni(id);
ALTER TABLE acl ADD CONSTRAINT fk_acl_2 FOREIGN KEY (id_portale) REFERENCES portali(id);
ALTER TABLE acl ADD CONSTRAINT fk_acl_3 FOREIGN KEY (id_operatore) REFERENCES operatori(id);
ALTER TABLE acl ADD CONSTRAINT fk_acl_4 FOREIGN KEY (id_dominio) REFERENCES domini(id);
ALTER TABLE acl ADD CONSTRAINT fk_acl_5 FOREIGN KEY (id_tipo_tributo) REFERENCES tipi_tributo(id);
ALTER TABLE rr ADD CONSTRAINT fk_rr_1 FOREIGN KEY (id_rpt) REFERENCES rpt(id);
ALTER TABLE fr ADD CONSTRAINT fk_fr_1 FOREIGN KEY (id_psp) REFERENCES psp(id);
ALTER TABLE fr ADD CONSTRAINT fk_fr_2 FOREIGN KEY (id_dominio) REFERENCES domini(id);
ALTER TABLE fr_applicazioni ADD CONSTRAINT fk_fr_applicazioni_1 FOREIGN KEY (id_applicazione) REFERENCES applicazioni(id);
ALTER TABLE fr_applicazioni ADD CONSTRAINT fk_fr_applicazioni_2 FOREIGN KEY (id_fr) REFERENCES fr(id);
ALTER TABLE pagamenti ADD CONSTRAINT fk_pagamenti_1 FOREIGN KEY (id_rpt) REFERENCES rpt(id);
ALTER TABLE pagamenti ADD CONSTRAINT fk_pagamenti_2 FOREIGN KEY (id_singolo_versamento) REFERENCES singoli_versamenti(id);
ALTER TABLE pagamenti ADD CONSTRAINT fk_pagamenti_3 FOREIGN KEY (id_fr_applicazione) REFERENCES fr_applicazioni(id);
ALTER TABLE pagamenti ADD CONSTRAINT fk_pagamenti_4 FOREIGN KEY (id_rr) REFERENCES rr(id);
ALTER TABLE pagamenti ADD CONSTRAINT fk_pagamenti_5 FOREIGN KEY (id_fr_applicazione_revoca) REFERENCES fr_applicazioni(id);
ALTER TABLE rendicontazioni_senza_rpt ADD CONSTRAINT fk_rendicontazioni_senza_rpt_1 FOREIGN KEY (id_fr_applicazione) REFERENCES fr_applicazioni(id);
ALTER TABLE rendicontazioni_senza_rpt ADD CONSTRAINT fk_rendicontazioni_senza_rpt_2 FOREIGN KEY (id_iuv) REFERENCES iuv(id);
ALTER TABLE rendicontazioni_senza_rpt ADD CONSTRAINT fk_rendicontazioni_senza_rpt_3 FOREIGN KEY (id_singolo_versamento) REFERENCES singoli_versamenti(id);

	