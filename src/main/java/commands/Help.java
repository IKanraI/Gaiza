package commands;

import java.awt.Color;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;
import management.BotInfo;
import management.Keywords;

public class Help 
{
	private String helpCommand = "help";
	
	public Help(DiscordApi getApi)
	{
		DiscordApi helpApi = getApi;
		Keywords myKey = new Keywords();
		
		displayHelp(helpApi);
		
		System.out.println("Help.java loaded!");
	}
	
	public void displayHelp(DiscordApi getApi)
	{
		DiscordApi helpApi = getApi;
		DatabaseLL modifyData = InitDatabase.getCurrLL();
		Keywords getServerKey = new Keywords();
		
		helpApi.addMessageCreateListener(event ->
		{
			String userWhoCalled;
			String userCalledIconURL;
			String myKey = "";
			String getServerAddress = "";
			Icon userIcon;
			int i;
			
			for (i = 0; i < BotInfo.getServerCount(); ++i)
			{
				getServerAddress = event.getServer().get().getIdAsString();
				
				if (getServerAddress.equals(modifyData.getCurrServerID(modifyData, i)))
				{
					myKey = getServerKey.getKey(getServerAddress, i);
				}
			}
			
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
