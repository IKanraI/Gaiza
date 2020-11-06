package Management;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;

public class BotInfo 
{
	@Getter private static String botName;
	@Getter private static String botImageStr;
	@Getter private static String botActivity;
	@Getter private static Icon botImage;
	@Getter @Setter private static int serverCount;
	@Getter @Setter private static int userCount;
	
	public BotInfo(DiscordApi getApi)
	{
		DiscordApi botApi = getApi;

		botName = botApi.getYourself().getName();
		botImageStr = botApi.getYourself().getAvatar().getUrl().toString();
		botImage = botApi.getYourself().getAvatar();
		botActivity = "Sadayo Kawakami is Mommy";

		System.out.println("BotInfo.java loaded!");
		
	}
}
