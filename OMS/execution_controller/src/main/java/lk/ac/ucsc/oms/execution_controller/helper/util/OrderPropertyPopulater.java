package lk.ac.ucsc.oms.execution_controller.helper.util;



/**
 * This will be use a utility class for populate the order properties
 * Author: Chathura
 * Date: 4/10/13
 */
public class OrderPropertyPopulater {

    private static OrderPropertyPopulater instance;

    private OrderPropertyPopulater() {
    }

    public static synchronized OrderPropertyPopulater getInstance() {
        if (instance == null) {
            instance = new OrderPropertyPopulater();
        }
        return instance;
    }



}
