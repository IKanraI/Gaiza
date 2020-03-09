package listener;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import management.BotInfo;

public class UwuResponse 
{
	private String policeEnforcement = "https://b.catgirlsare.sexy/zWCg.png";
	public UwuResponse(DiscordApi getApi)
	{
		DiscordApi uwuApi = getApi;
		
		uwuListener(uwuApi);
		
		System.out.println("UwuResponse.java loaded!");
	}
	
	public void uwuListener(DiscordApi getApi)
	{
		DiscordApi uwuApi = getApi;
		
		uwuApi.addMessageCreateListener(event ->
		{
			String getWholeMessage = "";
			String[] splitMessage = null;
			int i = 0;
			
			try
			{
				if (event.getMessage().getAuthor().isUser())
				{
				
					getWholeMessage = event.getMessageContent();
					getWholeMessage = getWholeMessage.toLowerCase();
					splitMessage = getWholeMessage.split("");
				}
			}
			catch (Exception e)
			{
				System.out.println("Message author is not a user");
				e.printStackTrace();
			}

			try
			{
				for (i = 0; i < splitMessage.length - 3; ++i)
				{
					if (splitMessage[i].equals("u"))
					{
						if (splitMessage[i + 1].equals("w"))
						{
							if (splitMessage[i + 2].equals("u"))
							{
								EmbedBuilder embed = new EmbedBuilder()
										.setTitle("Stop, you've violated the law for the last time")
										.setImage(policeEnforcement)
										.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
										.setTimestampToNow();
								
								event.getChannel().sendMessage(embed);
							} //end third if
						} //end second if
					} //end first if
				} //end for
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
