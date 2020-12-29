package Management;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BotInfo {
	@Getter private static String ownerId;
	@Getter private static String botId;
	@Getter private static String botName;
	@Getter private static String botImageStr;
	@Getter private static String botActivity;
	@Getter private static Icon botImage;
	@Getter private static String botInvite;
	@Getter private static String botRepo;
	@Getter @Setter private static int serverCount;
	@Getter @Setter private static int userCount;

	@SneakyThrows
	public BotInfo(DiscordApi api) {
		ownerId = String.valueOf(api.getOwnerId());
		botId = api.getYourself().getIdAsString();
		botName = api.getYourself().getName();
		botImageStr = api.getYourself().getAvatar().getUrl().toString();
		botImage = api.getYourself().getAvatar();
		botActivity = Files.readAllLines(Paths.get("C:\\Users\\joelm\\Desktop\\BotActivity.txt")).get(0);
		botInvite = "https://discordapp.com/oauth2/authorize?client_id=369295519576489984&scope=bot&permissions=2146561111";
		botRepo = "https://github.com/IKanraI/Gaiza";
		serverCount = api.getServers().size();
	}
}
