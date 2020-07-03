%====================================================================================
% waiter description   
%====================================================================================
context(ctxwaiter, "localhost",  "TCP", "8010").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8070").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waiter, ctxwaiter, "it.unibo.waiter.Waiter").
  qactor( planner, ctxwaiter, "it.unibo.planner.Planner").
  qactor( client, ctxwaiter, "it.unibo.client.Client").
  qactor( smartbell, ctxwaiter, "it.unibo.smartbell.Smartbell").
  qactor( table1, ctxwaiter, "it.unibo.table1.Table1").
  qactor( table2, ctxwaiter, "it.unibo.table2.Table2").
  qactor( barman, ctxwaiter, "it.unibo.barman.Barman").
