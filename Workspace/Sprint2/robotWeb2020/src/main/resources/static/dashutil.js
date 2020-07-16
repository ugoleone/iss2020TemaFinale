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
 * Questa funzione è un adapter per mostrare il numero totale di tavoli liberi
 */
function adapterTavoliLiberi(table1, table2) {
    var tavoliLiberi = 0;
    if (table1 == "clean")
        tavoliLiberi += 1;
    if (table2 == "clean")
        tavoliLiberi += 1;
    updateTavoliLiberi(tavoliLiberi);
}

/*
 * Questa funzione serve per aggiornare la posizione del robot sulla mappa.
 * Disegna una R in grassetto alle coordinate passate con cols e rows.
 */
function updateMap(cols, rows) {
    var table = document.getElementById("mappa");
    var nomeCella = "cell"+cols+rows;
    for (var i = 0, row; row = table.rows[i]; i++) {
        //itera sulle righe
        for (var j = 0, col; col = row.cells[j]; j++) {
            //itera sulle colonne (quindi le celle)
            col.textContent = "";
        }  
    }
    document.getElementById(nomeCella).innerHTML = "<strong>R</strong>";
}


/*
 * Questa funzione serve per aggiornare lo stato corrente del waiter,
 * colorando di verde lo stato corrente.
 * id="home"
 * id="reach"
 * id="convoy"
 * id="ordering"
 * id="serving"
 * id="collecting"
 * id="sanitizing"
 */
function updateStatus(stato) {
    var table = document.getElementById("stati");
    var coloreSelezionato = "#ddffdd";
    var coloreBase = "white";
    for (var i = 0, row; row = table.rows[i]; i++) {
        row.style.backgroundColor = coloreBase;
    }
    document.getElementById(stato).style.backgroundColor = coloreSelezionato;
}


/*
{
    "serviceDeskState":"d",
    "robotState":"a",
    "xRobot":"r",
    "yRobot":"c",
    "totalNumberOfClients":"e",
    "clientsInTheRoom":"f",
    "teatable2State":"c",
    "teatable1State":"b"
}
*/
function showMsg(message) {
    console.log(message);
    
    updateStatus(message.robotState);
    updateClientiServiti(message.totalNumberOfClients);
    updateClientiInSala(message.clientsInTheRoom);
    adapterTavoliLiberi(message.teatable1State, message.teatable2State);

    //updateTheServiti(data);
    updateMap(message.yRobot, message.xRobot);
}