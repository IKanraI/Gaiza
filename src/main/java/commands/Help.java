package commands;

import java.awt.Color;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import management.BotInfo;
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
			try
			{			
				if (event.getMessageContent().equalsIgnoreCase(myKey + helpCommand) && event.getMessageAuthor().isUser())
				{
					String userWhoCalled;
					String userCalledIconURL;
					Icon userIcon;
					
					userWhoCalled = event.getMessageAuthor().getDisplayName();
					userCalledIconURL = event.getMessageAuthor().getAvatar().getUrl().toString();
					userIcon = event.getMessageAuthor().getAvatar();
					
					EmbedBuilder embed = new EmbedBuilder()
							.setAuthor(userWhoCalled, userCalledIconURL, userIcon)
							.setColor(Color.magenta)
							
							.setTitle("Bot Help")
							.setDescription("Commands are currently all prefixed by $")
							
							.addInlineField("Avatar", "Use either [prefix]avatar or [prefix]avatar @[user]")
							.addInlineField("Invite", "Can be used to get an invite for the bot")
							.addInlineField("Ping", "The most basic of commands")
							
							.setThumbnail(BotInfo.getBotImageStr())
							.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
							.setTimestampToNow();
					
					event.getChannel().sendMessage(embed);
							
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
