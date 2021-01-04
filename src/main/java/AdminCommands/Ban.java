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

public class Ban extends Command {
	@Getter
	public static String help = "Bans user with or without a reason";
	
	public Ban(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
				banUser(super.getMessage(), super.getServer(), super.getChannel(), super.getMessageAuthor(), super.getArgs()));
	}

	@SneakyThrows
	public void banUser(Message message, Server server, TextChannel channel, MessageAuthor author, List<String> args) {
		if (!onAdminCommand()) {
			return;
		}
		if (message.getMentionedUsers().size() != 1) {
			channel.sendMessage("Please mention a user");
			return;
		}
		if (!author.canBanUserFromServer(message.getMentionedUsers().get(0))) {
			channel.sendMessage("User cannot be kicked");
			return;
		}
		try {
			if (args.size() > 1 && (Integer.parseInt(args.get(1)) < 0 || Integer.parseInt(args.get(1)) > 7)) {
				channel.sendMessage("Please input an amount of days to delete messages from the user between 0 and 7 days");
				return;
			}
		} catch (Exception e) {
			channel.sendMessage("Please provide a number 0 - 7 for how long to delete messages with the ban " + getKey().substring(0, 1) + "ban [user] <0-7> <reason>");
			return;
		}


		StringBuilder msg = new StringBuilder();
		if (args.size() > 2) {
			for (String s : args) {
				if (s.equals("<@!" + message.getMentionedUsers().get(0).getIdAsString() + ">")) {
					continue;
				}
				msg.append(s + " ");
			}
			msg.replace(0, 2, "");
		} else {
			msg.append("No reason provided");
		}
		int days = args.size() == 1 ? 0 : Integer.parseInt(args.get(1));

		server.banUser(message.getMentionedUsers().get(0), days, msg.toString()).exceptionally(e -> {
			channel.sendMessage("User could not be banned");
			return null;
		});
		Thread.sleep(1500);
		for (Server s : message.getMentionedUsers().get(0).getMutualServers()) {
			if (s.getIdAsString().equals(server.getIdAsString())) {
				return;
			}
		}

		channel.sendMessage("User has been banned with reason: " + msg);
		message.addReaction("\u2705");
	}
}
