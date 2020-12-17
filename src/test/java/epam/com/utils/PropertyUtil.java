package epam.com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    public static String getProperty(String property){
        FileInputStream fis;
        Properties properties = new Properties();
        String propertyValue = "";
        try {
            fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
            propertyValue = properties.getProperty(property);
        } catch (IOException e) {
            System.err.println("Properties file not found.");
            System.exit(1);
        }
        return propertyValue;
    }
}
