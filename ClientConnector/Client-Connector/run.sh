#!/bin/sh
java -Dlog4j.configuration=file:log4j-trs.properties
-Djava.net.preferIPv4Stack=true -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=7878,suspend=n -jar mfg_trs-1.0.41.jar