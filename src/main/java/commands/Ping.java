package commands;

import org.javacord.api.DiscordApi;

import de.btobastian.sdcf4j.CommandExecutor;
import management.Keywords;

public class Ping implements CommandExecutor
{
	private String pingMsg = "ping";
	
	public Ping(DiscordApi api)
	{
		DiscordApi holdPingApi = api;
		
		listenPing(api);
		
		System.out.println("Ping.java loaded!");
			
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
