package lk.ac.ucsc.clientConnector.settings;

import lk.ac.ucsc.clientConnector.common.api.MessageDestination;
import lk.ac.ucsc.clientConnector.common.api.TrsConstants;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static lk.ac.ucsc.clientConnector.settings.TrsUtil.decrypt;
import static lk.ac.ucsc.clientConnector.settings.TrsUtil.encrypt;


public class Settings {
    private static Logger logger = LoggerFactory.getLogger(Settings.class);
    private Map<String, MessageDestination> routingMap;
    private String trsId;
    private int clientTimeout;
    private String requestConfigFilePath;
    private boolean webClientEnabled;
    private boolean frontOfficeEnabled;
    private boolean secondaryFrontOfficeEnabled;
    private int messageTimeout;
    private String frontDbPw;

    public boolean isWebClientEnabled() {
        return webClientEnabled;
    }

    public void setWebClientEnabled(boolean webClientEnabled) {
        this.webClientEnabled = webClientEnabled;
    }

    public boolean isFrontOfficeEnabled() {
        return frontOfficeEnabled;
    }

    public void setFrontOfficeEnabled(boolean frontOfficeEnabled) {
        this.frontOfficeEnabled = frontOfficeEnabled;
    }

    public boolean isNode02Enabled() {
        return secondaryFrontOfficeEnabled;
    }

    public void setNode02Enabled(boolean node02Enabled) {
        this.secondaryFrontOfficeEnabled = node02Enabled;
    }

    public int getClientSessionTimeout() {
        return clientTimeout;
    }

    public String getTrsId() {
        return trsId;
    }

    public void setTrsId(String trsId) {
        this.trsId = trsId;
    }

    public void setClientSessionTimeout(int clientSessionTimeout) {
        this.clientTimeout = clientSessionTimeout;
    }


    public void setRequestConfigFilePath(String requestConfigFilePath) {
        this.requestConfigFilePath = requestConfigFilePath;
    }

    public Map<String, MessageDestination> loadMessageRoutes() {
        routingMap = new HashMap<>();
        try {
            File trsConfigFile = new File(requestConfigFilePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(trsConfigFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName(TrsConstants.XML_TAG_MESSAGE);
            String group = null;
            String subGroup = null;
            String destination = null;
            for (int s = 0; s < nodeList.getLength(); s++) {
                Node node = nodeList.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    group = element.getAttribute(TrsConstants.XML_ATTRIBUTE_GROUP);
                    subGroup = element.getAttribute(TrsConstants.XML_ATTRIBUTE_SUBGROUP);
                    destination = element.getAttribute(TrsConstants.XML_ATTRIBUTE_MESSAGE_ROUTING_DESTINATION);
                    String key = group + "-" + subGroup;
                    if ("front_office".equals(destination)) {
                        routingMap.put(key, MessageDestination.FRONT_OFFICE);
                    }
                }
            }
            return routingMap;
        } catch (Exception e) {
            logger.error("Error loading routing configuration", e);
        }
        return null;
    }



    public void setMessageTimeout(int messageTimeout) {
        this.messageTimeout = messageTimeout;
    }

    public int getMessageTimeout() {
        return messageTimeout;
    }

    public void setFrontDbPw(String frontDbPw) {
        try {

            if (frontDbPw.startsWith("ENC:")) {
                // password is encrypted and base 64 encoded
                byte[] encPw = Base64.decodeBase64(frontDbPw.substring(4));
                this.frontDbPw = decrypt(encPw);

            } else {
                this.frontDbPw = frontDbPw;
                byte[] encPw = encrypt(frontDbPw);
                String base64Pw = "ENC:" + Base64.encodeBase64String(encPw);
                // update property file
                TrsUtil.updateTrsConfigFile("DB_PASSWORD", base64Pw);
            }
        } catch (Exception e) {
            logger.error("Error decrypting password");
        }
    }

    public String getFrontDbPw() {
        return frontDbPw;
    }




}
