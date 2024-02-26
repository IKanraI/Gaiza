package Database;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Command.Command;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONObject;

import Management.BotInfo;
import org.json.simple.parser.JSONParser;

public class GlobalUserInformation extends Command {
//	public static String filePath = "/home/kanra/projects/data/userData/";
	public static String filePath = "C:\\Users\\joelm\\Documents\\JavaProjects\\Hidden\\userData\\";
	private final String fineTag = "Fine Amount";
	
	public GlobalUserInformation(DiscordApi api) {
		super(api);
		getUsersList(api);
		api.addServerMemberJoinListener(event ->
				newUserJoins(event.getUser(), event.getServer()));
		
	}

	@SneakyThrows
	public void getUsersList(DiscordApi api) {
		int users = 0;
		for (Server s : api.getServers()) {
			for (User u : s.getMembers()) {

				File target = new File(filePath + u.getId() + ".json");
				if (target.createNewFile()) {
					JSONObject store = new JSONObject();
					store.put(fineTag, 0);
					Files.write(Paths.get(target.toString()), store.toJSONString().getBytes());
				}
			}
			users += s.getMemberCount();
		}
		BotInfo.setUserCount(users);
	}

	@SneakyThrows
	public void newUserJoins(User user, Server server) {
		File target = new File(filePath + user.getId() + ".json");
		if (target.createNewFile()) {
			JSONObject store = new JSONObject();
			store.put(fineTag, 0);
			Files.write(Paths.get(target.toString()), store.toJSONString().getBytes());
			System.out.println("User joined with id of " + user.getId());
		}
		if (!Boolean.parseBoolean(InitDatabase.getData().get(server.getIdAsString()).getWEnabled())) {
			return;
		}
		server.getChannelById(InitDatabase.getData().get(server.getIdAsString()).getWChannel())
				.get().asServerTextChannel().get()
				.sendMessage(InitDatabase.getData().get(server.getIdAsString()).getWMsg().replaceAll("<<mention>>", user.getMentionTag()))
				.exceptionally(e -> {
					System.err.println("Something went wrong with this welcome");
					return null;
				});
	}
}