%====================================================================================
% waiter description   
%====================================================================================
context(ctxwaiter, "localhost",  "TCP", "8010").
context(ctxsmartbell, "127.0.0.1",  "TCP", "8040").
context(ctxbarman, "127.0.0.1",  "TCP", "8050").
context(ctxclient, "127.0.0.1",  "TCP", "8060").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8070").
 qactor( smartbell, ctxsmartbell, "external").
  qactor( barman, ctxbarman, "external").
  qactor( client, ctxclient, "external").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiter, ctxwaiter, "it.unibo.waiter.Waiter").
  qactor( planner, ctxwaiter, "it.unibo.planner.Planner").
