Feature: Censimento tipiPendenza

Background:

* callonce read('classpath:utils/common-utils.feature')
* callonce read('classpath:configurazione/v1/anagrafica_estesa.feature')
* def basicAutenticationHeader = getBasicAuthenticationHeader( { username: govpay_backoffice_user, password: govpay_backoffice_password } )
* def backofficeBaseurl = getGovPayApiBaseUrl({api: 'backoffice', versione: 'v1', autenticazione: 'basic'})
* def operatore = 
"""
{
  ragioneSociale: 'Mario Rossi',
  domini: ['#(idDominio)'],
  tipiPendenza: ['#(codLibero)'],
  acl: [ { servizio: 'Pagamenti', autorizzazioni: [ 'R', 'W' ] } ],
  abilitato: true
}
"""

Scenario: Aggiunta di un operatore

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Scenario Outline: Modifica di un operatore (<field>)

* set operatore.<field> = <value>
* def checkValue = <value> != null ? <value> : '#notpresent'

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.<field> == checkValue

Examples:
| field | value | 
| ragioneSociale | 'Nuova Ragione' |
| acl | [ { servizio: 'Anagrafica PagoPA', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Anagrafica Creditore', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Anagrafica Applicazioni', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Anagrafica Ruoli', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Pagamenti', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Pendenze', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Rendicontazioni e Incassi', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Giornale degli Eventi', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Configurazione e manutenzione', autorizzazioni: [ 'R', 'W' ] } ] |
| acl | [ { servizio: 'Anagrafica PagoPA', autorizzazioni: [ 'R', 'W' ] },  { servizio: 'Anagrafica Creditore', autorizzazioni: [ 'W' ] } ] |
| abilitato | true |
| abilitato | false |

Scenario: Modifica di un operatore (domini)

* set operatore.domini = ['#(idDominio)','#(idDominio_2)']

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.domini == '#[2]'
And match response.domini[0] contains { idDominio: '#(idDominio)' }
And match response.domini[1] contains { idDominio: '#(idDominio_2)' } 

Scenario: Modifica di un operatore (domini)

* set operatore.domini = ['#(idDominio)','*']

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.domini == [ { idDominio: '*', ragioneSociale: 'Tutti' } ]

Scenario Outline: Modifica di un operatore (domini)

* set operatore.<field> = <value>

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.domini == [ ]

Examples:
| field | value | 
| domini | null |
| domini | [ ] |

Scenario: Modifica di un operatore (tipiPendenza)

* set operatore.tipiPendenza = ['#(codEntrataBollo)','#(codLibero)']

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.tipiPendenza == '#[2]'
And match response.tipiPendenza[0] contains { idTipoPendenza: '#(codEntrataBollo)' }
And match response.tipiPendenza[1] contains { idTipoPendenza: '#(codLibero)' } 

Scenario: Modifica di un operatore (tipiPendenza)

* set operatore.tipiPendenza = ['#(codEntrataBollo)','*']

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.tipiPendenza == '#[1]'
And match response.tipiPendenza[0] contains { idTipoPendenza: '*', descrizione: 'Tutti' } 

Scenario Outline:  Modifica di un operatore (tipiPendenza)

* set operatore.<field> = <value>

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', 'MarioRossi'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.tipiPendenza == [ ]

Examples:
| field | value | 
| tipiPendenza | [ ] |
| tipiPendenza | null |

Scenario: Caratteri codificati nel field (idOperatore)
# ' &*' = '%20%26%2A'

Given url backofficeBaseurl
And path 'operatori', '%20%26%2A' 
And headers basicAutenticationHeader
And request operatore
When method put
Then assert responseStatus == 200 || responseStatus == 201

Given url backofficeBaseurl
And path 'operatori', '%20%26%2A'
And headers basicAutenticationHeader
When method get
Then status 200
And match response.principal == ' &*'