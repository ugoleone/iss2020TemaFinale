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


%% ------------------------------------------ 
%% Waiter
%% ------------------------------------------ 
%% athome
%% reachEntranceDoor
%% convoyTable
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

pendingTasks([]).
addTask(T,P) :- pendingTasks(L), append(L,[[T,P]],R), replaceRule(pendingTasks(_),pendingTasks(R)).
getTask(T,P) :- pendingTasks([[T,P]|L]), replaceRule(pendingTasks(_), pendingTasks(L)).

waitingClients([]).
addWaitingClient(ID) :- waitingClients(L), append(L,[ID],R), replaceRule(waitingClients(_),waitingClients(R)).
getWaitingClient(ID) :- waitingClients([ID|L]), replaceRule(waitingClients(_), waitingClients(L)).

%% ------------------------------------------ 
%% Teatables
%% ------------------------------------------ 
%% busy		(not free and not clean)
%% dirty	(free and not clean)
%% available (free and clean)	

teatable( 1, available ).
teatable( 2, available ).

teatableClientID(1,-1).
teatableClientID(2,-1).

stateOfTeatables(T1,T2) :- teatable(1,T1),teatable(2,T2).
assignTable(T,ID) :- replaceRule(teatableClientID(T,_),teatableClientID(T,ID)).



%% ------------------------------------------ 
%% Clients State
%% ------------------------------------------ 
%% client(1,waiting)

clientsIDs([]).
addClient(X) :- clientsIDs(L),append(L,[X],R),replaceRule(clientsIDs(_),clientsIDs(R)),addRule(client(X,waiting)).

delete(Element,[Element|Tail],Tail).
delete(Element,[Head|Tail],[Head|Tail1]) :- delete(Element,Tail,Tail1).
removeClient(X) :- removeRule(client(X,_)), clientsIDs(L), delete(X,L,Res), replaceRule(clientsIDs(_),clientsIDs(Res)).

getClientsStateRic([],[]).
getClientsStateRic([[C,S]|T],[C|CT]) :- client(C,S), getClientsStateRic(T,CT).
getClientsState([[FirstClient,FirstState]|Tail]) :- clientsIDs([FirstClient|ClientsIDs]),!,client(FirstClient,FirstState),getClientsStateRic(Tail,ClientsIDs).
getClientsState([]).

%% ------------------------------------------ 
%% ServiceDesk
%% ------------------------------------------ 
%% idle
%% preparing( CLIENTID )
%% ready( CLIENTID )

orders([]).
addOrder(ID,O) :- orders(L), append(L,[[ID,O]],R), replaceRule(orders(_),orders(R)).
deleteOrder(ID,O) :- orders(L), delete([ID,O],L,R), replaceRule(orders(_), orders(R)).
servicedesk( idle ).

 
	 
%% ------------------------------------------ 
%% Statistics
%% ------------------------------------------ 

totalNumberOfClients(0).
clientsInTheRoom(0).
teaServed(0).
withdraws(0).

newClient(X) :- totalNumberOfClients(N),X is N+1,replaceRule(totalNumberOfClients(_),totalNumberOfClients(X)),addClient(X),clientsInTheRoom(NC),XC is NC+1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)).
exitClient(X) :- clientsInTheRoom(NC),XC is NC-1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)),removeClient(X).
serveTea :- teaServed(TS), NTS is TS + 1, replaceRule(teaServed(_),teaServed(NTS)).
withdraw :- withdraws(W), WS is W + 1, replaceRule(withdraws(_),withdraws(WS)).


%% ------------------------------------------ 
%% Room as a whole
%% ------------------------------------------ 
roomstate( waiter(S),currentWaiterPosDir(Y,X,D) , stateOfTeatables(T1,T2), servicedesk(SD), orders(O), getClientsState(CS), teaServed(TS), totalNumberOfClients(NC), clientsInTheRoom(CR), withdraws(W)):- waiter(S),currentWaiterPosDir(Y,X,D) , stateOfTeatables(T1,T2), servicedesk(SD), orders(O), getClientsState(CS), teaServed(TS), totalNumberOfClients(NC), clientsInTheRoom(CR), withdraws(W).


	 
	