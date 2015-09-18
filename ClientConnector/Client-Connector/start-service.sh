#!/bin/sh
TRS_START="java -Dlog4j.configurationFile=log4j2-trs.xml
-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
-Dorg.hornetq.logger-delegate-factory-class-name=lk.ac.ucsc.clientConnector.log.Slf4jLogDelegateFactory
-Xms1024m -Xmx4096m -XX:MaxPermSize=256m -Djava.net.preferIPv4Stack=true
-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=7878,suspend=n
-jar mfg_trs-1.0.43-SNAPSHOT.jar"
NOHUP=/usr/bin/nohup
$NOHUP $TRS_START ./nohup_trs.out &
