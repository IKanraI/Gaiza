package UserCommands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Management.BotInfo;
import Management.Keywords;

public class UrbanDictionary 
{
	@Getter
	public static String help = "Search urban dictionary";
	private String command = "define";
	
	public UrbanDictionary(DiscordApi getApi)
	{
		DiscordApi uDApi = getApi;
		
		uDSearch(uDApi);
		
		System.out.println("UrbanDictionary.java loaded!");
	}
	
	public EmbedBuilder buildOutput(String messageName, String messageURL, Icon messageIcon, String messageToSend, String keyTerm)
	{
		String messageAuthorName = messageName;
		String messageAuthorURL = messageURL;
		Icon messageAuthorIcon = messageIcon;
		
		String definition = messageToSend;
		String definitionTerm = keyTerm;
		String finalCount = "";
		
		final int MAXCHARCOUNT = 1024;
		int bufferCharAmount = 30;
		Integer charCount = 0;
		
		charCount = definition.length();
		charCount += definitionTerm.length();
		charCount += messageAuthorName.length() + messageAuthorURL.length() + BotInfo.getBotName().length();
		charCount += bufferCharAmount;
		
		if (charCount <= MAXCHARCOUNT)
		{
			EmbedBuilder output = new EmbedBuilder()
					.setTitle(definitionTerm)
					.setAuthor(messageAuthorName, messageAuthorURL, messageAuthorIcon)
					.setColor(Color.magenta)
					
					.addInlineField("Definition", definition)
					
					.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
					.setTimestampToNow();
			
			return output;
		}
		else
		{
			return null;
		}
	}
	
	public String searchAndReturnMessage(String searchString, String searchURL)
	{
		Elements getDefinitions;
		String elementText = "";
		Document docReference;
		
		try
		{
			searchURL = searchURL.concat(searchString);
			docReference = Jsoup.connect(searchURL)
					.followRedirects(true)
					.ignoreHttpErrors(true)
					.userAgent("Mozilla/5.0")
					.get();

			getDefinitions = docReference.select("div.meaning");
			elementText = getDefinitions.get(0).text();
			
			if (!elementText.equals(""))
			{
				return elementText;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return "noneFoundHere";
	}
	
	public void uDSearch(DiscordApi getApi)
	{
		DiscordApi uDApi = getApi;
		
		uDApi.addMessageCreateListener(event ->
		{
			String getMessage = "";
			String myKey = "";
			String splitMessage[] = null;
			String searchString = "";
			String getServerID = "";
			String searchURL = "";
			String finalMessage = "noneFoundHere";
			String getTermForOutput = "";
			
			String messageAuthorName;
			String messageAuthorURL;
			List<String> messageSplitForSize = new ArrayList<String>();
			Icon messageAuthorIcon;
			EmbedBuilder sendOutput;
			
			final int MAXDISCORDMESSAGE = 2000;
			int i, trackCharLimit = 0;
			
			if (event.getMessageAuthor().isUser())
			{
				getServerID = event.getMessage().getServer().get().getIdAsString();
				myKey = Keywords.getKey(getServerID);
				
				getMessage = event.getMessageContent();
				splitMessage = getMessage.split(" ");
				
				searchURL = "https://www.urbandictionary.com/define.php?term=";
				
				messageAuthorName = event.getMessageAuthor().getName();
				messageAuthorURL = event.getMessageAuthor().getAvatar().getUrl().toString();
				messageAuthorIcon = event.getMessageAuthor().getAvatar();
			
				if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length == 2)
				{
					searchString = splitMessage[splitMessage.length - 1];
					
					finalMessage = searchAndReturnMessage(searchString, searchURL);
					
					if (!finalMessage.equalsIgnoreCase("noneFoundHere"))
					{
						try
						{
							getTermForOutput = searchString.substring(0, 1).toUpperCase() + searchString.substring(1);
							sendOutput = buildOutput(messageAuthorName, messageAuthorURL, messageAuthorIcon, finalMessage, getTermForOutput);
							
							if (sendOutput != null)
							{
								event.getChannel().sendMessage(sendOutput);
							}
							else
							{
								while (trackCharLimit < finalMessage.length())
								{
									messageSplitForSize.add(finalMessage.substring(trackCharLimit, Math.min(trackCharLimit + MAXDISCORDMESSAGE,  finalMessage.length())));
									trackCharLimit += MAXDISCORDMESSAGE;
								}
								
								for (i = 0; i < messageSplitForSize.size(); ++i)
								{
									event.getChannel().sendMessage(i + 1 + "/" + messageSplitForSize.size() + " " + messageSplitForSize.get(i));
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (finalMessage.equalsIgnoreCase("noneFoundHere"))
					{
						//event.getMessage().addReaction("");
						event.getChannel().sendMessage("Could not find the term. Please try again");
					}
				}
				else if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length > 2)
				{
					for (i = 1; i < splitMessage.length; ++i)
					{
						if (i == splitMessage.length - 1)
						{
							searchString = searchString.concat(splitMessage[i]);
						}
						else
						{
							searchString = searchString.concat(splitMessage[i] + "+");
						}
					}
					
					finalMessage = searchAndReturnMessage(searchString, searchURL);
					
					if (!finalMessage.equalsIgnoreCase("noneFoundHere"))
					{
						try
						{
							getTermForOutput = searchString.substring(0, 1).toUpperCase() + searchString.substring(1);
							getTermForOutput = getTermForOutput.replace('+', ' ');
							sendOutput = buildOutput(messageAuthorName, messageAuthorURL, messageAuthorIcon, finalMessage, getTermForOutput);
							
							if (sendOutput != null)
							{
								event.getChannel().sendMessage(sendOutput);
							}
							else
							{
								while (trackCharLimit < finalMessage.length())
								{
									messageSplitForSize.add(finalMessage.substring(trackCharLimit, Math.min(trackCharLimit + MAXDISCORDMESSAGE,  finalMessage.length())));
									trackCharLimit += MAXDISCORDMESSAGE;
								}
								
								for (i = 0; i < messageSplitForSize.size(); ++i)
								{
									event.getChannel().sendMessage(i + 1 + "/" + messageSplitForSize.size() + " " + messageSplitForSize.get(i));
								}
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (finalMessage.equalsIgnoreCase("noneFoundHere"))
					{
						//event.getMessage().addReaction("");
						event.getChannel().sendMessage("Could not find the term. Please try again");
					}
				}
			}
		});
	}
}
