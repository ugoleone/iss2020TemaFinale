%====================================================================================
% basicrobot description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8070").
 qactor( datacleaner, ctxbasicrobot, "rx.dataCleaner").
  qactor( distancefilter, ctxbasicrobot, "rx.distanceFilter").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
msglogging.
