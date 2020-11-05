package UserCommands;

import org.javacord.api.DiscordApi;
import Command.*;

import Management.Keywords;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

public class Ping extends Command
{
	private String pingMsg = "ping";
	
	public Ping(DiscordApi api) {
		super(api);
		super.setCommand("ping");
		api.addMessageCreateListener(event -> {
			pingCommand(super.getApi(), super.getChannel(), super.getMessage(), super.getMessageAuthor());
		});
	}

	public void pingCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor messageAuthor) {
		if (!onCommand(api, channel, message, messageAuthor)) {
			return;
		}

		channel.sendMessage("<@" + messageAuthor.getIdAsString() + "> rules! ... well pong i guess");
	}
}
