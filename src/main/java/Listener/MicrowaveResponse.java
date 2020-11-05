package Listener;

import org.javacord.api.DiscordApi;

public class MicrowaveResponse 
{
	private String triggerPhrase = "microwave";
	private String responseLink = "https://www.youtube.com/watch?v=js71WSAos5M&t=25s";
	
	public MicrowaveResponse(DiscordApi getApi)
	{
		DiscordApi microApi = getApi;
		
		listenMicrowave(microApi);
		
		System.out.println("MicrowaveResponse.java loaded!");
	}
	
	public void listenMicrowave(DiscordApi getApi)
	{
		DiscordApi microListApi = getApi;
		
		microListApi.addMessageCreateListener(event ->
		{
			String messageToSplit;
			String[] splitMessage;
			
			if (event.getMessageAuthor().isUser())
			{
				try
				{
					messageToSplit = event.getMessageContent();
					messageToSplit = messageToSplit.toLowerCase();
					splitMessage = messageToSplit.split(" ");
					
					for (int i = 0; i < splitMessage.length; ++i)
					{
						if (splitMessage[i].equals(triggerPhrase))
						{
							event.getChannel().sendMessage(responseLink);
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
