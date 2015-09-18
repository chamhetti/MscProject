#!/bin/sh
java -XX:PermSize=512m -XX:MaxPermSize=512m -Dlog4j.configurationFile=log4j2.xml -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=7879,suspend=n -jar exchange_sim_build-1.0.3.jar
