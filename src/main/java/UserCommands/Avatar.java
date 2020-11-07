package UserCommands;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Command.Command;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import Management.BotInfo;
import Management.Keywords;

public class Avatar extends Command {
	@Getter
	private final String imageSize;
	@Getter
	public static String help = "Returns the avatar of a user.";
	
	public Avatar(DiscordApi api) {
		super(api);
		imageSize = "?size=256";
		api.addMessageCreateListener(e -> {
			avatarCommand(api, super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
		});
	}

	public void avatarCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor messageAuthor, List<String> args) {
		if(!onCommand(api, channel, message, messageAuthor, args)) {
			return;
		}
		if (message.getMentionedUsers().size() == 0 && args.size() == 1) {
			return;
		}

		switch(args.size()) {
			case 0:
				channel.sendMessage(buildOutputMessage(messageAuthor.asUser().get()));
				break;
			case 1:
				channel.sendMessage(buildOutputMessage(message.getMentionedUsers().get(0)))
						.exceptionally(e -> {
							channel.sendMessage("User not found");
							return null;
						});
				break;
			default:
				channel.sendMessage("Please either invoke just the command: ("
						+ super.getKey() + super.getCommand() + ") or the command with one user: ("
						+ super.getKey()+ super.getCommand() + " [username])")
				.exceptionally(e -> {
					e.printStackTrace();
					return null;
				});
				break;
		}
	}
	
	private EmbedBuilder buildOutputMessage(User user) {
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(user.getDiscriminatedName(), user.getAvatar().getUrl().toString(), user.getAvatar())
				.setColor(Color.magenta)
				.setImage(user.getAvatar().getUrl() + imageSize)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();
		return embed;
	}
}