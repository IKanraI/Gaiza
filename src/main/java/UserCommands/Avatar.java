package UserCommands;
import java.awt.Color;
import java.util.List;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import Management.BotInfo;

public class Avatar extends Command {
	@Getter
	private final String imageSize;
	@Getter
	public static String help = "Returns the avatar of a user.";
	
	public Avatar(DiscordApi api) {
		super(api);
		imageSize = "?size=256";
		api.addMessageCreateListener(e ->
			avatarCommand(super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs()));
	}

	public void avatarCommand(TextChannel channel, Message message, MessageAuthor messageAuthor, List<String> args) {
		if(!onCommand()) {
			return;
		}
		if (message.getMentionedUsers().size() == 0 && args.size() == 1) {
			channel.sendMessage("Please mention a user");
			return;
		}

		message.toString().toLowerCase();

		if (args.size() == 0) {
			channel.sendMessage(buildOutputMessage(messageAuthor.asUser().get()))
					.exceptionally(e -> {
						channel.sendMessage("User not found");
						return null;
					});
		} else if (args.size() == 1) {
			channel.sendMessage(buildOutputMessage(message.getMentionedUsers().get(0)))
					.exceptionally(e -> {
						channel.sendMessage("User not found");
						return null;
					});
		}
	}
	
	private EmbedBuilder buildOutputMessage(User user) {
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(user.getDiscriminatedName(), user.getAvatar().getUrl().toString() + imageSize, user.getAvatar())
				.setColor(Color.magenta)
				.setImage(user.getAvatar().getUrl() + imageSize)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();
		return embed;
	}
}