package AdminCommands;

import Database.InitDatabase;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;

public class KickUser 
{
	private String command = "kick";
	@Getter
	public static String help = "Kicks user with or without a reason";
	
	public KickUser(DiscordApi getApi)
	{
		DiscordApi kickApi = getApi;
		
		kickMentionedUser(kickApi);
		
		System.out.println("KickUser.java loaded!");
	}
	
	public void kickMentionedUser(DiscordApi getApi)
	{
		DiscordApi kickApi = getApi;
		final int MAXMESSAGESIZE = 2;
		
		kickApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverAddress = "";
			String splitMessage[] = null;
			String getWholeMessage = "";
			String optionalMessage = "";
			User getUserKicked;
			int i;
			
			try
			{
				if (event.getMessageAuthor().isServerAdmin())
				{
					//Splits the message into parts to be used for later and server comparison
					serverAddress = event.getServer().get().getIdAsString();
					myKey = InitDatabase.getData().get(serverAddress).getPrefix();
					
					getWholeMessage = event.getMessageContent();
					splitMessage = getWholeMessage.split(" ");
					
					
					if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length == MAXMESSAGESIZE)
					{	
						//Enters here if the command entered has only the command and a user to kick
						if (event.getMessageAuthor().canKickUsersFromServer() && event.getMessage().getMentionedUsers().size() > 0)
						{
							getUserKicked = event.getMessage().getMentionedUsers().get(0);
							
							event.getChannel().sendMessage("User " + getUserKicked.getName() + " was kicked with no reason provided");
							
							event.getServer().get().kickUser(getUserKicked);
						}
						else
						{
							event.getChannel().sendMessage("Unable to kick user");
						}
					}
					else if(splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length > MAXMESSAGESIZE)
					{
						//Enters here if the command has an optional message attached to it and calls the appropriate kick method to store the message
						for (i = 2; i < splitMessage.length; ++i)
						{
							optionalMessage = optionalMessage.concat(splitMessage[i] + " ");
							
						}
						
						if (event.getMessageAuthor().canKickUsersFromServer() && event.getMessage().getMentionedUsers().size() > 0)
						{
							getUserKicked = event.getMessage().getMentionedUsers().get(0);
							
							event.getChannel().sendMessage("User " + getUserKicked.getName() + " was kick with reason: " + optionalMessage);
							
							event.getServer().get().kickUser(getUserKicked, optionalMessage);
						}
						else
						{
							event.getChannel().sendMessage("Unable to kick user");
						}
					}
					else if (splitMessage[0].equalsIgnoreCase(myKey + command) && event.getMessage().getMentionedUsers().size() == 0)
					{
						//Should only go here if the user does not include anything and just writes the command
						event.getChannel().sendMessage("The command needs a user with it. Write it as: " + myKey + command + " [user] <reason>");
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
