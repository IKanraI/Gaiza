package Management;

import Database.DatabaseLL;
import Database.InitDatabase;
import lombok.Getter;

public class Prefix
{
	public static String defaultPrefix = "$";
	
	public void setDefaultKey(String newKey)
	{
		defaultPrefix = newKey;
	}
	
	public void setKey(String id, String prefix) {
		InitDatabase.getData().get(id).setPrefix(prefix);
		new InitDatabase().saveDatabase();

	}
	
	public static String getKey(String id) {
		return InitDatabase.getData().get(id).getPrefix();
	}
}