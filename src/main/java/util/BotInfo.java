package util;

import config.PropertiesLoader;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.entity.Icon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Data
public class BotInfo {
    private static BotInfo instance = null;
    private Properties properties = null;

    private String ownerId;
    private String botId;
    private String botName;
    private String botImageStr;
    private String botActivity;
    private Icon botImage;
    private String botInvite;
    private String botRepo;
    private String tenorApiKey;
    private int serverCount;
    private int userCount;
    private String zaraiUserId;

    public static synchronized BotInfo getInstance() {
        if (instance == null)
            instance = new BotInfo();
        return instance;
    }

    public String getValue(String propKey) {
        return this.properties.getProperty(propKey);
    }

    private BotInfo() {
        try {
            properties = PropertiesLoader.loadProperties();
        } catch (IOException ioe) {
            log.error("Could not load properties file from system");
            log.error("OS: " + System.getProperty("os.name"));
            log.error("Error Message: " + ioe.getMessage());
        }
    }

}
