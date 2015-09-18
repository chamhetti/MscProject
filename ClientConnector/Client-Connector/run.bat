TITLE TRS
set path=D:/WORK/JAVA/jdk1.7.0_67/bin
javac -version
java -Dlog4j.configurationFile=log4j2-trs.xml -Duser.timezone=+05:30 -Dorg.hornetq.logger-delegate-factory-class-name=lk.ac.ucsc.clientConnector.log.Slf4jLogDelegateFactory -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=7878,suspend=n -jar ClientConnector-1.0.0.jar