package lk.ac.ucsc.clientConnector.settings;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;


public class TrsUtil {
    private static String IV = "AAAAAAAAAAAAAAAA";
    static String encryptionKey = "0123456789abcdef";
    private static String trsConfigFilePath = "config" + File.separator + "settings.ini";



    public static byte[] encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding ", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding ", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }

    public static void updateTrsConfigFile(String property, String newValue) {
        try {
            File file = new File(trsConfigFilePath);

            PropertiesConfiguration config = new PropertiesConfiguration();
            PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
            layout.load(new InputStreamReader(new FileInputStream(file)));
            config.setProperty(property, newValue);
            layout.save(new FileWriter(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
