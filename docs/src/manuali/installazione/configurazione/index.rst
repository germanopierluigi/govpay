.. _inst_configurazione:

Configurazione dei moduli applicativi
=====================================

La fase di configurazione dei moduli applicativi consente di impostare i
dati di riferimento del proprio ambiente di installazione, tramite una
procedura basata sul modello wizard.

Download
--------

Scaricare l'ultima versione (binary release) di GovPay dal sito GitHub
https://github.com/link-it/GovPay.

Esecuzione dell'Installer
-------------------------

L'archivio di installazione può essere scompattato e il relativo
installer eseguito su un ambiente che non deve essere necessariamente
quello di destinazione. Infatti l'Installer non installa il prodotto ma
produce tutti gli elementi necessari che dovranno essere dispiegati
nell’ambiente di esercizio.

Per l'esecuzione dell'installer verificare ed eventualmente impostare la
variabile d’ambiente **JAVA_HOME** in modo che riferisca la directory
radice dell'installazione di Java. Eseguire quindi l'installer mandando
in esecuzione il file **install.sh** su Unix/Linux, oppure
**install.cmd** su Windows.

Avvio
~~~~~

L’Installer mostra all’avvio una pagina introduttiva.

Sono mostrate informazioni quali:

-  Nome e versione del prodotto
-  Informazioni sul copyright
-  Informazioni sulla licenza d'uso

Selezionando il pulsante Next si procede con la configurazione del
software.

.. figure:: ../_images/INS01_AvvioInstaller.png
   :alt: Pagina introduttiva all'avvio dell'Installer
   :align: center
   :name: PaginaIntroduttivaInstaller

   Pagina introduttiva all'avvio dell'Installer

Informazioni Preliminari
~~~~~~~~~~~~~~~~~~~~~~~~

La schermata "Informazioni Preliminari" consente di inserire i dati sul
contesto di installazione nell'ambiente di esercizio.

.. figure:: ../_images/INS02_InformazioniPreliminari.png
   :alt: Pagina relativa alle informazioni preliminari
   :align: center
   :name: InstallazioneInformazioniPreliminari

   Informazioni preliminari

Devono essere inserite le seguenti informazioni:

-  **Application Server:** la scelta dell'application server è vincolata su "WildFly 11.0"**
-  **Work Folder:** inserire il path assoluto della *directory*, presente nell'ambiente di destinazione, che sarà utilizzata da GovPay per accedere a dati accessori legati alle funzionalità opzionali, ad esempio:
   -  **file di configurazione personalizzati**
   -  **loghi dei psp**

-  **Log Folder**: inserire il path assoluto della directory, presente nell'ambiente di destinazione, che sarà utilizzata da GovPay per
   inserire i diversi file di tracciamento prodotti.

Informazioni Applicative
~~~~~~~~~~~~~~~~~~~~~~~~

-  *Username Amministratore:* indicare l'identificativo dell'utenza di
   amministrazione per l'accesso alla console di gestione e
   monitoraggio. Tipicamente si fornisce il "principal" dell'utenza
   applicativa registrata sull'Application Server, ma è in alternativa
   possibile indicare altre tipologie di utenze, come ad esempio
   identificate dal Certificato Client Digitale.
-  *Nome Dominio:* inserire l'hostname tramite il quale saranno
   raggiungibili i servizi di GovPay (ad esempio la console di
   monitoraggio).


.. figure:: ../_images/INS03_InformazioniApplicative.png
   :alt: Pagina relativa alle informazioni applicative
   :align: center
   :name: InstallazioneInformazioniApplicative

   Informazioni applicative


Il Database
~~~~~~~~~~~

Nella schermata "Il Database" si devono inserire i riferimenti per
l'accesso al database di esercizio di GovPay.

.. figure:: ../_images/INS04_InformazioniAccessoDatabase.png
   :alt: Pagina relativa alle informazioni di accesso al database
   :align: center
   :name: InstallazioneInformazioniAccessoDB
   
   Informazioni accesso al DB
   

-  **DB Platform:** selezionare la piattaforma RDBMS utilizzata
-  **Hostname**: indirizzo per raggiungere il database
-  **Porta**: la porta da associare all’hostname per la connessione al
   database
-  **Nome Database**: il nome dell’istanza del database a supporto di
   GovPay.
-  **Username**: l’utente con diritti di lettura/scrittura sul database
   sopra indicato.
-  **Password**: la password dell’utente del database.

.. note::
    Non è necessario che il database e l'utente indicato esistano in questa fase. Potranno essere creati nella successiva fase di dispiegamento purché i dati relativi coincidano con i valori inseriti in questi campi del wizard.

Installazione
~~~~~~~~~~~~~

Premendo il pulsante **Install** il processo di configurazione termina
con la produzione dei files necessari per l’installazione di GovPay che
verranno inseriti nella nuova directory **dist** creata al termine di
questo processo.

.. figure:: ../_images/INS05_InstallazioneTerminata.png
   :alt: Pagina relativa alla fine dell'installazione
   :align: center
   :name: InstallazioneTerminata
   
   Installazione terminata
   
   
I files presenti nella directory **dist** dovranno essere utilizzati
nella fase successiva di dispiegamento di GovPay.

