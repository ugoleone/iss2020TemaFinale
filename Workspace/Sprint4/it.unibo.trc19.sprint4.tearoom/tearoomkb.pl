%===========================================
% tearoomkb.pl
%===========================================

%% ------------------------------------------ 
%% Positions
%% ------------------------------------------ 
pos( home,		   0, 0 ).
pos( barman,       5, 0 ).
pos( teatable1,    2, 2 ).
pos( teatable2,    4, 2 ).
pos( entrancedoor, 1, 4 ).
pos( exitdoor,     6, 4 ).


%% ------------------------------------------ 
%% Current Waiter Position
%% ------------------------------------------ 


currentWaiterPosDir(0,0,downDir).

updateWaiterPositionDirection(X,Y,D) :- replaceRule(currentWaiterPosDir(_,_,_),currentWaiterPosDir(X,Y,D)).

%% ------------------------------------------ 
%% Waiter
%% ------------------------------------------ 
%% athome
%% reachEntranceDoor
%% convoy
%% takingOrder
%% collectingDrink
%% bringingDrinkToClient
%% servingDrinkToClient
%% collectingPayment
%% convoyExit
%% sanitizing
%% returnHome
%% returnHomeFromExit

waiter( athome ).

updateWaiterTask(T) :- replaceRule(waiter(_),waiter(T)).

pendingTasks([]).
addTask(T,P) :- pendingTasks(L), append(L,[[T,P]],R), replaceRule(pendingTasks(_),pendingTasks(R)).
getTask(T,P) :- pendingTasks([[T,P]|L]), replaceRule(pendingTasks(_), pendingTasks(L)).


%% ------------------------------------------ 
%% Teatables
%% ------------------------------------------ 
%% busy		(not free and not clean)
%% dirty	(free and not clean)
%% available (free and clean)	

%% (teatable number, state, cleaning time, seated client)
teatable( 1, available, 0, -1).
teatable( 2, available, 0, -1).

teatableClientID(1, -1).
teatableClientID(2, -1).

stateOfTeatables([S1,RT1,CS1],[S2,RT2,CS2]) :- teatable(1,S1,RT1,CS1),teatable(2,S2,RT2,CS2).
reserveTable(T,ID) :- replaceRule(teatableClientID(T,_),teatableClientID(T,ID)), replaceRule(teatable( T, _, RT, _), teatable( T, busy, RT, -1)).

seatClient(T, ID) :- replaceRule(teatable( T, S, _, _), teatable( T, S, 60000, ID)).
freeTable(T) :- replaceRule(teatable( T, _, _, _), teatable( T, dirty, 60000, -1)).
tableCleaned(T) :- replaceRule(teatable( T, _, _, _), teatable( T, available, 0, -1)).


%% ------------------------------------------ 
%% Clients State
%% ------------------------------------------ 
%% 		  ID, Stato, Lock => Lock si riferisce se il bottone "next state" nella pagina web è "lock" o no
%% client(1,waiting, true).

waitingClients([]).
addWaitingClient(ID) :- waitingClients(L), append(L,[ID],R), replaceRule(waitingClients(_),waitingClients(R)).
getWaitingClient(ID) :- waitingClients([ID|L]), replaceRule(waitingClients(_), waitingClients(L)).


nextState(waiting,entering).
nextState(entering,ordering).
nextState(ordering,waitingtea).
nextState(waitingtea,drinking).
nextState(drinking,paying).
nextState(paying,exiting).
nextState(exiting,gone).
nextState(gone,none).

updateClientState(ID, S, L) :- replaceRule(client(ID,_,_),client(ID,S,L)).

unlockClient(ID) :- replaceRule(client(ID,S,_),client(ID,S,false)).
lockClient(ID) :- replaceRule(client(ID,S,_),client(ID,S,true)).

clientsIDs([]).
addClient(X) :- clientsIDs(L),append(L,[X],R),replaceRule(clientsIDs(_),clientsIDs(R)),addRule(client(X,waiting,true)).

delete(Element,[Element|Tail],Tail).
delete(Element,[Head|Tail],[Head|Tail1]) :- delete(Element,Tail,Tail1).
removeClient(X) :- removeRule(client(X,_,_)), clientsIDs(L), delete(X,L,Res), replaceRule(clientsIDs(_),clientsIDs(Res)).

getClientsStateRic([],[]).
getClientsStateRic([[C,S,NS,L]|T],[C|CT]) :- client(C,S,L),nextState(S,NS), getClientsStateRic(T,CT).
getClientsState([[FirstClient,FirstState,NextState,Lock]|Tail]) :- clientsIDs([FirstClient|ClientsIDs]),!,client(FirstClient,FirstState,Lock),nextState(FirstState,NextState),getClientsStateRic(Tail,ClientsIDs).
getClientsState([]).



%% ------------------------------------------ 
%% ServiceDesk
%% ------------------------------------------ 
%% idle
%% preparing( CLIENTID )
%% ready( CLIENTID )
%% order(1,tea,true)
addOrder(ID,O,R) :- addRule(order(ID,O,R)).
deleteOrder(ID) :- removeRule(order(ID,_,_)).
orderReady(ID) :- replaceRule(order(ID,O,_),order(ID,O,true)).
servicedesk( idle ).

getOrders(L) :- findall([ID,O,R],order(ID,O,R),L).

updateBarmanState(S) :- replaceRule(serviceDesk(_),serviceDesk(S)).

 
	 
%% ------------------------------------------ 
%% Statistics
%% ------------------------------------------ 

totalNumberOfClients(0).
clientsInTheRoom(0).
teaServed(0).
withdraws(0).

newClient(X) :- totalNumberOfClients(N),X is N+1,replaceRule(totalNumberOfClients(_),totalNumberOfClients(X)),addClient(X).
exitClient(X) :- clientsInTheRoom(NC),XC is NC-1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)),removeClient(X).
enterClient :- clientsInTheRoom(NC),XC is NC+1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)).
serveTea :- teaServed(TS), NTS is TS + 1, replaceRule(teaServed(_),teaServed(NTS)).
withdraw :- withdraws(W), WS is W + 1, replaceRule(withdraws(_),withdraws(WS)).


%% ------------------------------------------ 
%% Room as a whole
%% ------------------------------------------ 
roomstate(S,Y,X,D,T1,T2,SD,O,CS,TS,NC,CR,W):- waiter(S), currentWaiterPosDir(Y,X,D) , stateOfTeatables(T1,T2), servicedesk(SD), getOrders(O), getClientsState(CS), teaServed(TS), totalNumberOfClients(NC), clientsInTheRoom(CR), withdraws(W).


	 
	