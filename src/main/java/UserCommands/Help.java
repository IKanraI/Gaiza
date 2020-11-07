package UserCommands;

import Command.Command;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import Management.BotInfo;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.File;
import java.util.List;

public class Help extends Command
{
	public Help(DiscordApi api) {
		super(api);

		api.addMessageCreateListener(e -> {
			helpCommand(api, getChannel(), getMessage(), getMessageAuthor(), getArgs());
		});

	}

	@SneakyThrows
	public void helpCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor author, List<String> args) {
		if (!onCommand(api, channel, message, author, args)) {
			return;
		}

		if (args.size() == 1) {
			String name = args.get(0).substring(0, 1).toUpperCase() + args.get(0).substring(1);
			channel.sendMessage(Class.forName("UserCommands." + name).getDeclaredField("help").get(0).toString())
			.exceptionally(e -> {
				channel.sendMessage("Command not found");
				return null;
			});
		} else {
			channel.sendMessage(buildEmbed(author.asUser().get()));
		}
	}

	private EmbedBuilder buildEmbed(User user) {
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(user.getDiscriminatedName(), user.getAvatar().getUrl().toString(), user.getAvatar())
				.setColor(Color.MAGENTA)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();
		try {
			File folder = new File("C:\\Users\\17244\\Documents\\JavaProjects\\Gaiza\\src\\main\\java\\UserCommands\\");
			for (File f : folder.listFiles()) {
				String currCommand = f.getName().replaceAll(".java", "");
				if (currCommand.equalsIgnoreCase("help")) {
					continue;
				}
				embed.addInlineField(getKey().replace("help", "")
						+ currCommand, Class.forName("UserCommands." + currCommand).getDeclaredField("help").get(0).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return embed;
	}
}
