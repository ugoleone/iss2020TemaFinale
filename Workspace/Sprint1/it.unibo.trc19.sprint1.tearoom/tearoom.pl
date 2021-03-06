%====================================================================================
% tearoom description   
%====================================================================================
context(ctxtearoom, "localhost",  "TCP", "8010").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8070").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiter, ctxtearoom, "it.unibo.waiter.Waiter").
  qactor( planner, ctxtearoom, "it.unibo.planner.Planner").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
  qactor( clientsimulator, ctxtearoom, "it.unibo.clientsimulator.Clientsimulator").
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
