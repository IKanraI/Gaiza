package commands;

import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import management.Keywords;

public class Avatar 
{
	private String avaCall = "avatar";
	
	public Avatar(DiscordApi getApi)
	{
		DiscordApi avaApi = getApi;
		Keywords holdKey = new Keywords();
		
		listenAvatar(avaApi, holdKey.getKey());
		
		System.out.println("Avatar.java loaded!");		
	}
	
	public void listenAvatar(DiscordApi getApi, char commandKey)
	{
		DiscordApi avaApi = getApi;
		char myKey = commandKey;
		
		avaApi.addMessageCreateListener(event ->
		{
			
			String messageString = "";
			String[] splitMessage = null;
			String checkMessage = "";
			String concatMessage  = "";
			
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
					String imageStr = event.getMessageAuthor().getAvatar().getUrl().toString();

					event.getChannel().sendMessage(imageStr);
																			
					
					/*EmbedBuilder avaEmbed = new EmbedBuilder()
							.setAuthor(event.getMessageAuthor().getDisplayName()
							.setImage(imageStr)
								);
							
					event.getChannel().sendMessage(avaEmbed);*/
				}
				else if (checkMessage.equals(concatMessage) && splitMessage.length == 2)
				{
					//Handles the command if there is one argument
					String imageStr = "";									
					
					try
					{
						imageStr = event.getMessage().getMentionedUsers().get(0).getAvatar().getUrl().toString();
						
						event.getChannel().sendMessage(imageStr);
					}
					catch (Exception e)
					{
						event.getChannel().sendMessage("User mentioned either doesn't exist in the server or is not a user");
					}	
				}
				else if (checkMessage.equals(concatMessage) && splitMessage.length > 2)
				{
					//Returns error if this occurs
					event.getChannel().sendMessage("Please either invoke just the command or the command with one user: " + myKey + avaCall + " [username]");
				}
			}

		});
	}
}
