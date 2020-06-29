%====================================================================================
% barman description   
%====================================================================================
context(ctxwaiter, "127.0.0.1",  "TCP", "8010").
context(ctxbarman, "localhost",  "TCP", "8050").
 qactor( waiter, ctxwaiter, "external").
  qactor( barman, ctxbarman, "it.unibo.barman.Barman").
