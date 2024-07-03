package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties() throws IOException {
        String configFile = "linux.properties";

        if (System.getProperty("os.name").startsWith("Windows"))
            configFile = "windows.properties";

        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(configFile);
        configuration.load(inputStream);
        inputStream.close();

        return configuration;
    }
}
