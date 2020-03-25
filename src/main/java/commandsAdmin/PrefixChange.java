package commandsAdmin;

import org.javacord.api.DiscordApi;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;
import management.BotInfo;
import management.Keywords;

public class PrefixChange 
{
	private String prefixChangeCommand = "pChange";
	
	public PrefixChange(DiscordApi getApi)
	{
		DiscordApi preChangeApi = getApi;
		Keywords serverKey = new Keywords();	
		
		changePrefix(getApi);
		
		System.out.println("PrefixChange.java loaded!");
	}
	
	public void changePrefix(DiscordApi getApi)
	{
		DiscordApi prefixChangeApi = getApi;
		DatabaseLL modifyData = InitDatabase.getCurrLL();
		Keywords modifyKey = new Keywords();
		
		
		prefixChangeApi.addMessageCreateListener(event ->
		{
			final int MESSAGELENGTH = 2;
			String[] splitMessage = null;
			String getMessage = "";
			String newPrefix = "";
			String getServerAddress = "";
			String serverPrefix = "";
			int i;
			
			getMessage = event.getMessageContent().toLowerCase();
			splitMessage = getMessage.split(" ");
			
			try
			{
				for (i = 0; i < BotInfo.getServerCount(); ++i)
				{
					getServerAddress = event.getServer().get().getIdAsString();
					
					if (getServerAddress.equals(modifyData.getCurrServerID(modifyData, i)))
					{
						serverPrefix = modifyKey.getKey(getServerAddress, i);
					}
				}
				
				if (splitMessage[0].equalsIgnoreCase(serverPrefix + prefixChangeCommand) && splitMessage.length == MESSAGELENGTH)
				{
					if(event.getMessageAuthor().isServerAdmin() && event.getMessageAuthor().isUser())
					{
						newPrefix = splitMessage[1];
						
						for (i = 0; i < BotInfo.getServerCount(); ++i)
						{
							if (getServerAddress.equals(modifyData.getCurrServerID(modifyData, i)))
							{
								modifyKey.setKey(modifyData, newPrefix, i);
								event.getChannel().sendMessage("Success! The server's prefix was set to : " + newPrefix);
							}
						}
					}
					else
					{
						event.getChannel().sendMessage("User is not an admin of the server");
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
