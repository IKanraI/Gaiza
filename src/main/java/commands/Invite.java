package commands;

import org.javacord.api.DiscordApi;

import management.Keywords;

public class Invite 
{
	private String botInvite;
	private String invMsg = "invite";
	
	public Invite(DiscordApi getApi)
	{
		DiscordApi inviteApi = getApi;
		Keywords holdKey = new Keywords();
		
		setInvite(inviteApi.createBotInvite());
		sendInvite(inviteApi, holdKey.getKey());
		
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
	
	public void sendInvite(DiscordApi sendInviteApi, char commandKey)
	{
		DiscordApi sendInv = sendInviteApi;
		char myKey = commandKey;
		
		sendInv.addMessageCreateListener(event ->
		{
			if (event.getMessageContent().equalsIgnoreCase(myKey + invMsg))
			{
				event.getChannel().sendMessage("Here's your invite! " + sendInv.createBotInvite());
			}
		});
	}
	
	

}
