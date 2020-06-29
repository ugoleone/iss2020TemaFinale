%====================================================================================
% waiter description   
%====================================================================================
context(ctxwaiter, "localhost",  "TCP", "8010").
context(ctxtable1, "192.168.1.166",  "TCP", "8020").
context(ctxtable2, "192.168.1.166",  "TCP", "8030").
context(ctxsmartbell, "192.168.1.166",  "TCP", "8040").
context(ctxbarman, "192.168.1.166",  "TCP", "8050").
context(ctxclient, "192.168.1.166",  "TCP", "8060").
 qactor( table1, ctxtable1, "external").
  qactor( table2, ctxtable2, "external").
  qactor( smartbell, ctxsmartbell, "external").
  qactor( barman, ctxbarman, "external").
  qactor( client, ctxclient, "external").
  qactor( waiter, ctxwaiter, "it.unibo.waiter.Waiter").
