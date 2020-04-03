package commandsAdmin;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;

import management.Keywords;

public class KickUser 
{
	private String command = "kick";
	
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
			String getMentionedUser = "";				
			
			try
			{
				if (event.getMessageAuthor().isServerAdmin())
				{
					serverAddress = event.getServer().get().getIdAsString();
					myKey = Keywords.getKey(serverAddress);
					
					getWholeMessage = event.getMessageContent();
					splitMessage = getWholeMessage.split(" ");
					
					
					if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length == MAXMESSAGESIZE)
					{
						getMentionedUser = splitMessage[1];
						
						if (event.getMessageAuthor().canKickUsersFromServer())
						{
							event.getChannel().sendMessage("Termination in \n3........");
							Thread.sleep(1000);
							event.getChannel().sendMessage("2.....");
							Thread.sleep(1000);
							event.getChannel().sendMessage("1..");
							Thread.sleep(1000);
							event.getChannel().sendMessage("Catch you on the flip side, " + getMentionedUser + ".");
							Thread.sleep(1000);
							event.getServer().get().kickUser(event.getMessage().getMentionedUsers().get(0));
							
						}
						
						
					}
					else if(splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length > MAXMESSAGESIZE)
					{
						
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
