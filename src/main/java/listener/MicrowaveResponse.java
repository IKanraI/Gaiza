package listener;

import command.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;

public class MicrowaveResponse extends Command {
	
	public MicrowaveResponse(DiscordApi api) {
		super(api);
		
		api.addMessageCreateListener(event ->
				listenMicrowave(super.getChannel(), super.getServer(), super.getMessage()));

	}
	
	public void listenMicrowave(TextChannel channel, Server server, Message message) {
		if (message.getContent().equalsIgnoreCase("microwave")) {
			channel.sendMessage("https://www.youtube.com/watch?v=js71WSAos5M&t=25s");
		}
	}
}