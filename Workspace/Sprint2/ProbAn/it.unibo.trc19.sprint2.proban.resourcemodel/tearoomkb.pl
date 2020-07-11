%===========================================
% tearoomkb.pl
%===========================================

%% ------------------------------------------ 
%% Positions
%% ------------------------------------------ 
pos( barman,       5, 0 ).
pos( teatable1,    2, 2 ).
pos( teatable2,    4, 2 ).
pos( entrancedoor, 1, 4 ).
pos( exitdoor,     6, 4 ).


%% ------------------------------------------ 
%% Current Waiter Position
%% ------------------------------------------ 

currentPos(0,0).


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

teatable( 1, clean ).
teatable( 2, clean ).


%% ------------------------------------------ 
%% Client IDs
%% ------------------------------------------ 

clientIDs([]).



%% ------------------------------------------ 
%% ServiceDesk
%% ------------------------------------------ 
%% idle
%% preparing( CLIENTID )
%% ready( CLIENTID )

servicedesk( idle ).

%% ------------------------------------------ 
%% Room as a whole
%% ------------------------------------------ 
roomstate(  waiter(S), stateOfTeatables(V), servicedesk(D) ):-
	 waiter(S), stateOfTeatables(V), servicedesk(D).
	 
	 
%% ------------------------------------------ 
%% Statistics
%% ------------------------------------------ 

totalNumberOfClient(0).

clientsInTheRoom(X) :- clientIDs(L),length(L,X).


	 
	