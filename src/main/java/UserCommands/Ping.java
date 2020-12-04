package UserCommands;

import lombok.Getter;
import org.javacord.api.DiscordApi;
import Command.*;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;

public class Ping extends Command {
	@Getter
	public static String help = "Typical test command. Returns !pong normally.";
	
	public Ping(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(e ->
			pingCommand(super.getChannel(), super.getMessageAuthor()));
	}

	private void pingCommand(TextChannel channel, MessageAuthor messageAuthor) {
		if(!onCommand()) {
			return;
		}
		channel.sendMessage("<@" + messageAuthor.getIdAsString() + "> rules! ... well pong i guess");
	}
}