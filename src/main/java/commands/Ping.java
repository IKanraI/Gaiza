package commands;

import org.javacord.api.DiscordApi;

public class Ping 
{
	public Ping(DiscordApi api)
	{
			
		api.addMessageCreateListener(event ->{
			
			if (event.getMessageContent().equalsIgnoreCase("$ping"))
			{
				event.getChannel().sendMessage("Oh boy my creator finally put a separate function into his program");
				
			}
		});
	}
}
