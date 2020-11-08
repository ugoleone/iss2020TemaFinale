/*
  * Queste funzioni servono per aggiornare gli elementi della dashboard
  * che hanno i seguenti id:
  * id="clientiInSala"
  * id="tavoliLiberi"
  * id="theServiti"
  * id="clientiServiti"
  * id="rinunce"
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

function updateClientiTotali(data) {
    document.getElementById('clientiTotali').textContent = data;
}

function updateRinunce(data) {
    document.getElementById('rinunce').textContent = data;
}

/*
 * Aggiorno informazioni sui tavoli
 */
/*
 * "teatable2State":{"state":"c", "remainingTime": "0", "seatedClient":"1"},
 * "teatable1State":{"state":"c", "remainingTime": "0", "seatedClient":"1"},
 */   
function updateTablesState(table1, table2) {
    var tavoliLiberi = 0;
    if (table1.state == "available") {
        tavoliLiberi += 1;
        document.getElementById("cell23").innerHTML = "";
        document.getElementById("cell23").style.background = "rgb(225, 254, 255)"
    }
    if (table2.state == "available") {
        tavoliLiberi += 1;
        document.getElementById("cell43").innerHTML = "";
        document.getElementById("cell43").style.background = "rgb(225, 254, 255)"
    }
    updateTavoliLiberi(tavoliLiberi);
    updateClientsInTable(table1, table2)
    updateTableCleaningState(table1, table2);
    
    if(table1.state == "busy" && table1.seatedClient == "-1") {
    	document.getElementById("cell23").innerHTML = "";
        document.getElementById("cell23").style.background = "rgb(225, 254, 255)"
    }
    if(table2.state == "busy" && table2.seatedClient == "-1") {
    	document.getElementById("cell43").innerHTML = "";
        document.getElementById("cell43").style.background = "rgb(225, 254, 255)"
    }
}

/*
 * Questa funzione disegna sulla mappa lo stato di pulizia dei tavoli
 */
function updateTableCleaningState(table1, table2) {
    //Se il tavolo e' in fase di pulizia si aggiorna la mappa per informare il manager
    if(table1.state == "dirty") {
        var generated = "<img src=\"cleanIcon.png\" style=\"height: 30px;\"></img>";
        var tempoResiduo = parseInt(table1.remainingTime/1000);
        var percDone = Math.round(mapRange(0, 60, 0, 100, tempoResiduo));
        var percUndone = percDone;
        console.log(percDone)
        console.log(percUndone)
        document.getElementById("cell23").innerHTML = generated;
        document.getElementById("cell23").style.background= "linear-gradient(to bottom, rgb(252, 222, 221) "+percDone+"%,  rgb(225, 254, 255) "+percUndone+"%)";
    }
    if(table2.state == "dirty") {
        var generated = "<img src=\"cleanIcon.png\" style=\"height: 30px;\"></img>";
        var tempoResiduo = parseInt(table2.remainingTime/1000);
        var percDone = Math.round(mapRange(0, 60, 0, 100, tempoResiduo));
        var percUndone = percDone;
        console.log(percDone)
        console.log(percUndone)
        document.getElementById("cell43").innerHTML = generated;
        document.getElementById("cell43").style.background= "linear-gradient(to bottom, rgb(252, 222, 221) "+percDone+"%,  rgb(225, 254, 255) "+percUndone+"%)";
    }
}

function mapRange (in_min, in_max, out_min, out_max, input) {
    return (input - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
  }

/*
 * Questa funzione disegna sulla mappa un cliente ed il relativo id se si trova ad un tavolo
 */
function updateClientsInTable(teatable1, teatable2) {
    if(teatable1.seatedClient != "-1" && teatable1.state == "busy") {
    	document.getElementById("cell23").style.background = "rgb(252, 222, 221)"
        var generated = "<i class=\"fa fa-user\" aria-hidden=\"true\"></i>&nbsp;"+teatable1.seatedClient;
        document.getElementById("cell23").innerHTML = generated; 
    }
    if(teatable2.seatedClient != "-1" && teatable2.state == "busy") {
    	document.getElementById("cell43").style.background = "rgb(252, 222, 221)"
        var generated = "<i class=\"fa fa-user\" aria-hidden=\"true\"></i>&nbsp;"+teatable2.seatedClient;
        document.getElementById("cell43").innerHTML = generated; 
    }
}

/*
 * Questa funzione serve per aggiornare la posizione del robot sulla mappa.
 * Disegna una R in grassetto alle coordinate passate con cols e rows.
 */
function updateMap(oldCol, oldRow, col, row, direction) {
    var table = document.getElementById("mappa");
    var nomeCella = "cell"+col+row;
    var oldCell = "cell"+oldCol+oldRow
    document.getElementById(oldCell).innerHTML = ""
    direction = direction.replace("Dir", "");
    document.getElementById(nomeCella).innerHTML = "<i class='fa fa-chevron-circle-"+direction+" fa-lg'></i>";
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
 * id="movingAround"
 */
function updateStatus(stato) {
    var table = document.getElementById("stati");
    var coloreSelezionato = "#ddffdd";
    var coloreBase = "white";
    for (var i = 0, row; row = table.rows[i]; i++) {
        row.style.backgroundColor = coloreBase;
    }

    if(stato == "convoyTable" || stato == "convoyExit" || stato == "forceExit"){
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
 * Questa funzione serve per aggiornare lo stato corrente degli ordini,
 * 
 */

function updateOrders(orders) {
    document.getElementById("ordini").innerHTML = "";
    if(orders && Array.isArray(orders) && orders.length) {
        orders.forEach(addOrder);
    }
    else {
        var generated = "<tr class=\"w3-round-small;\"><td style=\"text-align: center;\">There are no orders yet</td></tr>";
        document.getElementById("ordini").innerHTML = generated;
    }
}

//[ { "id" : "1", "order" : "tea1", "ready" : "true"},  { "id" : "2", "order" : "tea2", "ready" : "false"} ]
function addOrder(order) {
    var clientOrder = "" + order.id + order.order
    var button = ""
    console.log(order.ready)
    if (order.ready == "true")
    	button = '<button class="w3-button w3-blue-gray w3-round-small" disabled>Ready</button>'
    else 
    	button = '<button class="w3-button w3-blue-gray w3-round-small" onclick="prepare('+order.id+')" id='+clientOrder+' type="submit">Prepare</button>'
     	
    var generated = "<tr class=\"w3-round-small;\"><td style='text-align:center;vertical-align:middle'>Client "+order.id+":</td><td style='text-align:center;vertical-align:middle' >"+order.order+"</td><td style='text-align:center;vertical-align:middle'>"+button+"</td></tr>";
    document.getElementById("ordini").innerHTML += generated;
}

/*
 * Questa funzione serve per aggiornare lo stato corrente del barman,
 * 
 */
function updateBarmanStatus(stato) {
    document.getElementById("barmanStatus").innerText = "Barman status: " + stato;


}

/*
 * Questa funzione serve per il pannello di controllo di un singolo cliente
 * clientNumber = numero intero che identifica il client
 */
//[ { "id" : "1", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"},  { "id" : "2", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"} ]
function addAClient(item) {
    var clientNumber = item.id;
    var clientName = "clientState"+clientNumber;
    var clientButtonID = "clientButton" + clientNumber;
    var clientButtonText = "<i class=\"fa fa-arrow-right w3-center w3-large\"></i>&nbsp; &nbsp;" + item.nextState;
    var disabled = ""
    if(item.currentState == "waiting"){
    	disabled = "disabled"
    }
    var generated = "<tr class=\"w3-round-small;\"><td style='text-align:center;vertical-align:middle'>Client "+clientNumber+":</td><td id=\""+clientName+"\" style='text-align:center;vertical-align:middle' >"+item.currentState+"</td><td style='text-align:center;vertical-align:middle'><button class=\"w3-button w3-blue-gray w3-round-small\" onclick=\"emitClientChange(\'"+clientNumber+"\')\"id=\""+clientButtonID+"\" type=\"submit\" "+disabled+">"+clientButtonText+"</button></td></tr>";
    document.getElementById("statiClienti").innerHTML += generated;
    if (item.lock == "true") {
        document.getElementById(clientButtonID).disabled = true;
    }
}
/*
 * OUTPUT:
 *
 * <tr class="w3-round-small;">
 *      <td>Client 1:</td>
 *      <td id="clientState1">Waiting to arrive</td>
 *      <td><button class="w3-button w3-blue-gray w3-round-small" onclick="emitClientChange('clientButton1')" id="clientButton1" type="submit">Next State</button></td>
 * </tr>
*/


/*
 * Questa funzione serve per aggiornare lo stato corrente dei clienti,
 * clientsStatus = array con lo stato dei clienti
 */
function updateClientsStatus(clientsStatus) {
    document.getElementById("statiClienti").innerHTML = "";
    if(clientsStatus && Array.isArray(clientsStatus) && clientsStatus.length) {
        clientsStatus.forEach(addAClient);
        //document.getElementById("addClientButtonInternal").style.display = "none";
        //document.getElementById("addClientButtonExternal").style.display = "block";
    }
    else {
        var generated = "<tr class=\"w3-round-small;\"><td style=\"text-align: center;\">There are no clients yet</td></tr>";
        document.getElementById("statiClienti").innerHTML = generated;
        //Aggiungo il pulsante per istanziare nuovi clienti
        //document.getElementById("addClientButtonInternal").style.display = "block";
        //document.getElementById("addClientButtonExternal").style.display = "none";
    }
}
/*
 * OUTPUT nel caso in cui l'array sia vuoto/non ci siano clienti
 *
 * <tr class="w3-round-small;">            
 *      <td style="text-align: center;">There are no clients yet</td>                
 * </tr>
 */





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
    "teatable2State":{"state":"c", "remainingTime": "0", "seatedClient":"1"},
    "teatable1State":{"state":"c", "remainingTime": "0", "seatedClient":"1"},
    "withdraws" : "w",
    "clientsState" : [ { "id" : "1", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"},  { "id" : "2", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"} ] 
}
*/

var oldMessage = null
var raspIP = ""

function updateDashboard(message) {
    //console.log(message);
    
    if (oldMessage) {
		if (oldMessage.robotState != message.robotState)
	    	updateStatus(message.robotState);
	    if (oldMessage.totalNumberOfClients != message.totalNumberOfClients)
	    	updateClientiTotali(message.totalNumberOfClients);
	    if (oldMessage.clientsInTheRoom != message.clientsInTheRoom)
	    	updateClientiInSala(message.clientsInTheRoom);
	    if (oldMessage.teaServed != message.teaServed)
	    	updateTheServiti(message.teaServed);
	    if (oldMessage.withdraws != message.withdraws)
	    	updateRinunce(message.withdraws)
	    if (oldMessage.yRobot != message.yRobot || oldMessage.xRobot != message.xRobot || oldMessage.direction != message.direction)
	    	updateMap(oldMessage.yRobot, oldMessage.xRobot, message.yRobot, message.xRobot, message.direction);
	    if (oldMessage.serviceDeskState != message.serviceDeskState)
	    	updateBarmanStatus(message.serviceDeskState);
	    if (JSON.stringify(oldMessage.orders) != JSON.stringify(message.orders))
	    	updateOrders(message.orders)
	    if (JSON.stringify(oldMessage.clientsState) != JSON.stringify(message.clientsState))
	    	updateClientsStatus(message.clientsState);
	    if (JSON.stringify(oldMessage.teatable1State) != JSON.stringify(message.teatable1State) || JSON.stringify(oldMessage.teatable2State) != JSON.stringify(message.teatable2State))
	    	updateTablesState(message.teatable1State, message.teatable2State);
    } else {
    	raspIP = message.raspIP
    	var url = "http://" + raspIP + ":8080/?action=stream";
        document.getElementById("img_src").src = url;
        document.getElementById("img_src2").src = url;
    	updateStatus(message.robotState);
    	updateClientiTotali(message.totalNumberOfClients);
    	updateClientiInSala(message.clientsInTheRoom);
    	updateTheServiti(message.teaServed);
    	updateRinunce(message.withdraws)
    	updateMap(0,0,message.yRobot, message.xRobot, message.direction);
    	updateBarmanStatus(message.serviceDeskState);
    	updateOrders(message.orders)
    	updateClientsStatus(message.clientsState);
    	updateTablesState(message.teatable1State, message.teatable2State);
   	}
    
    oldMessage = message
    
}




/*
 * Queste due funzioni servono a cambiare pagina\vista
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


/*
 * Questa funzione permette di passare da un tab all'altro
 */
function openTab(evt, tabName) {
    var i, x, tablinks;
  x = document.getElementsByClassName("tabView");
  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < x.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" w3-blue-gray", "");
  }
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " w3-blue-gray";
}
