%====================================================================================
% waiter description   
%====================================================================================
context(ctxwaiter, "localhost",  "TCP", "8010").
context(ctxtable1, "127.0.0.1",  "TCP", "8020").
context(ctxtable2, "127.0.0.1",  "TCP", "8030").
context(ctxsmartbell, "127.0.0.1",  "TCP", "8040").
context(ctxbarman, "127.0.0.1",  "TCP", "8050").
context(ctxclient, "127.0.0.1",  "TCP", "8060").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8070").
 qactor( table1, ctxtable1, "external").
  qactor( table2, ctxtable2, "external").
  qactor( smartbell, ctxsmartbell, "external").
  qactor( barman, ctxbarman, "external").
  qactor( client, ctxclient, "external").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiter, ctxwaiter, "it.unibo.waiter.Waiter").
  qactor( planner, ctxwaiter, "it.unibo.planner.Planner").
