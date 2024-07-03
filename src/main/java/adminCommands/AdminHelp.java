package adminCommands;

import java.awt.Color;
import java.io.File;
import java.util.List;

import lombok.SneakyThrows;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import util.BotInfo;
import org.javacord.api.entity.user.User;

public class AdminHelp {
	public static String help = "Admin panel help menu. Usage: [prefix]AdminHelp";
	private static File folder;
//	public AdminHelp(DiscordApi api) {
//		super(api);
//		folder = new File("/home/kanra/projects/Gaiza/src/main/java/AdminCommands");
//		api.addMessageCreateListener(event ->
//				displayHelp(super.getChannel(), super.getMessageAuthor(), super.getArgs()));
//
//	}
	@SneakyThrows
	private void displayHelp(TextChannel channel, MessageAuthor author, List<String> args) {
//		if (!onAdminCommand()) {
//			return;
//		}
		if (args.size() == 0) {
			channel.sendMessage(buildEmbed(author.asUser().get()));
		} else if (args.size() == 1) {
			channel.sendMessage(Class.forName("AdminCommands."
					+ args.get(0).substring(0, 1).toUpperCase() + args.get(0).substring(1))
					.getDeclaredField("help").get(0).toString())
					.exceptionally(e -> {
						channel.sendMessage("Command not found");
						return null;
			});
		}
	}

	@SneakyThrows
	private EmbedBuilder buildEmbed(User user) {
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(user.getDiscriminatedName(), user.getAvatar().getUrl().toString(), user.getAvatar())
				.addField("Attention", "Most of these are incorrect and being worked on")
				.setColor(Color.MAGENTA)
				.setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage())
				.setTimestampToNow();

		for (File f : folder.listFiles()) {
			String currCommand = f.getName().replaceAll(".java", "");
			if (currCommand.equalsIgnoreCase("adminhelp")) {
				continue;
			}

//			embed.addInlineField(getKey().replace("adminhelp", "")
//					+ currCommand, Class.forName("AdminCommands." + currCommand).getDeclaredField("help").get(0).toString());

		}
		return embed;
	}
}
