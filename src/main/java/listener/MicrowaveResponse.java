package listener;

import command.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MicrowaveResponse implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if (event.getMessage().getContent().equalsIgnoreCase("microwave")) {
			event.getChannel().sendMessage("https://www.youtube.com/watch?v=js71WSAos5M&t=25s");
		}
	}
}