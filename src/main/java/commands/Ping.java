package commands;

import org.javacord.api.DiscordApi;

import management.Keywords;

public class Ping
{
	private String pingMsg = "ping";
	public Ping(DiscordApi api)
	{
		DiscordApi holdPingApi = api;
		Keywords holdKey = new Keywords();
		
		listenPing(api, holdKey.getKey());
			
	}
	
	public void listenPing(DiscordApi pingApi, char keySymbol)
	{
		char myKey = keySymbol;
		
		pingApi.addMessageCreateListener(event ->{
			
			if (event.getMessageContent().equalsIgnoreCase(myKey + pingMsg) && event.getMessageAuthor().isUser())
			{
				event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + "> rules! ... well pong i guess");
				
			}
		});
	}
}
