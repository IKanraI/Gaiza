package commands;

import java.awt.Color;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;
import management.BotInfo;
import management.Keywords;

public class Avatar 
{
	private String avaCall = "avatar";
	private String imageSize = "?size=256";
	
	public Avatar(DiscordApi getApi)
	{
		DiscordApi avaApi = getApi;
		Keywords holdKey = new Keywords();
		
		listenAvatar(avaApi);
		
		System.out.println("Avatar.java loaded!");		
	}
	
	public void listenAvatar(DiscordApi getApi)
	{
		DiscordApi avaApi = getApi;
		DatabaseLL modifyData = InitDatabase.getCurrLL();
		Keywords getServerKey = new Keywords();
		
		avaApi.addMessageCreateListener(event ->
		{
			String messageString = "";
			String checkMessage = "";
			String concatMessage  = "";
			String[] splitMessage = null;
			String getServerAddress = "";
			String myKey = "";
						
			int i;
			
			for (i = 0; i < BotInfo.getServerCount(); ++i)
			{
				getServerAddress = event.getServer().get().getIdAsString();
				
				if (getServerAddress.equals(modifyData.getCurrServerID(modifyData, i)))
				{
					myKey = getServerKey.getKey(getServerAddress, i);
				}
			}
			
			if (event.getMessageAuthor().isUser())
			{
				//Checks if user has entered command first then parses the command
				messageString = event.getMessageContent();
				
				splitMessage = messageString.split(" ");
				concatMessage = myKey + avaCall;
				checkMessage = splitMessage[0];
				
				checkMessage.trim();
				concatMessage.trim();
				
				if (checkMessage.equals(concatMessage) && splitMessage.length == 1)
				{
					//Handles the command if there are no arguments
					String imageStr = "";
					String displayName = "";
					String userImageUrl = "";
					Icon userIcon;
					
					try
					{
						//Makes sure that if this statement is executed and a bad value comes back that the program does not break entirely
						imageStr = event.getMessageAuthor().getAvatar().getUrl().toString() + imageSize;
						displayName = event.getMessageAuthor().getDisplayName();
						userIcon = event.getMessageAuthor().getAvatar();	
						userImageUrl = event.getMessageAuthor().getAvatar().getUrl().toString();
						
						//Embed the image with these properties
						EmbedBuilder embed = new EmbedBuilder()
								.setAuthor(displayName, userImageUrl, userIcon)
								.setColor(Color.magenta)
								.setImage(imageStr)
								.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
								.setTimestampToNow();
				
						event.getChannel().sendMessage(embed);
					}
					catch (Exception e)
					{
						//Not sure if it can hit this but hey I'll find out if I do
						event.getChannel().sendMessage("Something went wrong");
						
						e.printStackTrace();
					}
					
				}
				else if (checkMessage.equals(concatMessage) && splitMessage.length == 2)
				{
					//Handles the command if there is one argument
					String imageStr = "";
					String displayName = "";
					String userImageUrl = "";
					Icon userIcon;
					
					try
					{
						//Makes sure that if this statement is executed and a bad value comes back that the program does not break entirely
						imageStr = event.getMessage().getMentionedUsers().get(0).getAvatar().getUrl().toString() + imageSize;
						displayName = event.getMessage().getMentionedUsers().get(0).getName();
						userIcon = event.getMessage().getMentionedUsers().get(0).getAvatar();
						userImageUrl = event.getMessageAuthor().getAvatar().getUrl().toString();
						
						//Embed the image with these properties
						EmbedBuilder embed = new EmbedBuilder()
								.setAuthor(displayName, userImageUrl, userIcon)
								.setColor(Color.magenta)
								.setImage(imageStr)
								.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
								.setTimestampToNow();
								
						//Sends the embedded message back to the channel
						event.getChannel().sendMessage(embed);
						
					}
					catch (Exception e)
					{
						//Catches exceptions that include user not being a mentioned user or not entering a user in the server
						event.getChannel().sendMessage("User mentioned either doesn't exist in the server or is not a user");
						
						e.printStackTrace();
					}
					
				}
				else if (checkMessage.equals(concatMessage) && splitMessage.length > 2)
				{
					//Returns error if there are multiple arguments entered
					event.getMessage().addReaction("‼");
					event.getChannel().sendMessage("Please either invoke just the command: (" + myKey + avaCall + ") or the command with one user: (" + myKey + avaCall + " [username])");	
				}
				
			}
			
		});
	}
}
