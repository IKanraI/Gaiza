package Database;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.DiscordApi;
import org.json.simple.JSONObject;

import Management.BotInfo;

public class GlobalUserInformation 
{
	private static Map <String , String> userIDList = new HashMap<>();
	private static String filePath = "E:\\Bot\\bin\\uwuData\\";
	private String uwuAmount = "Fine Amount";
	
	public GlobalUserInformation(DiscordApi getApi)
	{
		DiscordApi userApi = getApi;
		
		getUsersList(userApi);
		checkForNewUsers();
		newUserJoins(userApi);
		
	}

	public static String getUserByIdDb (String id) {
		return userIDList.get(id);
	}
	
	public static Map<String, String> getUserIDList()
	{
		return userIDList;
	}
	
	public void addToList(String ID)
	{
		String userID = ID;
		String userIDPath = filePath + ID + ".json";
		
		userIDList.put(userID, userIDPath);
		
	}
	
	@SuppressWarnings("unchecked")
	public void blankFileInit(String getUserID)
	{
		JSONObject initData = new JSONObject();
		String userID = getUserID;
		String userPath = "";
		long initValue = 0;
		
		userPath = filePath + userID + ".json";
		
		initData.put(uwuAmount, initValue);
		
		try
		{
			Files.write(Paths.get(userPath), initData.toJSONString().getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void checkForNewUsers()
	{
		File createNewFile;
		boolean fileExists;
		String checkFilePath = "";
		String fileKey = "";
		
		for (Map.Entry<String, String> mapElement : userIDList.entrySet())
		{
			fileKey = mapElement.getKey();
			checkFilePath = mapElement.getValue();
			fileExists = new File(checkFilePath).exists();
			
			if (!fileExists)
			{
				try
				{
					createNewFile = new File(checkFilePath);
					createNewFile.createNewFile();
					blankFileInit(fileKey);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void getUsersList(DiscordApi getApi)
	{
		DiscordApi userInitApi = getApi;
		ArrayList<String> generalUserInfo = new ArrayList<String>();
		DatabaseLL getServersLL = InitDatabase.getCurrLL();
		
		int usersInServer = 0;
		int i, j;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			for (j = 0; j < userInitApi.getServerById(getServersLL.getCurrServerID(getServersLL, i)).get().getMemberCount(); ++j)
			{
				generalUserInfo.add(userInitApi.getServerById(getServersLL.getCurrServerID(getServersLL, i)).get().getMembers().toArray()[j].toString());
				usersInServer += userInitApi.getServerById(getServersLL.getCurrServerID(getServersLL, i)).get().getMemberCount();
			}
		}
		
		BotInfo.setUserCount(usersInServer);
		
		splitUserID(generalUserInfo);
	}
	
	public void newUserJoins(DiscordApi getApi)
	{
		DiscordApi joinNewApi = getApi;
		
		joinNewApi.addServerMemberJoinListener(event ->
		{
			String userID;
			String userFilePath;
			boolean fileExists;
			
			userID = event.getUser().getIdAsString();
			userFilePath = filePath + userID + ".json";
			
			fileExists = new File(userFilePath).exists();
			
			if (!fileExists)
			{
				addToList(userID);
				blankFileInit(userID);
				
				System.out.println("User joined with ID of " + userID);
			}	
		});
	}
	
	public void splitUserID(ArrayList<String> userInfo)
	{
		ArrayList<String> userInfoSplit = userInfo;
		String splitString[] = null;
		String userID = "";
		
		final int IDINDEX = 2;
		int i;
		
		for (i = 0; i < userInfoSplit.size(); ++i)
		{
			splitString = userInfoSplit.get(i).split(" ");
			userID = splitString[IDINDEX].replaceAll(",", "");
			
			addToList(userID);
		}
	}	
}