%====================================================================================
% client description   
%====================================================================================
context(ctxwaiter, "192.168.1.166",  "TCP", "8010").
context(ctxsmartbell, "192.168.1.166",  "TCP", "8040").
context(ctxclient, "localhost",  "TCP", "8060").
 qactor( smartbell, ctxsmartbell, "external").
  qactor( waiter, ctxwaiter, "external").
  qactor( client, ctxclient, "it.unibo.client.Client").
