package AdminCommands;

import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;

import Management.ServerPrefix;

public class BanUser 
{
	private String command = "ban";
	@Getter
	public static String help = "Bans user with or without a reason";
	
	public BanUser(DiscordApi getApi)
	{
		DiscordApi banApi = getApi;
		
		banMentionedUser(banApi);
		
		System.out.println("BanUser.java loaded!");
	}
	
	public void banMentionedUser(DiscordApi getApi)
	{
		DiscordApi banApi = getApi;
		final int MAXMESSAGESIZE = 2;
		
		banApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverAddress = "";
			String splitMessage[] = null;
			String getWholeMessage = "";
			String optionalMessage = "";
			User getUserBanned;
			int i;
			
			try
			{
				if (event.getMessageAuthor().isServerAdmin())
				{
					//Splits the message into parts to be used for later and server comparison
					//Can only be called by admins of the server
					serverAddress = event.getServer().get().getIdAsString();
					myKey = ServerPrefix.getKey(serverAddress);
					
					getWholeMessage = event.getMessageContent();
					splitMessage = getWholeMessage.split(" ");
					
					
					if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length == MAXMESSAGESIZE)
					{
						//Enters here if the command entered has only the command and a user to ban
						if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessage().getMentionedUsers().size() > 0)
						{
							getUserBanned = event.getMessage().getMentionedUsers().get(0);
							
							event.getChannel().sendMessage("User " + getUserBanned.getName() + " was banned with no reason provided");
							
							event.getServer().get().banUser(getUserBanned);
						}
						else
						{
							event.getChannel().sendMessage("Unable to ban user");
						}
					}
					else if(splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length > MAXMESSAGESIZE)
					{
						//Enters here if the command has an optional message attached to it and calls the appropriate ban method to store the message
						for (i = 2; i < splitMessage.length; ++i)
						{
							optionalMessage = optionalMessage.concat(splitMessage[i] + " ");
							
						}
						if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessage().getMentionedUsers().size() > 0)
						{
							getUserBanned = event.getMessage().getMentionedUsers().get(0);
							
							event.getChannel().sendMessage("User " + getUserBanned.getName() + " was kick with reason: " + optionalMessage);
							
							event.getServer().get().banUser(getUserBanned, 0, optionalMessage);
						}
						else
						{
							event.getChannel().sendMessage("Unable to ban user");
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
