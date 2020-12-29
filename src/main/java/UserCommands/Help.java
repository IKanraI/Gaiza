package UserCommands;

import Command.Command;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import Management.BotInfo;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Help extends Command {
	@Setter @Getter
	private Map<String, File> folders = new HashMap();

	public Help(DiscordApi api) {
		super(api);
		folders.put("UserCommands.", new File("C:\\Users\\joelm\\Documents\\JavaProjects\\Gaiza\\src\\main\\java\\UserCommands\\"));
		folders.put("UserMentions.", new File("C:\\Users\\joelm\\Documents\\JavaProjects\\Gaiza\\src\\main\\java\\UserMentions\\"));

		api.addMessageCreateListener(e ->
			helpCommand(getChannel(), getMessageAuthor(), getArgs()));
	}

	@SneakyThrows
	public void helpCommand(TextChannel channel, MessageAuthor author, List<String> args) {
		if (!onCommand()) {
			return;
		}
		if (args.size() == 0) {
			channel.sendMessage(buildEmbed(author.asUser().get()));
		} else if (args.size() == 1) {

			String name = args.get(0).substring(0, 1).toUpperCase() + args.get(0).substring(1);
			String type = "";

			for (Map.Entry<String, File> pack : folders.entrySet()) {
				for (final File file : pack.getValue().listFiles()) {
					if (file.getName().equals(name + ".java")) {
						type = pack.getKey();
						break;
					}
				}
			}
			if (type.isEmpty()) {
				channel.sendMessage("Please send a valid command. Use " + super.getKey() +  " to see all of the commands");
				return;
			}

			channel.sendMessage(Class.forName(type + name).getDeclaredField("help").get(0).toString())
			.exceptionally(e -> {
				channel.sendMessage("Command not found");
				return null;
			});
		}
	}

	public EmbedBuilder buildEmbed(User user) {
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(user.getDiscriminatedName(), user.getAvatar().getUrl().toString(), user.getAvatar())
				.setColor(Color.MAGENTA)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();

		folders.forEach((folder, file) -> {
			for (File f : file.listFiles()) {
				String currCommand = f.getName().replaceAll(".java", "");
				if (currCommand.equalsIgnoreCase("help")) {
					continue;
				}

				try {
					embed.addInlineField(getKey().replace("help", "")
							+ currCommand, Class.forName(folder + currCommand).getDeclaredField("help").get(0).toString());
				} catch (Exception e) {
					System.err.println(currCommand + " : " + folder + " : ");
				}
			}
		});

		return embed;
	}
}
