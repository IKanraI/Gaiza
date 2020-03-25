package management;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;

public class Keywords 
{
	private static String defaultKey = "$";
	
	public void setDefaultKey(String newKey)
	{
		defaultKey = newKey;
	}
	
	public void setKey(DatabaseLL modifyData, String newKey, int selectServer)
	{
		InitDatabase saveDB = new InitDatabase();
		DatabaseLL getLLData = modifyData;
		String getNewKey = newKey;
		int serverSlot = selectServer;
		getLLData.setServerPrefix(getLLData, getNewKey, serverSlot);
		
		saveDB.saveDatabase();
	}
	
	public static String getDefaultKey()
	{
		return defaultKey;
	}
	
	public String getKey(String serverID, int i)
	{
		DatabaseLL getDB = InitDatabase.getCurrLL();
		String getPrefix = "";
		int serverSlot = i;
		
		getPrefix = getDB.getServerPrefix(getDB, serverSlot);
		
		return getPrefix;
	}
}