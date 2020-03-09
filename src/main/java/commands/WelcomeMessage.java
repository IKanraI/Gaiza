package commands;

import org.javacord.api.DiscordApi;

import management.Keywords;

public class WelcomeMessage 
{
	private boolean isWelcomeActive;
	
	public WelcomeMessage(DiscordApi getApi)
	{
		DiscordApi welcomeApi = getApi;
		Keywords myKey = null;
		
		welcomeCheck(welcomeApi, myKey.getKey());
		
		System.out.println("WelcomeMessage.java loaded!");
	}
	
	public void welcomeCheck(DiscordApi getApi, char getKeyword)
	{
		DiscordApi welcomeApi = getApi;
		char myKeyword = getKeyword;
		
		if (isWelcomeActive)
		{
			welcomeApi.addServerMemberJoinListener(event ->
			{
				event.getServer().getServerMemberJoinListeners().get(0).onServerMemberJoin(event);
			});
		}
	}
	
	public void setWelcome(boolean welcomeActive)
	{
		isWelcomeActive = welcomeActive;
	}
	
	public boolean getWelcome()
	{
		return isWelcomeActive;
	}
}
