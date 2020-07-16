%====================================================================================
% tearoom description   
%====================================================================================
context(ctxtearoom, "localhost",  "TCP", "8038").
 qactor( waiter, ctxtearoom, "it.unibo.waiter.Waiter").
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
  qactor( client, ctxtearoom, "it.unibo.client.Client").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
