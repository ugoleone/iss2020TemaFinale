%====================================================================================
% waiter description   
%====================================================================================
context(ctxtearoom, "localhost",  "TCP", "8010").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8070").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiter, ctxtearoom, "it.unibo.waiter.Waiter").
  qactor( planner, ctxtearoom, "it.unibo.planner.Planner").
  qactor( client, ctxtearoom, "it.unibo.client.Client").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
