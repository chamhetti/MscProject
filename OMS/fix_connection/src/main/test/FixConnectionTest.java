import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.fixConnection.api.ConnectionStatus;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFacade;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFactory;
import lk.ac.ucsc.oms.fixConnection.api.beans.ExchangeConnection;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * User: chamindah
 * Date: 4/6/15
 * Time: 1:00 PM
 */
public class FixConnectionTest {
    public static FIXConnectionFactory factory;

    public static void main(String[] args) {
        try {
            factory = FIXConnectionFactory.getInstance();
            factory.initialize();
            create();
            //update();
            //markFIXConnectionAsDeleted();
            //getAll();
            //getFixConnection();
            //getFIXConnectionData();
            System.out.println("======================testFIXConnection main Class finished========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void create() {

        try {
            FIXConnectionFacade fixConnectionFacade = FIXConnectionFactory.getInstance().getFIXConnectionFacade();
            FIXConnection fixConnection1 = FIXConnectionFactory.getInstance().getFIXConnectionFacade().getEmptyFIXConnection();
            fixConnection1.setConnectionStatus(ConnectionStatus.CONNECTED);
            fixConnection1.setConnectionID("NBKPROD");
            fixConnection1.setExchangeCode("NBK");
            fixConnection1.setDescription("NBK Production");
            fixConnection1.setStatus(RecordStatus.APPROVED);

            HashMap<Integer, String> fixtag = new HashMap<>();
            fixtag.put(49, "MBSPROD");
            fixtag.put(56, "NBKPROD");
            fixtag.put(57, "null");
            fixtag.put(20, "1");

            fixConnection1.setFixTags(fixtag);

       /*     HashMap<Integer, Integer> replaceFixTags = new HashMap<>();
            replaceFixTags.put(207, -1);
            replaceFixTags.put(336, -1);
            fixConnection1.setReplaceFixTags(replaceFixTags);
*/
            Map<String, String> orderedTags = new HashMap<>();

            orderedTags.put("NBK", "20,37,11,41,17,39,150,1,207,55,54,38,40,44,59,126,432,32,31,151,14,6,60,12,64");


            ExchangeConnection exchangeConnection = fixConnectionFacade.getEmptyExchangeConnection();
            exchangeConnection.setExchange("NBK");
            exchangeConnection.setMasterAccountRoutingEnabled(PropertyEnable.NO);
            exchangeConnection.setOrderedFixTags(orderedTags);


            fixConnection1.getExchangeConnections().put(exchangeConnection.getExchange(), exchangeConnection);
            fixConnection1.setSessionQualifier("NBKPROD");
            fixConnectionFacade.create(fixConnection1);

        } catch (FIXConnectionException e) {
            e.printStackTrace();
        }
    }
}
