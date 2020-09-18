/*
  * Queste funzioni servono per aggiornare gli elementi della dashboard
  * che hanno i seguenti id:
  * id="clientiInSala"
  * id="tavoliLiberi"
  * id="theServiti"
  * id="clientiServiti"
*/
function updateClientiInSala(data) {
    document.getElementById('clientiInSala').textContent = data+"/2";
}

function updateTavoliLiberi(data) {
    document.getElementById('tavoliLiberi').textContent = data+"/2";
}

function updateTheServiti(data) {
    document.getElementById('theServiti').textContent = data;
}

function updateClientiServiti(data) {
    document.getElementById('clientiServiti').textContent = data;
}

/*
 * Questa funzione Ã¨ un adapter per mostrare il numero totale di tavoli liberi
 */
function adapterTavoliLiberi(table1, table2) {
    var tavoliLiberi = 0;
    if (table1 == "available")
        tavoliLiberi += 1;
    if (table2 == "available")
        tavoliLiberi += 1;
    updateTavoliLiberi(tavoliLiberi);
}

/*
 * Questa funzione serve per aggiornare la posizione del robot sulla mappa.
 * Disegna una R in grassetto alle coordinate passate con cols e rows.
 */
function updateMap(cols, rows, direction) {
    var table = document.getElementById("mappa");
    var nomeCella = "cell"+cols+rows;
    for (var i = 0, row; row = table.rows[i]; i++) {
        //itera sulle righe
        for (var j = 0, col; col = row.cells[j]; j++) {
            //itera sulle colonne (quindi le celle)
            col.textContent = "";
        }  
    }
    direction = direction.replace("Dir", "");
    document.getElementById(nomeCella).innerHTML = "<strong>R</strong> <i class='fa fa-long-arrow-"+direction+"'></i>";
}


/*
 * Questa funzione serve per aggiornare lo stato corrente del waiter,
 * colorando di verde lo stato corrente.
 * id="athome"
 * id="reachEntranceDoor"
 * id="convoy"
 * id="takingOrder"
 * id="serving"
 * id="collectingPayment"
 * id="sanitizing"
 * id="returnHome"
 */
function updateStatus(stato) {
    var table = document.getElementById("stati");
    var coloreSelezionato = "#ddffdd";
    var coloreBase = "white";
    for (var i = 0, row; row = table.rows[i]; i++) {
        row.style.backgroundColor = coloreBase;
    }

    if(stato == "convoyTable" || stato == "convoyExit"){
        stato = "convoy"
    } else if(stato == "collectingDrink" || stato == "bringingDrinkToClient" || stato == "servingDrinkToClient") {
        stato = "serving"
    } else if(stato == "returnHomeFromExit") {
    	stato = "returnHome"
    } else if(stato == "stepAhead" || stato == "turnLeft" || stato == "turnRight") {
    	stato = "movingAround"
    }

    document.getElementById(stato).style.backgroundColor = coloreSelezionato;
}

/*
 * Questa funzione serve per aggiornare lo stato corrente del barman,
 * 
 */
function updateBarmanStatus(stato) {
    document.getElementById("barmanStatus").innerText = "Barman status: " + stato;
}

/*
 * Questa funzione serve per aggiornare lo stato corrente di un singolo cliente,
 * clientNumber = numero intero che identifica il client
 */
function updateAClientStatus(item, index) {
    var clientNumber = index+1;
    var clientName = "clientState"+clientNumber;
    var clientButton = "clientButton" + clientNumber;
    document.getElementById(clientName).innerText = item;
    if (item == "exiting") {
        document.getElementById(clientButton).disabled = true;
    }
}
/*
 * Questa funzione serve per aggiornare lo stato corrente dei clienti,
 * clientsStatus = array con lo stato dei clienti
 */
function updateClientsStatus(clientsStatus) {
    clientsStatus.forEach(updateAClientStatus);
}



/*
{
    "serviceDeskState":"d",
    "teaServed":"l"
    "robotState":"a",
    "xRobot":"r",
    "yRobot":"c",
    "direction" : "g",
    "totalNumberOfClients":"e",
    "clientsInTheRoom":"f",
    "teatable2State":"c",
    "teatable1State":"b",
    "clientsState" : [ { "id" : "1", "state" : "stato1"},  { "id" : "2", "state" : "stato1"} ] 
}
*/
function updateDashboard(message) {
    console.log(message);

    updateStatus(message.robotState);
    updateClientiServiti(message.totalNumberOfClients);
    updateClientiInSala(message.clientsInTheRoom);
    adapterTavoliLiberi(message.teatable1State, message.teatable2State);
    updateTheServiti(message.teaServed);
    updateMap(message.yRobot, message.xRobot, message.direction);
    updateBarmanStatus(message.serviceDeskState);
    updateClientsStatus(message.clientsState);
}



/*
 * Queste due funzioni servono a cambiare pagina
 */
function resetButtons(buttonName) {
    var pulsanti = document.getElementById("viewSelector").children;
    var i;
    for (i = 0; i < pulsanti.length; i++) {
        pulsanti[i].classList.remove("w3-blue-gray")
    }
    document.getElementById(buttonName).classList.add("w3-blue-gray");
}

function selectPage(pageName, buttonName) {
    resetButtons(buttonName);
    var pagine = document.getElementById("pageContainer").children;
    for (i = 0; i < pagine.length; i++) {
        pagine[i].style.display = "none";
    }
    document.getElementById(pageName).style.display = "block";
    document.getElementById("crediti").style.display = "block";
}
