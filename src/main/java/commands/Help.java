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
		
		displayHelp(helpApi);
		
		System.out.println("Help.java loaded!");
	}
	
	public void displayHelp(DiscordApi getApi)
	{
		DiscordApi helpApi = getApi;
		
		helpApi.addMessageCreateListener(event ->
		{
			String userWhoCalled;
			String userCalledIconURL;
			String myKey = "";
			String getServerAddress = "";
			Icon userIcon;

			getServerAddress = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(getServerAddress);		
			
			try
			{			
				if (event.getMessageContent().equalsIgnoreCase(myKey + helpCommand) && event.getMessageAuthor().isUser())
				{
					userWhoCalled = event.getMessageAuthor().getDisplayName();
					userCalledIconURL = event.getMessageAuthor().getAvatar().getUrl().toString();
					userIcon = event.getMessageAuthor().getAvatar();
					
					EmbedBuilder embed = new EmbedBuilder()
							.setAuthor(userWhoCalled, userCalledIconURL, userIcon)
							.setColor(Color.magenta)
							
							.setTitle("Bot Help")
							.setDescription("Commands are currently all prefixed by: " + myKey)
							
							.addInlineField(myKey + "Avatar", "Use either " + myKey + "avatar or " + myKey +  "avatar @[user]")
							.addInlineField(myKey + "Invite", "Can be used to get an invite for the bot")
							.addInlineField(myKey + "Ping", "The most basic of commands")
							
							.addInlineField(myKey + "ahelp", "Admin help panel")
							.addInlineField(myKey + "define", "Search Urban Dictionary")
							.addInlineField(myKey + "gif", "Returns a gif [command]gif [search]")
							
							.addInlineField(myKey + "fines", "Returns the amount of money you have in fines " + myKey + "fines")
							.addInlineField(myKey + "leaderboard", "Returns the server leaderboard for uwu fines")
							
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
