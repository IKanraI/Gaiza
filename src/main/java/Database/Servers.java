package Database;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInput;
import java.io.File;
import java.io.FileReader;

@Getter
@Setter
public class Servers {
    public String name;
    public String id;
    public String wChannel;
    public String wMsg;
    public String wEnabled;
    public String prefix;
    public String uwu;
    public String lsChannel;
    public String lsEnabled;
    public String lsMessage;
    public String lsRole;


    public Servers(String id, String name) {
        this.id = id;
        this.name = name;
        this.wChannel = "";
        this.wMsg = "";
        this.wEnabled = "false";
        this.prefix = "$";
        this.uwu = "false";
        this.lsChannel = "";
        this.lsEnabled = "false";
        this.lsMessage = "";
        this.lsRole = "";
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("id", id);
        obj.put("prefix", prefix);
        obj.put("wEnabled", wEnabled);
        obj.put("wChannel", wChannel);
        obj.put("wMsg", wMsg);
        obj.put("uwu", uwu);
        obj.put("lsChannel", lsChannel);
        obj.put("lsEnabled", lsEnabled);
        obj.put("lsMessage", lsMessage);
        obj.put("lsRole", lsRole);

        ObjectMapper map = new ObjectMapper();
        return map.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
    @SneakyThrows
    public void loadJson(File target) {
        Object info = new JSONParser().parse(new FileReader(target));
        JSONObject obj = (JSONObject) info;

        name = obj.get("name").toString();
        id = obj.get("id").toString();
        prefix = obj.get("prefix").toString();
        wChannel = obj.get("wChannel").toString();
        wMsg = obj.get("wMsg").toString();
        wEnabled = obj.get("wEnabled").toString();
        uwu = obj.get("uwu").toString();
        lsChannel = obj.get("lsChannel").toString();
        lsEnabled = obj.get("lsEnabled").toString();
        lsMessage = obj.get("lsMessage").toString();
        lsRole = obj.get("lsRole").toString();
    }

    public void consolePrint(DiscordApi api) {
        System.out.printf("%s2 %-20s %1s%n", "Id: " + id, " | Members: " + api.getServerById(id).get().getMembers().size(), "| Name: " + name);
    }

    @Override
    public String toString() {
        return ("id: " + id + " Name: " + name + " Welcome Channel: " + wChannel + " Welcome Message: "
                + wMsg + " Welcome Enabled: " + wEnabled + " prefix: " + prefix + " uwu: " + uwu
                + " Live Stream Channel: " + lsChannel + " Live Stream Enabled: " + lsEnabled
                + " Live Stream message: " + lsMessage + " Live Stream Role: " + lsRole);
    }
}
