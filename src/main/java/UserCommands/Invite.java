package UserCommands;

import lombok.Getter;
import org.javacord.api.DiscordApi;

import Management.Keywords;

public class Invite 
{
	@Getter
	public static String help = "Returns an invite link for the bot";
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
		
		sendInv.addMessageCreateListener(event ->
		{
			String myKey = "";
			String getServerAddress = "";
			
			getServerAddress = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(getServerAddress);
				
			if (event.getMessageContent().equalsIgnoreCase(myKey + invMsg))
			{
				event.getChannel().sendMessage("Here's your invite! " + sendInv.createBotInvite());
			}
		});
	}
	
	

}
