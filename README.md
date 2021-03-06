# iss2020TemaFinale
Esame di Ingegneria dei Sistemi Software A.A. 2019/2020

[**Project Website**](https://ugoleone.github.io/iss2020TemaFinale/)

## Introduzione

[**Analisi**](https://ugoleone.github.io/iss2020TemaFinale/Analisi/analisi.html)

[**Requisiti**](https://ugoleone.github.io/iss2020TemaFinale/Requisiti/Requisiti.html)

Il *manager* di una sala da the (*tearoom*) vorrebbe regolare l'accesso al servizio impiegando un DDR robot (*waiter*).

La *tearoom* è una stanza rettangolare che include:

- una porta di entrata (*entrancedoor*) per entrare nella stanza ed una di uscita (*exitdoor*) per uscire;
- un numero `N (N=2)` di tavoli (*teatable*);
- una *servicearea* che include a sua volta un *servicedesk* al quale lavora un *barman*;
- una *hall* provvista di un *presencedetector*, ad esempio un dispositivo (come un sonar) che può rilevare la presenza di una persona (o di qualche altra entità) davanti a se.

Il *waiter* può muoversi liberamente lungo i bordi della stanza, poichè lungo il perimetro non ci sono ostacoli.

Il *manager* deve poter visualizzare lo stato corrente (*current state*) della *tearoom* usando un browser connesso ad un server web associato all'applicazione.

<img src="docs/Analisi/img/tearoom20.png" alt="tearoom20" style="zoom:50%;" />

<img src="docs/HTMLResources/img/guiScreen.png" alt="WEB_GUI" style="zoom: 39%;" />



|                             MBot                             |                         Custom Robot                         |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="docs/Sprint4/img/mbot1.png" alt="mbot" style="zoom: 45%;" /> | <img src="docs/Sprint4/img/ubot2.jpeg" alt="ubot" style="zoom: 45%;" /> |



## Deployment su robot virtuale locale (UNIX)

1. Scaricare il *deployment.zip* più recente al link [releases](https://github.com/ugoleone/iss2020TemaFinale/releases)

2. Estrarre la cartella `deployment`

3. Entrare nella cartella `deployment`

4.  Controllare il seguente file di configurazione:

   - All'interno di `it.unibo.trc19.sprint4.tearoom-1.0/bin/tearoom.pl` sostituire la seconda riga con la riga `context(ctxbasicrobot, "localhost",  "TCP", "PORT").`, dove `PORT` è la porta del *basicrobot* (default = 8020)

5. Aprire un terminale all'interno della cartella `deployment`

6. Digitare nel terminale ed inviare i seguenti comandi (**Avvio del robot virtuale**) 

   ```shell
   cd it.unibo.virtualRobot2020/node/WEnv/server/src
   node main 8999
   ```

7. Aprire un secondo terminale all'interno della cartella `deployment`, digitare nel terminale ed inviare il seguente comando (**Avvio della pagina web con Spring**)  

   ```shell
   ./robotWeb2020-boot-1.0/bin/robotWeb2020
   ```

8. Aprire un terzo terminale all'interno della cartella `deployment`, digitare nel terminale ed inviare il seguente comando (**Avvio del basicrobot**) 

   ```shell
   ./it.unibo.qak20.basicrobot-1.0/bin/it.unibo.qak20.basicrobot
   ```

9.  Aprire un quarto terminale all'interno della cartella `deployment`, digitare nel terminale ed inviare il seguente comando (**Avvio della tearoom**) 

   ```shell
   ./it.unibo.trc19.sprint4.tearoom-1.0/bin/it.unibo.trc19.sprint4.tearoom
   ```

10. Buon divertimento!

## Deployment su robot fisico (UNIX)

1. Scaricare il *deployment.zip* più recente al link [releases](https://github.com/ugoleone/iss2020TemaFinale/releases) su PC

2. Estrarre la cartella `deployment` su PC

3. Entrare nella cartella `deployment` su PC

4. Cambiare i seguenti file di configurazione:

   - All'interno di `it.unibo.qak20.basicrobot-1.0/bin/basicrobotConfig.json`, sostituire la prima riga con la riga `{"type":"realmbot", "port":"/dev/ttyUSB0", "host":"localhost"}` se si sta usando MBOT
   - All'interno di `it.unibo.qak20.basicrobot-1.0/bin/basicrobotConfig.json`, sostituire la prima riga con la riga `{"type":"realnano", "port":"", "host":"localhost"}` se si sta usando invece un robot di tipo "nanoBot"
   - All'interno di `it.unibo.trc19.sprint4.tearoom-1.0/bin/tearoom.pl` sostituire la seconda riga con la riga `context(ctxbasicrobot, "IP",  "TCP", "PORT").`, dove `IP` è l'IP del RaspberryPi in uso e `PORT` è la porta del basicrobot (default = 8020)

5. Copiare la cartella `it.unibo.qak20.basicrobot-1.0` nel filesystem del Raspberry che controlla il robot fisico

6. Aprire un terminale all'interno della cartella `deployment` su PC

7. Digitare nel terminale ed inviare il seguente comando (**Avvio della pagina web con Spring**)  

   ```shell
   ./robotWeb2020-boot-1.0/bin/robotWeb2020
   ```

8. Aprire un terminale all'interno della cartella `it.unibo.qak20.basicrobot-1.0` su Raspberry

9. Digitare nel terminale ed inviare il seguente comando (**Avvio del basicrobot**) 

   ```shell
   ./it.unibo.qak20.basicrobot-1.0/bin/it.unibo.qak20.basicrobot
   ```

10. Aprire un secondo terminale all'interno della cartella `deployment` su PC, digitare nel terminale ed inviare il seguente comando (**Avvio della tearoom**) 

    ```shell
    ./it.unibo.trc19.sprint4.tearoom-1.0/bin/it.unibo.trc19.sprint4.tearoom
    ```

11. Buon divertimento!



## Piano di lavoro

Dalla analisi preliminare del sistema risulta necessario pianificare un piano di lavoro architettato in sprint, di seguito riportati.

| NOME                                                         | DESCRIZIONE                                                  | MOTIVAZIONE                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [**SPRINT 1**](https://ugoleone.github.io/iss2020TemaFinale/Sprint1/sprint1.html) | Si occupa della modellazione del sistema nell'ipotesi in cui operi un solo client e che compia unicamente le azioni previste dal suo comportamento standard, senza interferenze di alcun genere. | La scelta di questo primo sprint è motivata dalla volontà di simulare un primo scenario di base che preveda una interazione "controllata" e "prevista" del sistema, in modo da aggiungere nei successivi sprint i casi imprevisti. |
| [**SPRINT 2**](https://ugoleone.github.io/iss2020TemaFinale/Sprint2/sprint2.html) | Si occupa della definizione dello stato della stanza.  Questo sprint aggiunge al primo sprint una descrizione degli elementi della stanza, introducendo il concetto di **architettura esagonale**.  Inoltre in questo sprint si inizia a costruire il pannello di controllo da fornire al manager. | Dopo aver verificato le funzionalità base del sistema è necessario iniziare a dare una forma a quella che sarà l'architettura finale. |
| [**SPRINT 3**](https://ugoleone.github.io/iss2020TemaFinale/Sprint3/sprint3.html) | Si occupa della **concorrenza** fra client, ossia la presenza di più client nella *tearoom* contemporaneamente. e della gestione del tempo (**maxstaytime** e **waitingtime**). | Dopo aver costruito una prima versione dell'architettura finale, aggiungiamo al sistema ulteriori casi d'uso con lo scopo di avvicinarlo sempre più al prodotto finale richiesto. |
| [**SPRINT 4**](https://ugoleone.github.io/iss2020TemaFinale/Sprint4/sprint4.html) | Deployment del sistema e impiego di robot fisici oltre che del virtual robot fornito. Possibilità di interrompere alcuni task per riprenderli in seguito. | Questo sprint finale rende il sistema pronto per essere consegnato al cliente e ne dimostra il funzionamento su dei DDR reali. |




## Our Team

**Ugo Leone Cavalcanti** ( [ugoleone.cavalcanti@studio.unibo.it](MAILTO:ugoleone.cavalcanti@studio.unibo.it) )

**Amir Al Sadi** ( [amir.alsadi@studio.unibo.it](MAILTO:amir.alsadi@studio.unibo.it) )

**Nicolò Romandini** ( [nicolo.romandini@studio.unibo.it](MAILTO:nicolo.romandini@studio.unibo.it) )
