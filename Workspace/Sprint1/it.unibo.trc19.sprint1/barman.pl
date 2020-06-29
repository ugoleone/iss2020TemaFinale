%====================================================================================
% barman description   
%====================================================================================
context(ctxwaiter, "192.168.1.166",  "TCP", "8010").
context(ctxbarman, "localhost",  "TCP", "8050").
 qactor( waiter, ctxwaiter, "external").
  qactor( barman, ctxbarman, "it.unibo.barman.Barman").
