%====================================================================================
% clientsimulator description   
%====================================================================================
context(ctxwaiter, "127.0.0.1",  "TCP", "8010").
context(ctxsmartbell, "127.0.0.1",  "TCP", "8040").
context(ctxclientsimulator, "localhost",  "TCP", "8060").
 qactor( smartbell, ctxsmartbell, "external").
  qactor( waiter, ctxwaiter, "external").
  qactor( clientsimulator, ctxclientsimulator, "it.unibo.clientsimulator.Clientsimulator").
