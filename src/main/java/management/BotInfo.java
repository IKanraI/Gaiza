package management;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;

public class BotInfo 
{
	private static String botName;
	private static String botImageStr;
	private static String botActivity;
	private static Icon botImage;
	private static int serverCount;
	private static int userCount;
	
	public BotInfo(DiscordApi getApi)
	{
		DiscordApi botApi = getApi;
		String getBotName;
		String getBotImageStr;
		String standardActivity;
		Icon getBotIcon;
		
		getBotName = botApi.getYourself().getName();
		getBotImageStr = botApi.getYourself().getAvatar().getUrl().toString();
		getBotIcon = botApi.getYourself().getAvatar();
		standardActivity = "Moving to Java | Commands WIP";
		
		setBotName(getBotName);
		setBotImageStr(getBotImageStr);
		setBotImage(getBotIcon);
		setBotActivity(standardActivity);
		
		System.out.println("BotInfo.java loaded!");
		
	}
	
	//Setters
	
	public void setBotActivity(String setActivity)
	{
		botActivity = setActivity;
	}
	
	private void setBotImage(Icon iconImage)
	{
		botImage = iconImage;
	}
	
	private void setBotName(String newName)
	{
		botName = newName;
	}
	
	private void setBotImageStr(String getString)
	{
		botImageStr = getString;
	}
	
	public static void setServerCount(int noOfServers)
	{
		serverCount = noOfServers;
	}
	
	public static void setUserCount(int noUsers)
	{
		userCount = noUsers;
	}
	
	//Getters
	
	public static String getBotActivity()
	{
		return botActivity;
	}
	
	public static Icon getBotImage()
	{
		return botImage;
	}
	
	public static String getBotName()
	{
		return botName;
	}
	
	public static String getBotImageStr()
	{
		return botImageStr;
	}
	
	public static int getServerCount()
	{
		return serverCount;
	}
	
	public static int getUserCount()
	{
		return userCount;
	}
}
