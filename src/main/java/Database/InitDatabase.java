package Database;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import Command.Command;
import lombok.Getter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Management.BotInfo;

public class InitDatabase extends Command {
	@Getter private static Map<String, Servers> data = new HashMap();
	@Getter private static String dbPath = "C:\\Users\\17244\\Documents\\JavaProjects\\Gaiza\\bin\\Storage\\Servers\\";

	public InitDatabase(DiscordApi api) {
		super(api);
		initializeServers(api);
		api.addServerJoinListener(event ->
			serverJoin(super.getServer()));
		api.addServerLeaveListener(event ->
				serverLeave(super.getServer()));
	}

	@SneakyThrows
	public static boolean checkForChanges(String path, String id) {
		Object obj = new JSONParser().parse(new FileReader(path));
		JSONObject read = (JSONObject) obj;

		for(Field f : data.get(id).getClass().getDeclaredFields()) {
			if (!read.get(f.getName()).equals(f.get(data.get(id)))) {
				return true;
			}
		}
		return false;
	}

	@SneakyThrows
	public void serverJoin(Server server) {
		File target = new File(dbPath + server.getIdAsString() + ".json");
		data.put(server.getIdAsString(), new Servers(server.getIdAsString(), server.getName()));

		if (target.createNewFile()) {
			Files.write(Paths.get(target.toString()), data.get(server.getIdAsString()).toJSONString().getBytes());
		} else {
			data.get(server.getIdAsString()).loadJson(target);
		}

		BotInfo.setServerCount(BotInfo.getServerCount() + 1);
		System.out.println("The server " + server.getName() + " joined with ID of " + server.getIdAsString() + ", with " + server.getMembers().size() + " members!");
		saveDatabase();
	}
	
	public void serverLeave(Server server) {
		data.remove(server.getIdAsString());
		BotInfo.setServerCount(BotInfo.getServerCount() - 1);
		System.out.println("Server " + server.getName() + " has left with id of " + server.getIdAsString());
	}

	@SneakyThrows
	public void initializeServers(DiscordApi api) {
		for (Server s : api.getServers()) {
			data.put(s.getIdAsString(), new Servers(s.getIdAsString(), s.getName()));
			data.get(s.getIdAsString()).consolePrint(api);
			File target = new File(dbPath.concat(s.getIdAsString().concat(".json")));

			if (target.createNewFile()) {
				Files.write(Paths.get(target.toString()), data.get(s.getIdAsString()).toJSONString().getBytes());
			} else {
				data.get(s.getIdAsString()).loadJson(target);
			}
		}

		//printData();
	}

	@SneakyThrows
	@SuppressWarnings("unchecked")	
	public static void saveDatabase() {
		for (Map.Entry<String, Servers> s : data.entrySet()) {
			String currPath = dbPath.concat(s.getKey() + ".json");

			if (checkForChanges(currPath, s.getKey())) {
				Files.write(Paths.get(currPath), data.get(s.getKey()).toJSONString().getBytes());
			}
		}
	}

	public static void printData() {
		for (Map.Entry<String, Servers> s : data.entrySet()) {
			System.err.println(s.getKey() + " " + s.getValue().getId());
			System.err.println(s.getKey() + " " + s.getValue().getName());
			System.err.println(s.getKey() + " " + s.getValue().getPrefix());
			System.err.println(s.getKey() + " " + s.getValue().getWEnabled());
			System.err.println(s.getKey() + " " + s.getValue().getWChannel());
			System.err.println(s.getKey() + " " + s.getValue().getUwu());
		}
	}
}
