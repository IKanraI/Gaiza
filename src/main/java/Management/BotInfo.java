package Management;

import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.MessageAuthor;

public class BotInfo 
{
	@Getter private static String ownerId;
	@Getter private static String botId;
	@Getter private static String botName;
	@Getter private static String botImageStr;
	@Getter private static String botActivity;
	@Getter private static Icon botImage;
	@Getter private static MessageAuthor author;
	@Getter @Setter private static int serverCount;
	@Getter @Setter private static int userCount;
	{

	}
	public BotInfo(DiscordApi api)
	{
		ownerId = "150415472028811264";
		botId = api.getYourself().getIdAsString();
		botName = api.getYourself().getName();
		botImageStr = api.getYourself().getAvatar().getUrl().toString();
		botImage = api.getYourself().getAvatar();
		botActivity = "Sadayo Kawakami is Mommy";
		
	}
}
