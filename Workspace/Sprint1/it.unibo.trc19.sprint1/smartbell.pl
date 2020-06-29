%====================================================================================
% smartbell description   
%====================================================================================
context(ctxwaiter, "192.168.1.166",  "TCP", "8010").
context(ctxsmartbell, "localhost",  "TCP", "8040").
context(ctxclient, "192.168.1.166",  "TCP", "8060").
 qactor( waiter, ctxwaiter, "external").
  qactor( client, ctxclient, "external").
  qactor( smartbell, ctxsmartbell, "it.unibo.smartbell.Smartbell").
