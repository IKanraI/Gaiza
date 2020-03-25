package commands;

import org.javacord.api.DiscordApi;

import jsonDatabase.DatabaseLL;
import jsonDatabase.InitDatabase;
import management.BotInfo;
import management.Keywords;

public class Invite 
{
	private String botInvite;
	private String invMsg = "invite";
	
	public Invite(DiscordApi getApi)
	{
		DiscordApi inviteApi = getApi;
		
		setInvite(inviteApi.createBotInvite());
		sendInvite(inviteApi);
		
		System.out.println("Invite.java loaded!");
		
	}
	
	public void setInvite(String inviteLink)
	{
		botInvite = inviteLink;
	}
	
	public String getInvite()
	{
		return botInvite;
	}
	
	public void sendInvite(DiscordApi sendInviteApi)
	{
		DiscordApi sendInv = sendInviteApi;
		DatabaseLL modifyData = InitDatabase.getCurrLL();
		Keywords getServerKey = new Keywords();
		
		sendInv.addMessageCreateListener(event ->
		{
			String myKey = "";
			String getServerAddress = "";
			int i;
			
			for (i = 0; i < BotInfo.getServerCount(); ++i)
			{
				getServerAddress = event.getServer().get().getIdAsString();
				
				if (getServerAddress.equals(modifyData.getCurrServerID(modifyData, i)))
				{
					myKey = getServerKey.getKey(getServerAddress, i);
				}
			}
				
			if (event.getMessageContent().equalsIgnoreCase(myKey + invMsg))
			{
				event.getChannel().sendMessage("Here's your invite! " + sendInv.createBotInvite());
			}
		});
	}
	
	

}
