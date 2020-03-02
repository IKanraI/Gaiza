package management;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;

public class BotInfo 
{
	private static String botName;
	private static String botImageStr;
	private static Icon botImage;
	
	public BotInfo(DiscordApi getApi)
	{
		DiscordApi botApi = getApi;
		String getBotName;
		String getBotImageStr;
		Icon getBotIcon;
		
		getBotName = botApi.getYourself().getName();
		getBotImageStr = botApi.getYourself().getAvatar().getUrl().toString();
		getBotIcon = botApi.getYourself().getAvatar();
		
		setBotName(getBotName);
		setBotImageStr(getBotImageStr);
		setBotImage(getBotIcon);
		
		System.out.println("BotInfo.java loaded!");
		
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
}
