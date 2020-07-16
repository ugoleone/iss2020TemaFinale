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

currentWaiterPos(0,0).


%% ------------------------------------------ 
%% Waiter
%% ------------------------------------------ 

waiter( home ).


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




%% ------------------------------------------ 
%% Client IDs
%% ------------------------------------------ 

clientIDs([]).
addClientID(X) :- replaceRule(clientIDs(L),clientIDs([X|L])).



%% ------------------------------------------ 
%% ServiceDesk
%% ------------------------------------------ 
%% idle
%% preparing( CLIENTID )
%% ready( CLIENTID )

servicedesk( idle ).

 
	 
%% ------------------------------------------ 
%% Statistics
%% ------------------------------------------ 

totalNumberOfClients(0).
clientsInTheRoom(0).
teaServed(0).

newClient :- totalNumberOfClients(N),X is N+1,replaceRule(totalNumberOfClients(_),totalNumberOfClients(X)),clientsInTheRoom(NC),XC is NC+1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)).
exitClient :- clientsInTheRoom(NC),XC is NC-1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)).
serveTea :- teaServed(TS), NTS is TS + 1, replaceRule(teaServed(_),teaServed(NTS)).


%% ------------------------------------------ 
%% Room as a whole
%% ------------------------------------------ 
roomstate( waiter(S),currentWaiterPos(Y,X) , stateOfTeatables(T1,T2), servicedesk(D), teaServed(TS), totalNumberOfClients(NC), clientsInTheRoom(CR)):- waiter(S),currentWaiterPos(Y,X) , stateOfTeatables(T1,T2), servicedesk(D), teaServed(TS), totalNumberOfClients(NC), clientsInTheRoom(CR).


	 
	