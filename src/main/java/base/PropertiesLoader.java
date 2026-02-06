package base;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

    public Properties loadProperties() {
        try {
            FileReader reader = new FileReader(System.getProperty("user.dir") + java.io.File.separator + "config.properties");
            Properties properties = new Properties();
            properties.load(reader);
            return properties;
        } catch (IOException e) {
            RunLog.error("Error loading config.properties.  Exception: " + e.getMessage());
            throw new RuntimeException("Error loading config.properties");
        }
    }

}
