package commands;

import org.javacord.api.DiscordApi;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;
import management.BotInfo;
import management.Keywords;

public class Ping
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
		Keywords getServerKey = new Keywords();
		DatabaseLL modifyData = InitDatabase.getCurrLL();
		
		sendPing.addMessageCreateListener(event ->
		{
			String getServerAddress = "";
			String serverPrefix = "";
			int i;
			
			for (i = 0; i < BotInfo.getServerCount(); ++i)
			{
				getServerAddress = event.getServer().get().getIdAsString();
				
				if (getServerAddress.equals(modifyData.getCurrServerID(modifyData, i)))
				{
					serverPrefix = getServerKey.getKey(getServerAddress, i);
				}
			}
			
			if (event.getMessageContent().equalsIgnoreCase(serverPrefix + pingMsg) && event.getMessageAuthor().isUser())
			{
				event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + "> rules! ... well pong i guess");
				
			}
		});
	}
}
