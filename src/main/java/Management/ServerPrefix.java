package Management;

import Database.InitDatabase;

public class ServerPrefix
{
	public static String defaultPrefix = "$";
	
	public static void setKey(String id, String prefix) {
		InitDatabase.getData().get(id).setPrefix(prefix);
		InitDatabase.saveDatabase();

	}
	
	public static String getKey(String id) {
		return InitDatabase.getData().get(id).getPrefix();
	}
}