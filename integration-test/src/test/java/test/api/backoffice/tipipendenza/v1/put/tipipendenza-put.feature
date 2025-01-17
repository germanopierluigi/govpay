Feature: Censimento tipiPendenza

Background:

* callonce read('classpath:utils/common-utils.feature')
* callonce read('classpath:configurazione/v1/anagrafica.feature')
* def basicAutenticationHeader = getBasicAuthenticationHeader( { username: govpay_backoffice_user, password: govpay_backoffice_password } )
* def backofficeBaseurl = getGovPayApiBaseUrl({api: 'backoffice', versione: 'v1', autenticazione: 'basic'})
* def tipoPendenza = 
"""
{
  "descrizione": "Sanzione codice della strada",
  "tipo": "dovuta",
  "codificaIUV": "030",
  "pagaTerzi": true
}
"""          

Scenario: Aggiunta di un tipoPendenza

Given url backofficeBaseurl
And path 'tipiPendenza', 'SCDS'
And headers basicAutenticationHeader
And request tipoPendenza
When method put
Then assert responseStatus == 200 || responseStatus == 201

Scenario Outline: Modifica di una tipoPendenza (<field>)

* set tipoPendenza.<field> = <value>
* def checkValue = <value> != null ? <value> : '#notpresent'

Given url backofficeBaseurl
And path 'tipiPendenza', 'SCDS'
And headers basicAutenticationHeader
And request tipoPendenza
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'tipiPendenza', 'SCDS'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.<field> == checkValue

Examples:
| field | value | 
| descrizione | 'Nuova descrizione' |
| tipo | 'spontanea' |
| tipo | 'dovuta' |
| codificaIUV | null |
| codificaIUV | '090' |
| pagaTerzi | true |
| pagaTerzi | false |
| schema | { schema : 'aaaa'} |
| schema | null |
| datiAllegati | { datiAllegati : 'aaaa'} |
| datiAllegati | 'aaaa' |
| datiAllegati | [ 'a', 'b' ] |
| datiAllegati | 1 |
| datiAllegati | [ 1, 2 ] |
| datiAllegati | null |