package adminCommands;

import command.Command;
import model.InitDatabase;
import lombok.Getter;
import org.javacord.api.DiscordApi;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

import java.util.List;

public class Prefix {
	@Getter public static String help = "Changes the prefix of the server";
	
	public void changePrefix(Server server, TextChannel channel, List<String> args) {
//		if (!onAdminCommand()) {
//			return;
//		}
//		if (args.size() == 0) {
//			channel.sendMessage("Please enter a prefix to change to: " + getKey() + "[prefix]");
//			return;
//		}

		StringBuilder prefix = new StringBuilder();
		for (String s : args) {
			prefix.append(s).append(" ");
		}
		InitDatabase.getData().get(server.getIdAsString()).setPrefix(prefix.toString().trim());
		InitDatabase.saveDatabase();
		channel.sendMessage("Prefix was successfully updated to: " + prefix);
	}
}
