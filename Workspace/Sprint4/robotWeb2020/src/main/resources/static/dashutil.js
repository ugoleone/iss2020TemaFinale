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

//[ { "id" : "1", "order" : "tea1"},  { "id" : "2", "order" : "tea2"} ]
function addOrder(order) {
    var clientOrder = "" + order.id + order.order
    var generated = "<tr class=\"w3-round-small;\"><td style='text-align:center;vertical-align:middle'>Client "+order.id+":</td><td style='text-align:center;vertical-align:middle' >"+order.order+"</td><td style='text-align:center;vertical-align:middle'><button class=\"w3-button w3-blue-gray w3-round-small\" onclick=\"prepare(\'"+order.id+"\')\"id=\""+clientOrder+"\" type=\"submit\">Prepare</button></td></tr>";
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
//[ { "id" : "1", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"},  { "id" : "2", "currentState" : "stato3", "nextState" : "state4", "lock" : "true"} ]
function addAClient(item) {
    var clientNumber = item.id;
    var clientName = "clientState"+clientNumber;
    var clientButton = "clientButton" + clientNumber;
    var disabled = ""
    if(item.currentState == "waiting"){
    	disabled = "disabled"
    }
    var generated = "<tr class=\"w3-round-small;\"><td style='text-align:center;vertical-align:middle'>Client "+clientNumber+":</td><td id=\""+clientName+"\" style='text-align:center;vertical-align:middle' >"+item.currentState+"</td><td style='text-align:center;vertical-align:middle'><button class=\"w3-button w3-blue-gray w3-round-small\" onclick=\"emitClientChange(\'"+clientNumber+"\')\"id=\""+clientButton+"\" type=\"submit\" "+disabled+">Next State</button></td></tr>";
    document.getElementById("statiClienti").innerHTML += generated;
    if (item == "gone") {
        document.getElementById(clientButton).disabled = true;
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
    "teatable2State":"c",
    "teatable1State":"b",
    "teatable1ClientID": "id1", -> -1 = no client
    "teatable2ClientID": "id2", -> -1 = no client
    "withdraws" : "w",
    "clientsState" : [ { "id" : "1", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"},  { "id" : "2", "currentState" : "stato1", "nextState" : "state2", "lock" : "true"} ] 
}
*/
function updateDashboard(message) {
    console.log(message);

    updateStatus(message.robotState);
    updateClientiTotali(message.totalNumberOfClients);
    updateClientiInSala(message.clientsInTheRoom);
    adapterTavoliLiberi(message.teatable1State, message.teatable2State);
    updateTheServiti(message.teaServed);
    updateRinunce(message.withdraws)
    updateMap(message.yRobot, message.xRobot, message.direction);
    updateBarmanStatus(message.serviceDeskState);
    updateOrders(message.orders)
    updateClientsStatus(message.clientsState);
    
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
