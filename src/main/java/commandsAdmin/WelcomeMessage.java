package commandsAdmin;

import org.javacord.api.DiscordApi;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;
import management.BotInfo;
import management.Keywords;

public class WelcomeMessage 
{
	private String commandE = "enable";
	private String commandM = "welcome";
	private String commandC = "channel";
	
	public WelcomeMessage(DiscordApi initApi)
	{
		DiscordApi wApi = initApi;
		
		listenForNewMember(wApi);
		enableWelcomeMessage(wApi);
		updateWelcomeMessage(wApi);
		updateWelcomeChannel(wApi);
		
		System.out.println("WelcomeMessage.java loaded!");
		
	}

	public void enableWelcomeMessage(DiscordApi getApi)
	{
		DiscordApi wEApi = getApi;
		
		wEApi.addMessageCreateListener(event ->
		{
			String getMessage = "";
			String splitMessage[] = null;
			String serverID = "";
			String myKey = "";
			String setToValue = "";
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			getMessage = event.getMessageContent();
			splitMessage = getMessage.split(" ");
			
			
			if (event.getMessageAuthor().isServerAdmin())
			{
				if (splitMessage[0].equalsIgnoreCase(myKey + commandE) && splitMessage.length == 2)
				{
					if (isParamSet(serverID))
					{
						setToValue = splitMessage[1].trim();
						
						if (setToValue.equalsIgnoreCase("true") || setToValue.equalsIgnoreCase("t") || setToValue.equalsIgnoreCase("1"))
						{
							setWelcomeEnabled("true", serverID);
							event.getChannel().sendMessage("Welcome is now enabled");
						}
						else if (setToValue.equalsIgnoreCase("false") || setToValue.equalsIgnoreCase("f") || setToValue.equalsIgnoreCase("0"))
						{
							setWelcomeEnabled("false", serverID);
							event.getChannel().sendMessage("Welcome is now disabled");
						}
						else
						{
							event.getChannel().sendMessage("To enable the welcome message please use [true | t | 1] and to disable the welcome message please use [false | f | 0]");
						}
					}
					else
					{
						event.getChannel().sendMessage("Please set a welcome message and a welcome channel before enabling the welcome message module.");
					}
				}
				else if (splitMessage[0].equalsIgnoreCase(myKey + commandE) && splitMessage.length == 1)
				{
					event.getChannel().sendMessage("Please include either true or false to enable/disable the welcome message");
				}
			}
		});
	}
	
	public boolean isEnabled(String getServerID)
	{
		DatabaseLL getLLData = InitDatabase.getCurrLL();
		String serverID = getServerID;
		String isEnabledCheck = "";
		int i;

		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (serverID.equals(getLLData.getCurrServerID(getLLData, i)))
			{
				isEnabledCheck = getLLData.getServerWelcomeEnabled(getLLData, i);	
			}
		}
		
		if (isEnabledCheck.equalsIgnoreCase("true"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isParamSet(String getServerID)
	{
		DatabaseLL checkSettings = InitDatabase.getCurrLL();
		String serverID = getServerID;
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (serverID.equals(checkSettings.getCurrServerID(checkSettings, i)))
			{
				if (checkSettings.getServerWelcomeMessage(checkSettings, i).equals("") && checkSettings.getServerWelcomeChannel(checkSettings, i).equals(""))
				{
					return false;
				}
				else
				{
					return true;
				}	
			}
		}
		
		return true;
	}
	
	public void listenForNewMember(DiscordApi getApi)
	{
		DiscordApi memJoinApi = getApi;
		
		memJoinApi.addServerMemberJoinListener(event ->
		{
			String serverID = "";
			String welcomeMessage = "";
			String welcomeChannel = "";
			String finalMessageToSend = "";
			
			serverID = event.getServer().getIdAsString();

			if (isEnabled(serverID))
			{
				welcomeMessage = getWelcomeMessage(serverID);
				welcomeChannel = getWelcomeChannel(serverID);

				finalMessageToSend = welcomeMessage.replaceAll("<<mention>>", event.getUser().getMentionTag());

				event.getServer().getChannelById(welcomeChannel).get().asServerTextChannel().get().sendMessage(finalMessageToSend);
			}
		});
	}	
	
	public String getWelcomeMessage(String getServer)
	{
		DatabaseLL welcomeDB = InitDatabase.getCurrLL();
		String serverID = getServer;
		String getWelcomeMessage = "";
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (serverID.equals(welcomeDB.getCurrServerID(welcomeDB, i)))
			{
				getWelcomeMessage = welcomeDB.getServerWelcomeMessage(welcomeDB, i);
			}
		}
		
		return getWelcomeMessage;
		
	}
	
	public String getWelcomeChannel(String getServer)
	{
		DatabaseLL welcomeDB = InitDatabase.getCurrLL();
		String serverID = getServer;
		String getWelcomeChannel = "";
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (serverID.equals(welcomeDB.getCurrServerID(welcomeDB, i)))
			{
				getWelcomeChannel = welcomeDB.getServerWelcomeChannel(welcomeDB, i);
			}
		}
		
		return getWelcomeChannel;
	}
	
	public void setWelcomeChannel(String wChannel, String serverID)
	{
		InitDatabase saveDB = new InitDatabase();
		DatabaseLL getLLData = InitDatabase.getCurrLL();
		
		String currServerID = serverID;
		String welcomeChannel = wChannel;
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (currServerID.equals(getLLData.getCurrServerID(getLLData, i)))
			{
				getLLData.setWelcomeChannel(getLLData, welcomeChannel, i);
			}
		}
		
		saveDB.saveDatabase();
	}
	
	public void setWelcomeEnabled(String wEnable, String serverID)
	{
		InitDatabase saveDB = new InitDatabase();
		DatabaseLL getLLData = InitDatabase.getCurrLL();
		
		String currServerID = serverID;
		String isEnabled = wEnable;
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (currServerID.equals(getLLData.getCurrServerID(getLLData, i)))
			{
				getLLData.setWelcomeEnabled(getLLData, isEnabled, i);
			}
		}
		
		saveDB.saveDatabase();
	}
	
	public void setWelcomeMessage(String message, String serverID)
	{
		InitDatabase saveDB = new InitDatabase();
		DatabaseLL getLLData = InitDatabase.getCurrLL();
		
		String currServerID = serverID;
		String welcomeMessage = message;
		int i;
		
		for (i = 0; i < BotInfo.getServerCount(); ++i)
		{
			if (currServerID.equals(getLLData.getCurrServerID(getLLData, i)))
			{
				getLLData.setWelcomeMessage(getLLData, welcomeMessage, i);
			}
		}
		
		saveDB.saveDatabase();
	}
	
	public void updateWelcomeChannel(DiscordApi getApi)
	{
		DiscordApi channelApi = getApi;
		
		channelApi.addMessageCreateListener(event ->
		{
			String getMessage = "";
			String splitMessage[] = null;
			String keyWord = "";
			String serverID = "";
			String channelID = "";
			
			
			if (event.getMessageAuthor().isServerAdmin())
			{
				serverID = event.getServer().get().getIdAsString();
				keyWord = Keywords.getKey(serverID);
				
				getMessage = event.getMessageContent();
				splitMessage = getMessage.split(" ");
				
				if (splitMessage[0].equalsIgnoreCase(keyWord + commandC) && splitMessage.length == 2)
				{
					try
					{
						channelID = event.getMessage().getMentionedChannels().get(0).getIdAsString();
						setWelcomeChannel(channelID, serverID);
						
						event.getChannel().sendMessage("Welcome message has been set");
					}
					catch (Exception e)
					{
						event.getChannel().sendMessage("Unable to get channel. Make sure to reference the channel with #[channel name]");
					}
				}
				else if (splitMessage[0].equalsIgnoreCase(keyWord + commandC) && splitMessage.length == 1)
				{
					event.getChannel().sendMessage("Please enter a channel to set the welcome message to be sent in.");
				}
			}
		});
	}
	
	public void updateWelcomeMessage(DiscordApi getApi)
	{
		DiscordApi wMApi = getApi;
		
		wMApi.addMessageCreateListener(event ->
		{
			String getMessage = "";
			String splitMessage[] = null;
			String serverID = "";
			String myKey = "";
			String messageToChange = "";
			int i;
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			getMessage = event.getMessageContent();
			splitMessage = getMessage.split(" ");
			
			if (event.getMessageAuthor().isServerAdmin())
			{
				if (splitMessage[0].equalsIgnoreCase(myKey + commandM) && splitMessage.length > 2)
				{
					for (i = 1; i < splitMessage.length; ++i)
					{
						messageToChange = messageToChange + splitMessage[i] + " ";
					}
					
					messageToChange = messageToChange.trim();
					
					setWelcomeMessage(messageToChange, serverID);
					event.getChannel().sendMessage("Welcome has been set to: " + messageToChange);
				}
				else if (splitMessage[0].equalsIgnoreCase(myKey + commandM) && splitMessage.length == 1)
				{
					event.getChannel().sendMessage("Please include a message to set as a welcome message");
				}
			}
		});
	}
}

