package com.directfn.exchange_simulator.boot_strapper;


import com.directfn.exchange_simulator.common_util.utils.ConfigSettings;
import com.directfn.exchange_simulator.jms_reader.JMSQueueReader;
import com.directfn.exchange_simulator.jms_writer.JMSQueueWriter;
import com.directfn.exchange_simulator.message_processor.api.ControllerLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import quickfix.*;

import java.util.concurrent.ExecutorService;

/**
 * User: Ruchira Ranaweera
 * Date: 4/24/14
 */

/**
 * This is the entry point of simulator and required initialization will happen here.
 */
@ComponentScan
@EnableAutoConfiguration
public class StartUp {
    private static Logger logger = LoggerFactory.getLogger(StartUp.class);

    private JMSQueueReader jmsQueueReader;
    private JMSQueueWriter jmsQueueWriter;
    private MemoryStore memoryStore;
    ConfigSettings configSettings = new ConfigSettings();
    private final String queueNameToAppia = configSettings.getConfiguration("queueShouldRead");
    private final String queueNameFromSellSide = configSettings.getConfiguration("queueToWrite");
    private final String omsIP = configSettings.getConfiguration("omsIP");
    private final String webServerIp = configSettings.getConfiguration("webServerIP");
    private final String omsPort = configSettings.getConfiguration("omsPort");
    private final String connectMode = configSettings.getConfiguration("CONNECT_MODE");
    private String webServerPort;
    private String webServerSecurePort;
    private static final int TIME_OUT_CONST = 5;
    private ControllerLogic controllerLogic;
    private static final int THREAD_COUNT = 10;
    private ExecutorService executorService;

    public StartUp() {

    }

    public static void main(String[] args) {
        new StartUp().initializeAll(args);
    }

    private void initializeAll(String[] args) {
        logger.info("Initializing simulator.....");
        ApplicationContext context = new FileSystemXmlApplicationContext("./settings/spring.config.xml");

        logger.info("simulator connection mode configured as: {}", connectMode);
        if (!"APPIA".equalsIgnoreCase(connectMode)) {
            logger.info("Subscribing ToAppia queue of OMS.....");
            if (jmsQueueReader == null) {
                jmsQueueReader = new JMSQueueReader(queueNameToAppia, omsIP, Integer.parseInt(omsPort));
            }
            //initializing jmsQueueReader
            jmsQueueReader.initialize();
            JMSQueueReader.setIsFirstTime(false);
            logger.info("Subscribe ToAppia queue of OMS is success");
            logger.info("Subscribing FromSellSide queue of OMS.....");
            if (jmsQueueWriter == null) {
                jmsQueueWriter = new JMSQueueWriter(queueNameFromSellSide, omsIP, Integer.parseInt(omsPort), TIME_OUT_CONST);
            }
            //initializing jmsQueueWriter
            jmsQueueWriter.start();
            logger.info("Subscribe FromSellSide queue of OMS is success");
        }

        logger.info("Simulator initialization success...");
    }


    public void setJmsQueueReader(JMSQueueReader jmsQueueReader) {
        this.jmsQueueReader = jmsQueueReader;
    }

    public void setJmsQueueWriter(JMSQueueWriter jmsQueueWriter) {
        this.jmsQueueWriter = jmsQueueWriter;
    }

    public void setMemoryStore(MemoryStore memoryStore) {
        this.memoryStore = memoryStore;
    }


    public void setWebServerPort(String webServerPort) {
        this.webServerPort = webServerPort;
    }

    public void setWebServerSecurePort(String webServerSecurePort) {
        this.webServerSecurePort = webServerSecurePort;
    }

    public void setControllerLogic(ControllerLogic controllerLogic) {
        this.controllerLogic = controllerLogic;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }



}
