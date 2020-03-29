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
			final int MAXLENGTH = 3;
			final int PROTECTARRAYLENGTH = 2;
			String getWholeMessage = "";
			String splitMessage = "";
			String messageToGet = "uwu";
			ArrayList<Character> charMessageArray = null;
			int i = 0;
			
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

			try
			{
				
				if (messageToGet.equalsIgnoreCase(getWholeMessage))
				{
					EmbedBuilder embed = new EmbedBuilder()
							.setTitle("Stop! you've violated the law for the last time")
							.setImage(policeEnforcement)
							.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
							.setTimestampToNow();
					
					event.getChannel().sendMessage(embed);
				}
				else
				{
					for (i = 0; i < splitMessage.length() - PROTECTARRAYLENGTH; ++i)
					{
						if (charMessageArray.get(i).equals('u') && charMessageArray.get(i + 1).equals('w') && charMessageArray.get(i + 2).equals('u'))
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
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println(splitMessage.length() + " Length of Message \n" + getWholeMessage + " Whole message");
			}
		});
	}
}
