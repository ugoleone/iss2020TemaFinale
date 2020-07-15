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
%%  athome
%%	serving( CLIENTID )
%%	movingto( CELL )
%%	cleaning( table(N) )

waiter( athome ).


%% ------------------------------------------ 
%% Teatables
%% ------------------------------------------ 
%% busy
%% free		(not busy and not clean)
%% dirty	(not clean)
%% clean	(not dirty)
%% available (free and clean)	

teatable( 1, available ).
teatable( 2, available ).

stateOfTeatables(T1,T2) :- teatable(1,T1),teatable(2,T2).




%% ------------------------------------------ 
%% Client IDs
%% ------------------------------------------ 

clientIDs([]).
addClientID(X) :- replaceRule(clientIDs(L),clientIDs([X|L]).



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

newClient :- totalNumberOfClient(N),X is N+1,replaceRule(totalNumberOfClient(_),totalNumberOfClient(X)),clientsInTheRoom(NC),XC is NC+1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)).
exitClient :- clientsInTheRoom(NC),XC is NC-1,replaceRule(clientsInTheRoom(_),clientsInTheRoom(XC)).



%% ------------------------------------------ 
%% Room as a whole
%% ------------------------------------------ 
roomstate(  waiter(S), stateOfTeatables(T1,T2), servicedesk(D), totalNumberOfClients(X), clientsInTheRoom(Y) ):-
	 waiter(S), stateOfTeatables(T1,T2), servicedesk(D),totalNumberOfClients(X), clientsInTheRoom(Y).


	 
	