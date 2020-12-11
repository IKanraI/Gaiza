package Database;

import Management.Prefix;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

public class Servers {
    @Getter @Setter private String name;
    @Getter @Setter private String id;
    @Getter @Setter private String wChannel;
    @Getter @Setter private String wMsg;
    @Getter @Setter private String wEnabled;
    @Getter @Setter private String prefix;

    public Servers(String id, String name) {
        this.id = id;
        this.name = name;
        this.wChannel = "";
        this.wMsg = "";
        this.wEnabled = "";
        this.prefix = Prefix.defaultPrefix;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("Name", name);
        obj.put("Id", id);
        obj.put("Welcome Enabled", wEnabled);
        obj.put("Welcome Channel", wChannel);
        obj.put("Welcome Message", wMsg);

        return obj;
    }

    @Override
    public String toString() {
        return (id + " : " + name + " : " + wChannel + " : " + wMsg + " : " + wEnabled + " : " + prefix);
    }
}
