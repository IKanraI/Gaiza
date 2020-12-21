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
	public static String filePath = "E:\\Bot\\bin\\uwuData\\";
	private String fineTag = "Fine Amount";
	
	public GlobalUserInformation(DiscordApi api) {
		super(api);
		getUsersList(api);
		api.addServerMemberJoinListener(event ->
				newUserJoins(event.getUser()));
		
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
	public void newUserJoins(User user) {
		File target = new File(filePath + user.getId() + ".json");
		if (target.createNewFile()) {
			JSONObject store = new JSONObject();
			store.put(fineTag, 0);
			Files.write(Paths.get(target.toString()), store.toJSONString().getBytes());
			System.out.println("User joined with id of " + user.getId());
		}
	}
}