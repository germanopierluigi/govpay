Feature: Errori di autorizzazione inserimento pendenza

Background: 

* call read('classpath:utils/common-utils.feature')
* call read('classpath:configurazione/v1/anagrafica_estesa.feature')
* def idPendenza = getCurrentTimeMillis()
* def pendenzaPutMulti = read('msg/pendenza-put_multivoce_bollo.json')
* def pendenzaPutMono = read('msg/pendenza-put_monovoce_riferimento.json')
* def pendenzeBaseurl = getGovPayApiBaseUrl({api: 'backoffice', versione: 'v1', autenticazione: 'basic'})


Scenario: Caricamento dominio non autorizzato

* set applicazione.domini = ['#(idDominio_2)']

Given url backofficeBaseurl
And path 'applicazioni', idA2A 
And headers gpAdminBasicAutenticationHeader
And request applicazione
When method put
Then status 200

* call read('classpath:configurazione/v1/operazioni-resetCache.feature')

Given url pendenzeBaseurl
And path '/pendenze', idA2A, idPendenza
And headers idA2ABasicAutenticationHeader
And request pendenzaPutMono
When method put
Then status 403
And match response == 
"""
{ 
	categoria: 'AUTORIZZAZIONE',
	codice: '403000',
	descrizione: 'Operazione non autorizzata',
	dettaglio: '#notnull'
}
"""
And match response.dettaglio contains '#(idDominio)'

Scenario: Caricamento avviso con voce autodeterminata senza autorizzazione

* set applicazione.tipiPendenza = ['#(codEntrataSegreteria)']

Given url backofficeBaseurl
And path 'applicazioni', idA2A 
And headers gpAdminBasicAutenticationHeader
And request applicazione
When method put
Then status 200

* call read('classpath:configurazione/v1/operazioni-resetCache.feature')

* set pendenzaPutMono.voci = 
"""
[
	{
		idVocePendenza: '1',
		importo: 100.99,
		descrizione: 'Diritti e segreteria',
		ibanAccredito: '#(ibanAccredito)',
		tipoContabilita: 'ALTRO',
		codiceContabilita: 'XXXXX'
	}
]
"""
Given url pendenzeBaseurl
And path '/pendenze', idA2A, idPendenza
And headers idA2ABasicAutenticationHeader
And request pendenzaPutMono
When method put
Then status 403
And match response == 
"""
{ 
	categoria: 'AUTORIZZAZIONE',
	codice: '403000',
	descrizione: 'Operazione non autorizzata',
	dettaglio: '#notnull'
}
"""