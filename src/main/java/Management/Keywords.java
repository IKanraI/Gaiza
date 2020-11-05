package Management;

import Database.DatabaseLL;
import Database.InitDatabase;

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
	
	public static String getKey(String serverID)
	{
		DatabaseLL getDB = InitDatabase.getCurrLL();
		String serverToGet = serverID;
		String getPrefix = "";
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (serverToGet.equals(getDB.getCurrServerID(getDB, i)))
			{
				getPrefix = getDB.getServerPrefix(getDB, i);
			}
		}
		
		return getPrefix;
	}
}