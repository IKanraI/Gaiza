package UserCommands;
import java.awt.Color;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import Management.BotInfo;
import Management.Keywords;

public class Avatar {
	private final String command;
	private final String imageSize;
	
	public Avatar(DiscordApi userApi) {
		command = "avatar";
		imageSize = "?size=256";
		
		listenAvatar(userApi);
	}

	private void listenAvatar(DiscordApi userApi) {
		
		userApi.addMessageCreateListener(event -> {
			
			if (event.getMessageAuthor().isUser()) {
				
				String serverKey = Keywords.getKey(event.getServer().get().getIdAsString());
				String userMessage[] = event.getMessageContent().split(" ");
				
				if((serverKey + command).equalsIgnoreCase(userMessage[0]))
				{
					
					switch (userMessage.length) {
					case 1:
						MessageAuthor auth = event.getMessageAuthor();
						event.getChannel().sendMessage(
							buildOutputMessage(
								auth.getDisplayName(),
								auth.getAvatar().getUrl().toString(),
								auth.getAvatar(),
								auth.getAvatar().getUrl().toString()));
						break;
					case 2:
						User mUser = event.getMessage().getMentionedUsers().get(0);
						event.getChannel().sendMessage(
								buildOutputMessage(
									mUser.getName(),
									mUser.getAvatar().getUrl().toString(),
									mUser.getAvatar(),
									mUser.getAvatar().getUrl().toString()));
						break;
					default:
						event.getMessage().addReaction("‼");
						event.getChannel().sendMessage("Please either invoke just the command: (" + serverKey + command + ") or the command with one user: (" + serverKey + command + " [username])");
					}
				}
			}
		});
	}
	
	private EmbedBuilder buildOutputMessage(String displayName, String userImageUrl, Icon userIcon, String imageStr) {
		
		EmbedBuilder embed = new EmbedBuilder()
				.setAuthor(displayName, userImageUrl, userIcon)
				.setColor(Color.magenta)
				.setImage(imageStr + imageSize)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();
		
		return embed;
	}

}
