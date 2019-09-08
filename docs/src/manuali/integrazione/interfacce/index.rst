.. _integrazione_interfacce:


Gestione automatica delle interfacce
====================================

Una delle caratteristiche più interessanti di GovPay è quella di poter essere personalizzato tramite linguaggi formali atti a descrivere le interefacce verso il debitore: è possibile quindi definire le interfacce di pagamento (e anche quelle di inoltro, ad esempio, via mail della ricevuta telematica) attraverso file di testo con sintassi standard.
Nel seguito della sezione si affronterà un caso pratico di definizione di intefaccia di una pendenza caricata su un Ente Creditore.

I Linguaggi di definizione utilizzati
-----------------------------------
La definizione delle interfacce e dei processi di elaborazione e validazione si appoggia ai seguenti standard industriali assai consolidati:

* `Angular Json <https://angular.io/>`_
* `Freemarker <https://freemarker.apache.org/>`_

Nel primo caso (Angular) esiste una `risorsa web <https://hamidihamza.com/Angular6-json-schema-form/>`_ che consente di verificare online il form che si sta definendo.
Si noti come le sezioni seguenti non possano né vogliano sostituirsi a manualistica e tutorial per i *framework* prima citati: l'intento è solo quello di presentare alcuni casi d'uso frequenti nell'utilizzo e di semplice estensione.


Personalizzazione del tipo pendenza
-----------------------------------

La pendenza può essere personalizzata, ad esempio, in relazione alla sua instanza per l'Ente Creditore. Cerchiamo di modificare la sezione della Pendenza Sanzione Amministrativa in relazione a un Ente Creditore. Andando sull'Ente creditore:

.. figure:: ../_images/INT07_ModificaSanzioneAmministrativaDiComuneDimostrativo.png
   :align: center
   :name: ModificaLayoutPendenza

   Modifica del Tipo Pendenza all'interno di un Ente Ceditore

Selezionando questa modifica, il sistema propone

.. figure:: ../_images/INT06_CaratteristichePendenzaConInterfacceAutomatiche.png
   :align: center
   :name: Interfaccepersonalizzabilineltipopendenza

   Interfacce personalizzabili attraverso script nel Tipo Pendenza

Le interfacce personalizzabili sono

.. csv-table:: 
  :header: "Campo", "Significato", "Note"
  :widths: 40,40,20
  
  "Layout Form Dati", "Definizione dell'nterfaccia di caricamento dei dati dell'istanza della pendenza", "Angular Json"
  "Validazione", "Interfaccia di validazione dei dati dell'istanza della pendenza", "Angular Json"
  "Trasformazione", "Motore di traformazione dei dati dell'istanza della pendenza", "Freemarker"
  "Promemoria avviso di pagamento: oggetto", "Definizione dell'oggetto della mail del promemoria avviso di pagamento", "Freemarker"
  "Promemoria avviso di pagamento: messaggio", "Definizione del messaggio della mail del promemoria avviso di pagamento", "Freemarker"
  "Promemoria ricevuta telematica: oggetto", "Definizione dell'oggetto della mail del promemoria ricevuta telematica", "Freemarker"
  "Promemoria ricevuta telematica: messaggio", "Definizione del messaggio della mail del promemoria ricevuta telematica", "Freemarker"

Layout Forma Dati
~~~~~~~~~~~~~~~~~

Tramite lo script citato a seguire viene implementata un'interfaccia con i seguenti campi:

.. csv-table:: 
  :header: "Campo", "Note"
  :widths: 50,50
  
  "Numero verbale", "Campo libero per l'immissione del numero verbale"
  "Anagrafica Debitore", "Campo libero per l'immissione di nome e congnome del debitore, come evidenziato anche dall'etichetta"
  "Codice Fiscale Debitore", "Campo validato formalmente per l'immissione del codice fiscale del debitore"
  "eMail Debitore", "Campo validato formalmente (dev'essere un'email) per l'immissione della mail del debitore"
  "Tipo Violazione", "Campo a selezione in cui il debitore deve scegliere il tipo di violazione"

Il risultato finale è il seguente:

.. figure:: ../_images/INT08_FormDiImmissioneDati.png
   :align: center
   :name: FormLayoutCompleto

A titolo di esempio si consideri il campo di selezione, i cui valori sono stati inseriti nel json nella seguente sezione:

"tipoSanzione": {
		"type": "string",
		"enum": ["Violazione art. 123", "Violazione art. 456", "Violazione art. 789"]
		}

Il risultato è il seguente

.. figure:: ../_images/INT09_FormDiImmissionedatiConEvidenzaCombo.png
   :align: center
   :name: SceltaTipoViolazione

   Selezione del tipo di violazione

Lo script completo è (si noti le parti di definizione dei pattern di email e codice fiscale)

{
	"schema": {
		   "type": "object",
		   "required": [
			"idPendenza",
			"soggettoPagatore",
			"tipoSanzione"
		    ],
		   "properties": {
			"idPendenza": {
				"type": "string",
				"pattern": "[A-Za-z0-9\\-_]{1,35}"
			},
			"soggettoPagatore": {
				"type": "object",
				"required": [
					"identificativo",
					"anagrafica"
				],
				"properties": {
					"identificativo": {
						"type": "string",
						"pattern": "[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]"
					},
					"anagrafica": {
						"type": "string"
					},
					"email": {
						"type": "string",
						"pattern": "[A-Za-z0-9_]+([\\-\\+\\.'][A-Za-z0-9_]+)*@[A-Za-z0-9_]+([\\-\\.][A-Za-z0-9_]+)*\\.[A-Za-z0-9_]+([\\-\\.][A-Za-z0-9_]+)*"
					}
				}
			},
			"tipoSanzione": {
				"type": "string",
				"enum": ["Violazione art. 123", "Violazione art. 456", "Violazione art. 789"]
			}
		}
	},
	"layout": [
		{
			"key": "idPendenza",
			"title": "Numero verbale"
		},
		{
			"key": "soggettoPagatore.anagrafica",
			"title": "Anagrafica debitore",
			"placeholder": "Nome e cognome"
		},
		{
			"key": "soggettoPagatore.identificativo",
			"title": "Codice fiscale debitore"
		},
		{
			"key": "soggettoPagatore.email",
			"title": "E-Mail debitore",
			"placeholder": "Se indicato riceverà l'avviso di pagamento"
		},
		{
			"key": "tipoSanzione",
			"title": "Tipo di violazione"
		}
	]
}

            
            
Validazione
~~~~~~~~~~~

Lo script di validazione è ancora espresso nel formato json angular schema. Nel nostro esempio si presenta in questo modo:

            
              {
	"schema": {
		"type": "object",
		"required": [
			"idPendenza",
			"soggettoPagatore",
			"tipoSanzione"
		],
		"properties": {
			"idPendenza": {
				"type": "string",
				"pattern": "[A-Za-z0-9\\-_]{1,35}"
			},
			"soggettoPagatore": {
				"type": "object",
				"required": [
					"identificativo",
					"anagrafica"
				],
				"properties": {
					"identificativo": {
						"type": "string",
						"pattern": "[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]"
					},
					"anagrafica": {
						"type": "string"
					},
					"email": {
						"type": "string",
						"pattern": "[A-Za-z0-9_]+([\\-\\+\\.'][A-Za-z0-9_]+)*@[A-Za-z0-9_]+([\\-\\.][A-Za-z0-9_]+)*\\.[A-Za-z0-9_]+([\\-\\.][A-Za-z0-9_]+)*"
					}
				}
			},
			"tipoSanzione": {
				"type": "string",
				"enum": ["Violazione art. 123", "Violazione art. 456", "Violazione art. 789"]
			}
		}
	},
	"layout": [
		{
			"key": "idPendenza",
			"title": "Numero verbale"
		},
		{
			"key": "soggettoPagatore.anagrafica",
			"title": "Anagrafica debitore",
			"placeholder": "Nome e cognome"
		},
		{
			"key": "soggettoPagatore.identificativo",
			"title": "Codice fiscale debitore"
		},
		{
			"key": "soggettoPagatore.email",
			"title": "E-Mail debitore",
			"placeholder": "Se indicato riceverà l'avviso di pagamento"
		},
		{
			"key": "tipoSanzione",
			"title": "Tipo di violazione"
		}
	]
}

            
Un'osservazione attenta dello script ne mostra la sostanziale equivalenza con quello di definizione del layout. In effetti lo script afferma che:
1. I campi necessari sono idPendenza, soggettoPagatore e tipoSanzione, che si mappano su quelli definiti nel punto precedente
2. idPendenza è una stringa alfanumerica lunga fino a 35 caratteri
3. l'email non è necessaria: per essa è comunque fornita un'espressione regolare che impedisce l'immissione di email non valide
4. Il tipo sanzione ammette solo tre valori (123, 456, 789)

In effetti, adoperando il simulatore segnalato prima si ottiene il seguente risultato


.. figure:: ../_images/INT10_FormValidazione.png
   :align: center
   :name: Validazione
            
Si nota dai messaggi che il simulatore mostra come le componenti di validazione siano correttamente interpretate.

Ci si potrebbe chiedere il perchè di questa ripetizione (Layout Form Dati e Validazione): la ragione di questa necessità risiede nel comportamento non omogeneo dei browser. La prima validazione è infatti demandata al lato client della filiera applicativa, che non ha alcun contratto sull'esecuzione dei controlli. In altre parole, la piattaforma non ha alcuna sicurezza che i controlli immessi nel Layout Form saranno davvero effettuati lato client: l'unica strategia davvero cautelativa, in casi come questi, è pertanto quella di avere uno strato server di gestione degli errori che, prima di interpretare i dati e trasformarli, provveda alla validazione di quanto immesso anche se arrivato al server senza controlli clienti (comportamento del browser).
Per i motivi appena descritti, si consiglia sempre di implementare i controlli formali anche in questa sezione.


Trasformazione
~~~~~~~~~~~~~~

Questa sezione provvede all'instradamento, previa loro trasformazione, dei dati immessi nel form verso i servizi che li consumeranno. Vediamone un esempio complessivo i cui blocchi commenteremo in modo dettagliato:
            
<#assign jsonUtilities = class["org.openspcoop2.utils.json.JSONUtils"].getInstance()>
<#assign request = jsonUtilities.getAsNode(jsonPath.read("$"))>
<#assign calendar = class["java.util.Calendar"]>
<#assign now = new("java.util.Date")>
<#assign calendarInstance = calendar.getInstance()>
<#assign xxx = calendarInstance.setTime(now)!>
<#assign yyy = calendarInstance.add(calendar.MONTH, 1)!>
<#assign zzz = calendarInstance.set(calendar.DATE, calendarInstance.getActualMaximum(calendar.DAY_OF_MONTH))!>
<#assign dataValidita = calendarInstance.getTime()?string("yyyy-MM-dd")>
<#if request.get("tipoSanzione").asText() = "Violazione art. 123">
	<#assign importo = "54.01">
<#elseif request.get("tipoSanzione").asText() = "Violazione art. 456">
	<#assign importo = "123.6">
<#elseif request.get("tipoSanzione").asText() = "Violazione art. 678">
	<#assign importo = "307">

<#setting locale="en_US">
{
	"idA2A": "A2A-DEMO",
	"idPendenza": "${request.get("idPendenza").asText()}",
	"idDominio": "${pathParams["idDominio"]}",
	"idTipoPendenza": "${pathParams["idTipoPendenza"]}",
 	"causale": "Sanzione amministrativa - Verbale n. ${request.get("idPendenza").asText()}",
	"soggettoPagatore": {
		"tipo": "F",
		"identificativo": "${request.get("soggettoPagatore").get("identificativo").asText()}",
		"anagrafica": "${request.get("soggettoPagatore").get("anagrafica").asText()}",
		"email": "${request.get("soggettoPagatore").get("email").asText()}"
	},
   	"importo": "${importo}",
	"dataValidita": "${dataValidita}",
	"dataScadenza": "${dataValidita}",
	"tassonomiaAvviso": "Servizi erogati dal comune",
	"voci": [
		{
			"idVocePendenza": "1",
			"importo": "${importo}",
			"descrizione": "${request.get("tipoSanzione").asText()}",
			"ibanAccredito": "IT02L1234500000111110000001",
			"tipoContabilita": "ALTRO",
			"codiceContabilita": "${pathParams["idTipoPendenza"]}"
		}
	]
}
            
            





Promemoria avviso di pagamento
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
  
  
  
  
  
Promemoria ricevuta telematica
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
