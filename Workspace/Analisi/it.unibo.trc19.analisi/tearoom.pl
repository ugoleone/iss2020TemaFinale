%====================================================================================
% tearoom description   
%====================================================================================
context(ctxtearoom, "localhost",  "TCP", "8038").
 qactor( client, ctxtearoom, "external").
  qactor( waiter, ctxtearoom, "external").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
