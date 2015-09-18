package com.directfn.exchange_simulator.message_processor.impl.helper;

import com.directfn.exchange_simulator.common_util.utils.ConfigSettings;
import com.directfn.exchange_simulator.jms_writer.JMSQueueWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @ author Ruchira Ranaweera on 12/11/14.
 */
public class FixMessageSender implements Callable<Object> {
    private static Logger logger = LoggerFactory.getLogger(FixMessageSender.class);
    private Map<String, String> map = new HashMap<>();
    private int retryCount;
    private static String queueName = new ConfigSettings().getConfiguration("queueToWrite");
    private static String omsIP = new ConfigSettings().getConfiguration("omsIP");
    private static final int SERVER_PORT1099 = 1099;
    private static final int TIME_OUT_CONST = 5;
    private JMSQueueWriter jmsQueueWriter;

    public FixMessageSender(Map<String, String> map, int retryCount) {
        this.map = map;
        this.retryCount=retryCount;
    }

    @Override
    public Object call() throws Exception {
        jmsQueueWriter = new JMSQueueWriter(queueName, omsIP, SERVER_PORT1099, TIME_OUT_CONST);
        jmsQueueWriter.sendMessage(map, 1);
        return null;
    }
}
