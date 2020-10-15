package commands;

import org.apache.logging.log4j.message.Message;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import management.Keywords;

public class Ping implements CommandExecutor
{
	private String pingMsg = "ping";
	private String serverPrefix;
	
	public Ping() {
		Keywords.
	}
	
	
	@Command(aliases = {"!ping"}, async = true, description = "Pings Pong")
	public void onCommand(DiscordApi api, TextChannel channel, User author, Message message, String[] args) {
			channel.sendMessage(author.getName() + " rules in better code! ... er I mean... Pong" + args.length).join();
		
	}
	
	public void listenPing(DiscordApi pingApi)
	{
		DiscordApi sendPing = pingApi;
		
		sendPing.addMessageCreateListener(event ->
		{
			String getServerAddress = "";
			String serverPrefix = "";			
			
			getServerAddress = event.getServer().get().getIdAsString();
			serverPrefix = Keywords.getKey(getServerAddress);
				
			if (event.getMessageContent().equalsIgnoreCase(serverPrefix + pingMsg) && event.getMessageAuthor().isUser())
			{
				event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + "> rules! ... well pong i guess");
				
			}
		});
	}
	
	
}
