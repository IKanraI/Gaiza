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
	private final String dbPath = "C:\\Users\\Cain\\Documents\\javaDocs\\gaiza\\bin\\Storage\\Servers\\";
	private final String keyFieldServerName = "Server Name";
	private final String keyFieldID = "ID";
	private final String keyFieldPrefix = "Prefix";
	private final String keyFieldWEnabled = "Welcome Enabled";
	private final String keyFieldWMessage = "Welcome Message";
	private final String keyFieldChannel = "Welcome Channel";
	
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
		listenForLeaveServer(initApi);
	}
	
	@SuppressWarnings("unchecked")
	public void blankFileInit(String getServerPath)
	{
		JSONObject initData = new JSONObject();
		String filePath = getServerPath;
		
		initData.put(keyFieldID, "");
		initData.put(keyFieldServerName, "");
		initData.put(keyFieldPrefix, Keywords.getDefaultKey());
		
		initData.put(keyFieldWEnabled, "false");
		initData.put(keyFieldWMessage, "");
		initData.put(keyFieldChannel, "");
		
		try 
		{
			Files.write(Paths.get(filePath), initData.toJSONString().getBytes());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
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
		
		if (!getInfo.get(3).equals(serverLL.getServerWelcomeEnabled(serverLL, getServerPos)))
		{
			return true;
		}
		
		if (!getInfo.get(4).equals(serverLL.getServerWelcomeMessage(serverLL, getServerPos)))
		{
			return true;
		}
		
		if (!getInfo.get(5).equals(serverLL.getServerWelcomeEnabled(serverLL, getServerPos)))
		{
			return true;
		}
		
		return false;
	}
	
	public void listenForNewServer(DiscordApi getApi)
	{
		DiscordApi newServerApi = getApi;
		
		
		newServerApi.addServerJoinListener(event ->
		{
			String serverName = "";
			String serverID = "";
			String filePath = "";
			String finalPath = "";
			
			File saveNewServerFile;
			boolean fileExists;
			final int ADDSERVER = 1;
			
			JSONObject getPrefixJSON = null;
			JSONParser prefixData = new JSONParser();
			Object checkStorage;
			
			filePath = dbPath;
			
			serverID = event.getServer().getIdAsString();
			serverName = event.getServer().getName();
			
			finalPath = filePath.concat(serverID + ".json");
			
			serverLL.insertNewServerInfo(serverLL, serverID, serverName);
			
			fileExists = new File(finalPath).exists();
			
			if (!fileExists)
			{
				try 
				{
					saveNewServerFile = new File(finalPath);
					saveNewServerFile.createNewFile();
					blankFileInit(finalPath);
					
					serverLL.setServerPrefix(serverLL, Keywords.getDefaultKey(), serverLL.size(serverLL) - ADDSERVER);
					serverLL.setWelcomeEnabled(serverLL, "false", serverLL.size(serverLL) - ADDSERVER);
					serverLL.setWelcomeMessage(serverLL, "", serverLL.size(serverLL) - ADDSERVER);
					serverLL.setWelcomeChannel(serverLL, "", serverLL.size(serverLL) - ADDSERVER);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					checkStorage = prefixData.parse(new FileReader(finalPath));
					getPrefixJSON = (JSONObject) checkStorage;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				serverLL.setServerPrefix(serverLL, getPrefixJSON.get(keyFieldPrefix).toString(), serverLL.size(serverLL) - ADDSERVER);
				serverLL.setWelcomeEnabled(serverLL, getPrefixJSON.get(keyFieldWEnabled).toString(), serverLL.size(serverLL) - ADDSERVER);
				serverLL.setWelcomeMessage(serverLL, getPrefixJSON.get(keyFieldWMessage).toString(), serverLL.size(serverLL) - ADDSERVER);
				serverLL.setWelcomeChannel(serverLL, getPrefixJSON.get(keyFieldChannel).toString(), serverLL.size(serverLL) - ADDSERVER);
			}
			
			BotInfo.setServerCount(BotInfo.getServerCount() + ADDSERVER);
			
			System.out.println("The server " + serverName + " joined with ID of " + serverID + "!");
			
			saveDatabase();
		});
	}
	
	public void listenForLeaveServer(DiscordApi getApi)
	{
		final int REMOVESERVER = 1;
		DiscordApi newServerApi = getApi;
		
		newServerApi.addServerLeaveListener(event ->
		{
			String serverID = "";
			String serverName = "";
			int i;
			
			serverID = event.getServer().getIdAsString();
			serverName = event.getServer().getName();
			
			for(i = 0; i < BotInfo.getServerCount(); ++i)
			{
				if (serverID.equals(serverLL.getCurrServerID(serverLL, i)))
				{
					serverLL.removeNode(serverLL, i);
					
					BotInfo.setServerCount(BotInfo.getServerCount() - REMOVESERVER);
					
					System.out.println("The server " + serverName + " left with the ID of " + serverID + ".");
					
					break;
				}
			}
			
		});
	}

	public void manageDbFiles() throws Exception
	{	
		//Handles checking to see if the files already exists or not and initializes the files with information
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
					//File initialization. Handles adding to a freshly added server's linked list
					blankFileInit(tempPath);
					
					serverLL.setServerPrefix(serverLL, Keywords.getDefaultKey(), trackList);
					serverLL.setWelcomeEnabled(serverLL, "false", trackList);
					serverLL.setWelcomeMessage(serverLL, "", trackList);
					serverLL.setWelcomeChannel(serverLL, "", trackList);
					
				}
				else
				{
					//If the file is already created, then it grabs the value out of the file and places it in the LL
					dataManip = parsePrefix.parse(new FileReader(tempPath));
					getData = (JSONObject) dataManip;
					
					serverLL.setServerPrefix(serverLL, getData.get(keyFieldPrefix).toString(), trackList);
					serverLL.setWelcomeEnabled(serverLL, getData.get(keyFieldWEnabled).toString(), trackList);
					serverLL.setWelcomeMessage(serverLL, getData.get(keyFieldWMessage).toString(), trackList);
					serverLL.setWelcomeChannel(serverLL, getData.get(keyFieldChannel).toString(), trackList);
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
				tempCheck.add((String) storageGetJSON.get(keyFieldWEnabled));
				tempCheck.add((String) storageGetJSON.get(keyFieldWMessage));
				tempCheck.add((String) storageGetJSON.get(keyFieldChannel));
				
				if (checkForChanges(tempCheck, i))
				{
					//If there are changes made it will replace the current data with this data
					saveData.put(keyFieldPrefix, serverLL.getServerPrefix(serverLL, i));
					saveData.put(keyFieldID, serverLL.getCurrServerID(serverLL, i));
					saveData.put(keyFieldServerName, serverLL.getCurrServerName(serverLL, i));
					saveData.put(keyFieldWEnabled, serverLL.getServerWelcomeEnabled(serverLL, i));
					saveData.put(keyFieldWMessage, serverLL.getServerWelcomeMessage(serverLL, i));
					saveData.put(keyFieldChannel, serverLL.getServerWelcomeChannel(serverLL, i));
					
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
