package AdminCommands;

import Command.Command;
import Database.InitDatabase;
import lombok.Getter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public class Kick extends Command {
	@Getter
	public static String help = "Kicks user with or without a reason";
	
	public Kick(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
				kickUser(super.getMessage(), super.getServer(), super.getChannel(), super.getMessageAuthor(), super.getArgs()));
	}

	@SneakyThrows
	public void kickUser(Message message, Server server, TextChannel channel, MessageAuthor author, List<String> args) {
		if (!onAdminCommand()) {
			return;
		}
		if (message.getMentionedUsers().size() != 1) {
			channel.sendMessage("Please mention a user");
			return;
		}
		if (!author.canKickUserFromServer(message.getMentionedUsers().get(0))) {
			channel.sendMessage("User cannot be kicked");
			return;
		}

		StringBuilder msg = new StringBuilder();
		if (args.size() > 1) {
			for (String s : args) {
				if (s.equals("<@!" + message.getMentionedUsers().get(0).getIdAsString() + ">")) {
					continue;
				}
				msg.append(s + " ");
			}
		} else {
			msg.append("No reason provided");
		}
		server.kickUser(message.getMentionedUsers().get(0), msg.toString()).exceptionally(e -> {
			channel.sendMessage("User could not be kicked");
			return null;
		});
		Thread.sleep(1500);
		for (Server s : message.getMentionedUsers().get(0).getMutualServers()) {
			if (s.getIdAsString().equals(server.getIdAsString())) {
				return;
			}
		}

		channel.sendMessage("User has been kicked with reason: " + msg);
		message.addReaction("\u2705");
	}
}