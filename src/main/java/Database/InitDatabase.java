package Database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import lombok.Getter;
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
	@Getter private static Map<String, Servers> data = new HashMap();
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
		listenForNewServer(api);
		listenForLeaveServer(api);
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public void blankFileInit(String path, String id) {
		Files.write(Paths.get(path), data.get(id).toJSONString().toJSONString().getBytes());
	}

	@SneakyThrows
	public boolean checkForChanges(String path, String id) {
		Object obj = new JSONParser().parse(new FileReader(path));
		JSONObject read = (JSONObject) obj;

		for(Field f : data.get(id).getClass().getDeclaredFields()) {
			if (!read.get(f.getName()).equals(f.get(data.get(id)))) {
				return true;
			}
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
		for (Server s : api.getServers()) {
			File target = new File(dbPath.concat(s.getIdAsString().concat(".json")));
			if (!target.exists()) {
				target.createNewFile();
				blankFileInit(target.toString(), s.getIdAsString());
			} else {
				Object obj = new JSONParser().parse(new FileReader(target));
				JSONObject storage = (JSONObject) obj;
				data.get(s.getIdAsString()).loadJson(storage);
			}
		}
	}

	@SneakyThrows
	@SuppressWarnings("unchecked")	
	public void saveDatabase() {
		for (Map.Entry<String, Servers> s : data.entrySet()) {
			String currPath = dbPath.concat(s.getKey() + ".json");

			if (checkForChanges(currPath, s.getKey())) {
				Files.write(Paths.get(currPath), data.get(s.getKey()).toJSONString().toJSONString().getBytes());
			}
		}
	}
	
	public void splitServerInfo(DiscordApi api) {
		for (Server s : api.getServers()) {
			data.put(s.getIdAsString(), new Servers(s.getIdAsString(), s.getName()));
			serverLL.insertNewServerInfo(serverLL, s.getIdAsString(), s.getName());
			data.get(s.getIdAsString()).consolePrint(api);
		}
	}
	
	public static DatabaseLL getCurrLL() {
		return serverLL;
	}
}
