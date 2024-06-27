package management;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BotInfo {
    //Improve upon this somehow. I should probably add a way to inject variables into classes
	@Getter private static String ownerId;
	@Getter private static String botId;
	@Getter private static String botName;
	@Getter private static String botImageStr;
	@Getter private static String botActivity;
	@Getter private static Icon botImage;
	@Getter private static String botInvite;
	@Getter private static String botRepo;
	@Getter private static String tenorApiKey;
	@Getter @Setter private static int serverCount;
	@Getter @Setter private static int userCount;
    @Getter private static String zaraiUserId;


	@SneakyThrows
	public BotInfo(DiscordApi api) {
		ownerId = String.valueOf(api.getOwnerId());
		botId = api.getYourself().getIdAsString();
		botName = api.getYourself().getName();
		botImageStr = api.getYourself().getAvatar().getUrl().toString();
		botImage = api.getYourself().getAvatar();
		botInvite = "https://discordapp.com/oauth2/authorize?client_id=369295519576489984&scope=bot&permissions=2146561111";
		botRepo = "https://github.com/IKanraI/Gaiza";
		serverCount = api.getServers().size();
		zaraiUserId = "422153253170708490";

//		botActivity = Files.readAllLines(Paths.get("/home/kanra/projects/data/botActivity")).get(0);
//		tenorApiKey = Files.readAllLines(Paths.get("/home/kanra/projects/data/tenor")).get(0);

		tenorApiKey = Files.readAllLines(Paths.get("C:\\Users\\joelm\\Documents\\JavaProjects\\Hidden\\tenor.txt")).get(0);
		botActivity = Files.readAllLines(Paths.get("C:\\Users\\joelm\\Documents\\JavaProjects\\Hidden\\botActivity.txt")).get(0);
	}
}
