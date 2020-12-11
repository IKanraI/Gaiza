package Database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Management.BotInfo;
import Management.Prefix;

public class InitDatabase
{
	private static DatabaseLL serverLL = new DatabaseLL();
	private Map<String, Servers> data = new HashMap();
	private final String dbPath = "C:\\Users\\17244\\Documents\\JavaProjects\\Gaiza\\bin\\Storage\\Servers\\";
	private final String keyFieldServerName = "Server Name";
	private final String keyFieldID = "ID";
	private final String keyFieldPrefix = "Prefix";
	private final String keyFieldWEnabled = "Welcome Enabled";
	private final String keyFieldWMessage = "Welcome Message";
	private final String keyFieldChannel = "Welcome Channel";
	
	public InitDatabase() {

	}
	public InitDatabase(DiscordApi api) throws Exception {
		splitServerInfo(api);
		manageDbFiles(api);
		saveDatabase();
		listenForNewServer(api);
		listenForLeaveServer(api);
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public void blankFileInit(String path, String id) {
		Files.write(Paths.get(path), data.get(id).toJSONString().toJSONString().getBytes());
	}
	
	public boolean checkForChanges(ArrayList<String> infoCheck, int serverSelect) {
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
	
	public void listenForNewServer(DiscordApi getApi) {
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
					blankFileInit(finalPath, serverID);
					
					serverLL.setServerPrefix(serverLL, Prefix.defaultPrefix, serverLL.size(serverLL) - ADDSERVER);
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
	
	public void listenForLeaveServer(DiscordApi getApi) {
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

	public void manageDbFiles(DiscordApi api) throws Exception {
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

		for (Server s : api.getServers()) {
			File target = new File(dbPath.concat(s.getIdAsString().concat(".json")));
			if (!target.exists()) {
				target.createNewFile();
				blankFileInit(target.toString(), s.getIdAsString());
			} else {
				while (trackList != BotInfo.getServerCount())
				{


					//If the file is already created, then it grabs the value out of the file and places it in the LL
					dataManip = parsePrefix.parse(new FileReader(target));
					getData = (JSONObject) dataManip;

					serverLL.setServerPrefix(serverLL, getData.get(keyFieldPrefix).toString(), trackList);
					serverLL.setWelcomeEnabled(serverLL, getData.get(keyFieldWEnabled).toString(), trackList);
					serverLL.setWelcomeMessage(serverLL, getData.get(keyFieldWMessage).toString(), trackList);
					serverLL.setWelcomeChannel(serverLL, getData.get(keyFieldChannel).toString(), trackList);


					++trackList;
				}
			}
		}


	}

	@SuppressWarnings("unchecked")	
	public void saveDatabase() {
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
	
	public void splitServerInfo(DiscordApi api) {
		for (Server s : api.getServers()) {
			data.put(s.getIdAsString(), new Servers(s.getIdAsString(), s.getName()));
			serverLL.insertNewServerInfo(serverLL, s.getIdAsString(), s.getName());
		}
		serverLL.printLinkedList(serverLL);
	}
	
	public static DatabaseLL getCurrLL() {
		return serverLL;
	}
}
