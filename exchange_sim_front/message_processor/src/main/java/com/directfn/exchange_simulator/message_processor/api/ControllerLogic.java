package com.directfn.exchange_simulator.message_processor.api;



import com.directfn.exchange_simulator.message_processor.impl.beans.FIXPropertiesBean;
import com.directfn.exchange_simulator.message_processor.impl.beans.MessageObject;
import com.directfn.exchange_simulator.message_processor.impl.beans.TradingSessions;

import java.util.List;
import java.util.Map;

/**
 * User: Ruchira Ranaweera
 * Date: 6/17/14
 */
public interface ControllerLogic {

    /**
     * get request message list from memory
     *
     * @param request
     * @return
     */
    List<FIXPropertiesBean> getRequestMessageListFromMemory(String request);

    /**
     * get trading message fix properties from memory
     *
     * @param request
     * @return
     */
    List<FIXPropertiesBean> getTradingMessageFixProperties(String request);

    /**
     * get all trading sessions
     *
     * @return
     */
    List<TradingSessions> getTradingSessions(String connectStatus);

    /**
     * get trading sessions
     *
     * @return
     */
    List<TradingSessions> getTradingSessions();

    /**
     * get trading sessions
     *
     * @return
     */
    List<TradingSessions> getFixSessions();

    /**
     * update selected trading session
     *
     * @param input
     * @return
     */
    boolean updateTradingSession(String input);

    /**
     * get response list by clOrdId
     *
     * @param id
     * @return
     */
    List<FIXPropertiesBean> getResponseListByClOrdId(String id);

    /**
     * load current configuration from properties file
     *
     * @param id
     * @return
     */
    String loadCurrentSettings(String id);

    /**
     * update configuration
     *
     * @param input
     * @return
     */
    boolean updateSettings(String input);

    /**
     * get all Fix messages related to selected trading session
     *
     * @param sessionId
     * @return
     */
    List<String> getAllMessagesRelatedToSelectedTradingSession(String sessionId);



    /**
     * show property file
     *
     * @return
     */
    Map<String, String> showPropertyFile();

    /**
     * get message list by trading session
     *
     * @param sessionId
     * @return
     */
    List<FIXPropertiesBean> getMessageListByTradingSession(String sessionId);

    /**
     * is connected to ToAppia queue
     *
     * @return
     */
    String isConnectedToToAppiaQueue();

    /**
     * is connected to FromSellSide queue
     *
     * @return
     */
    public String isConnectedToFromSellSideQueue();

    /**
     * change current OMS ip to new OMS ip
     *
     * @param omsIp
     * @return
     */
    boolean changeOMSIp(String omsIp);


    /**
     * execute order
     *
     * @param messageObject
     * @return
     */
    List<FIXPropertiesBean> executeOrder(MessageObject messageObject);


}
