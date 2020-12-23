package Listener;

import Command.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public class MicrowaveResponse extends Command {
	
	public MicrowaveResponse(DiscordApi api) {
		super(api);
		
		api.addMessageCreateListener(event ->
				listenMicrowave(super.getChannel(), super.getMessage()));

	}
	
	public void listenMicrowave(TextChannel channel, Message message) {
		if (message.getContent().equalsIgnoreCase("microwave")) {
			channel.sendMessage("https://www.youtube.com/watch?v=js71WSAos5M&t=25s");
		}
	}
}
