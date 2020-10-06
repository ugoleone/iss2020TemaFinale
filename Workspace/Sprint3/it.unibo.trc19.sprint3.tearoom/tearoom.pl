%====================================================================================
% tearoom description   
%====================================================================================
context(ctxtearoom, "localhost",  "TCP", "8010").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( resourcemodel, ctxtearoom, "it.unibo.resourcemodel.Resourcemodel").
  qactor( waiter, ctxtearoom, "it.unibo.waiter.Waiter").
  qactor( planner, ctxtearoom, "it.unibo.planner.Planner").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
  qactor( clientmanager, ctxtearoom, "it.unibo.clientmanager.Clientmanager").
  qactor( timer, ctxtearoom, "it.unibo.timer.Timer").
