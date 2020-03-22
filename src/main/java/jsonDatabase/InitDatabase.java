package jsonDatabase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.json.simple.JSONObject;

import management.BotInfo;

public class InitDatabase
{
	private DatabaseLL serverLL = new DatabaseLL();
	String dbPath = "C:\\Users\\Cain\\Documents\\javaDocs\\gaiza\\bin\\Storage\\";
	
	public InitDatabase(DiscordApi getApi) throws IOException
	{
		DiscordApi initApi = getApi;
		
		getServerList(initApi);
		manageDbFiles();
		saveDatabase();
	}
	
	public void manageDbFiles() throws IOException
	{	
		String tempPath;
		String fileName;
		File saveServerFiles;
		boolean fileCheckExists;
		int trackList = 0;
		
		try
		{
			while (trackList != BotInfo.getServerCount())
			{
				fileName = serverLL.getCurrServerID(serverLL, trackList).concat(".json");
				tempPath = dbPath.concat(fileName);
				
				System.out.println(tempPath);
				
				fileCheckExists = new File(tempPath).exists();
				
				if (!fileCheckExists)
				{
					saveServerFiles = new File(tempPath);
					saveServerFiles.createNewFile();
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
		DiscordApi jsonApi = getApi;
		ArrayList<String> serverList = new ArrayList<String>();
		
		int i;
		
		for (i = 0; i < getApi.getServers().size(); ++i)
		{
			serverList.add(jsonApi.getServers().toArray()[i].toString());
		}
		
		/*for (i = 0; i < serverList.size(); ++i)
		{
			System.out.println(serverList.get(i));
		}*/
		
		BotInfo.setServerCount(serverList.size());
		
		splitServerInfo(serverList);
		
	}
	
	@SuppressWarnings("unchecked")
	
	public void saveDatabase()
	{
		JSONObject storageJSON = new JSONObject();
		String getDB = dbPath;
		String fullPath;
		int i;
		
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			storageJSON.put("ID:", serverLL.getCurrServerID(serverLL, i));
			storageJSON.put("Server Name:", serverLL.getCurrServerName(serverLL, i));
			
			fullPath = getDB.concat(serverLL.getCurrServerID(serverLL, i) + ".json");
			
			try
			{
				Files.write(Paths.get(fullPath), storageJSON.toJSONString().getBytes());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void splitServerInfo(ArrayList<String> serverList)
	{
		ArrayList<String> getServers = serverList;
		
		
		String[] splitString = null;
		String condenseName = "";
		
		int i, j;
		int idIndex = 2;
		int nameIndex = 4;
		
		for (i = 0; i < getServers.size(); ++i)
		{
			splitString = getServers.get(i).split(" ");
			
			splitString[idIndex] = splitString[idIndex].replaceAll(",", "");
			splitString[splitString.length - 1] = splitString[splitString.length - 1].replaceAll("[)]", "");
			
			condenseName = "";
			
			for (j = nameIndex; j < splitString.length; j++)
			{		
				condenseName = condenseName.concat(splitString[j] + " ");
			}			
			serverLL.insertNewServerInfo(serverLL, splitString[idIndex], condenseName);
		}
		
		serverLL.printLinkedList(serverLL);
		
	}
}
