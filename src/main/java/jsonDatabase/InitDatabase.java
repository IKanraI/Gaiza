package jsonDatabase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import management.BotInfo;
import management.Keywords;

public class InitDatabase
{
	private static DatabaseLL serverLL = new DatabaseLL();
	private final String dbPath = "C:\\Users\\Cain\\Documents\\javaDocs\\gaiza\\bin\\Storage\\";
	private final String keyFieldServerName = "Server Name";
	private final String keyFieldID = "ID";
	private final String keyFieldPrefix = "Prefix";
	
	public InitDatabase()
	{
		
	}
	public InitDatabase(DiscordApi getApi) throws Exception
	{
		DiscordApi initApi = getApi;
		
		getServerList(initApi);
		manageDbFiles();
		saveDatabase();
		listenForNewServer(initApi);
	}
	
	public boolean checkForChanges(ArrayList<String> infoCheck, int serverSelect)
	{
		//Compares the information in the linked list that will be updated before the server file 
		//returns true if there are changes between the two
		ArrayList<String> getInfo = new ArrayList<String>();
		int getServerPos = serverSelect;
		int i;
		
		for (i = 0; i < infoCheck.size(); ++i)
		{
			getInfo.add(infoCheck.get(i));
		}
		
		if (!getInfo.get(0).equalsIgnoreCase(serverLL.getCurrServerName(serverLL, getServerPos)))
		{
			return true;
		}
		
		if (!getInfo.get(1).equalsIgnoreCase(serverLL.getCurrServerID(serverLL, getServerPos)))
		{
			return true;
		}
		
		if (!getInfo.get(2).equals(serverLL.getServerPrefix(serverLL, getServerPos)))
		{
			return true;
		}
		
		return false;
	}
	
	public void listenForNewServer(DiscordApi getApi)
	{
		DiscordApi newServerApi = getApi;
	}
	
	@SuppressWarnings("unchecked")
	public void manageDbFiles() throws Exception
	{	
		//Handles checking to see if the files already exists or not and initializes the files with information
		JSONObject initData = new JSONObject();
		JSONObject getData;
		JSONParser parsePrefix = new JSONParser();
		Object dataManip;
		
		String tempPath;
		String fileName;
		File saveServerFiles;
		File checkLength;
		
		boolean fileCheckExists;
		int trackList = 0;
		
		try
		{
			while (trackList != BotInfo.getServerCount())
			{
				//Runs the check for the number of servers and grabs each path then checks to see
				//if the file already exists
				fileName = serverLL.getCurrServerID(serverLL, trackList).concat(".json");
				tempPath = dbPath.concat(fileName);
				
				//System.out.println(tempPath);
				
				fileCheckExists = new File(tempPath).exists();
				
				if (!fileCheckExists)
				{
					//if the file doesn't exist it will create a new file
					saveServerFiles = new File(tempPath);
					saveServerFiles.createNewFile();
				}
				
				checkLength = new File(tempPath);
				
				if (checkLength.length() == 0)
				{
					//File initialization
					initData.put("ID", "");
					initData.put("Server Name", "");
					initData.put("Prefix", "\"" + Keywords.getDefaultKey() + "\"");
					
					serverLL.setServerPrefix(serverLL, Keywords.getDefaultKey(), trackList);
					
					Files.write(Paths.get(tempPath), initData.toJSONString().getBytes());
				}
				else
				{
					dataManip = parsePrefix.parse(new FileReader(tempPath));
					getData = (JSONObject) dataManip;
					
					serverLL.setServerPrefix(serverLL, getData.get(keyFieldPrefix).toString(), trackList);	
				}

				++trackList;
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void getServerList(DiscordApi getApi)
	{
		//Handles acquiring the list of servers that the bot is currently residing in from the api
		DiscordApi jsonApi = getApi;
		ArrayList<String> serverList = new ArrayList<String>();
		
		int i;
		
		for (i = 0; i < getApi.getServers().size(); ++i)
		{
			serverList.add(jsonApi.getServers().toArray()[i].toString());
		}
		
		//Storage in a static variable that can be referenced to later and will always be set on runtime
		BotInfo.setServerCount(serverList.size());
		
		splitServerInfo(serverList);
		
	}
	
	/*public void loadDatabase()
	{
		int i = 0;
		String tempPath;
		String getPath = dbPath;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			//tempPath = getPath.concat(serverLL.getCurrServerID(serverLL, ))
		}
	}*/
	
	@SuppressWarnings("unchecked")	
	public void saveDatabase()
	{
		//Saves all the information to the database if changes are made
		//Might want to make static potentially for easier calling
		ArrayList<String> tempCheck = new ArrayList<String>();
		
		JSONObject storageGetJSON;
		JSONObject saveData = new JSONObject();
		JSONParser parseData = new JSONParser();		
		Object checkStorage;
		
		String getDB = dbPath;
		String fullPath;
		int i;
		
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			//Iterates through to check if there are any changes to each sever
			fullPath = getDB.concat(serverLL.getCurrServerID(serverLL, i) + ".json");
			
			try
			{
				//Adds the information that is currently in the database to an arraylist
				tempCheck.clear();
				
				checkStorage = parseData.parse(new FileReader(fullPath));
				storageGetJSON = (JSONObject) checkStorage;

				tempCheck.add((String) storageGetJSON.get(keyFieldPrefix));
				tempCheck.add((String) storageGetJSON.get(keyFieldID));
				tempCheck.add((String) storageGetJSON.get(keyFieldServerName));
				
				if (checkForChanges(tempCheck, i))
				{
					//If there are changes made it will replace the current data with this data
					saveData.put(keyFieldPrefix, serverLL.getServerPrefix(serverLL, i));
					saveData.put(keyFieldID, serverLL.getCurrServerID(serverLL, i));
					saveData.put(keyFieldServerName, serverLL.getCurrServerName(serverLL, i));
					
					Files.write(Paths.get(fullPath), saveData.toJSONString().getBytes());
				}				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void splitServerInfo(ArrayList<String> serverList)
	{
		//Handles parsing out the server information in order to prepare it for storage
		ArrayList<String> getServers = serverList;
		
		String[] splitString = null;
		String condenseName = "";
		
		int i, j;
		int idIndex = 2;
		int nameIndex = 4;
		
		for (i = 0; i < getServers.size(); ++i)
		{
			//Splits the string into pieces and grabs the appropriate portions that contain server information
			//This is then stripped of any symbols that may interfere with storage
			splitString = getServers.get(i).split(" ");
			
			splitString[idIndex] = splitString[idIndex].replaceAll(",", "");
			splitString[splitString.length - 1] = splitString[splitString.length - 1].replaceAll("[)]", "");
			
			condenseName = "";
			
			//There is a chance for the name to be multiple words long so there needs to be checks
			//to make sure the end of the name is retrieved. The results is trimmed to keep out any trailing whitespace
			for (j = nameIndex; j < splitString.length; j++)
			{	
				condenseName = condenseName.concat(splitString[j] + " ");
			}			
			serverLL.insertNewServerInfo(serverLL, splitString[idIndex], condenseName.trim());
		}
		
		serverLL.printLinkedList(serverLL);
		
	}
	
	public static DatabaseLL getCurrLL()
	{
		return serverLL;
	}
}
