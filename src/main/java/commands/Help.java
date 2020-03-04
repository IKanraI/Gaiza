package commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import management.Keywords;

public class Help 
{
	private String helpCommand = "help";
	
	public Help(DiscordApi getApi)
	{
		DiscordApi helpApi = getApi;
		Keywords myKey = new Keywords();
		
		displayHelp(helpApi, myKey.getKey());
		
		System.out.println("Help.java loaded!");
		
		
	}
	
	public void displayHelp(DiscordApi getApi, char getKey)
	{
		DiscordApi helpApi = getApi;
		char myKey = getKey;
		
		helpApi.addMessageCreateListener(event ->
		{
			if (event.getMessageContent().equalsIgnoreCase(myKey + helpCommand) && event.getMessageAuthor().isUser())
			{
										
			}
		});
	}
}
