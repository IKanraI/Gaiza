package listener;

import java.util.ArrayList;

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
			String splitMessage = null;
			ArrayList<Character> charMessageArray = null;
			int i = 0;
			
			try
			{
				if (event.getMessage().getAuthor().isUser())
				{
				
					getWholeMessage = event.getMessageContent();
					getWholeMessage = getWholeMessage.toLowerCase();
					splitMessage = getWholeMessage.replaceAll("\\s", "");
					
					charMessageArray = new ArrayList<Character>(splitMessage.length());
					
					for (i = 0; i < splitMessage.length(); ++i)
					{
						charMessageArray.add(splitMessage.charAt(i));
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("Message author is not a user");
				e.printStackTrace();
			}

			try
			{
				for (i = 0; i < splitMessage.length(); ++i)
				{
					if (charMessageArray.get(i).equals('u'))
					{
						if (charMessageArray.get(i + 1).equals('w'))
						{
							if (charMessageArray.get(i + 2).equals('u'))
							{
								EmbedBuilder embed = new EmbedBuilder()
										.setTitle("Stop! you've violated the law for the last time")
										.setImage(policeEnforcement)
										.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
										.setTimestampToNow();
								
								event.getChannel().sendMessage(embed);
								
								break;
							}
						}
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
