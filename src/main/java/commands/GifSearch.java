package commands;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import management.BotInfo;
import management.Keywords;

public class GifSearch 
{
	private String command = "gif";
	
	public GifSearch(DiscordApi getApi)
	{
		DiscordApi gifApi = getApi;
		
		gifSearch(gifApi);
	}
	
	public EmbedBuilder buildEmbed(String url, String author, String aURL, Icon aIcon)
	{
		String messageAuthorName = author;
		String messageAuthorURL = aURL;
		String gifToSend = url;
		Icon messageAuthorIcon = aIcon;
		
		EmbedBuilder message = new EmbedBuilder()
				.setTitle("A gif for your viewing")
				.setAuthor(messageAuthorName, messageAuthorURL, messageAuthorIcon)
				.setColor(Color.magenta)
				
				.setImage(gifToSend)
				
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();
		
		return message;
		
	}
	
	public void gifSearch(DiscordApi getApi)
	{
		DiscordApi gifApi = getApi;
		
		gifApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverCalled = "";
			String fullMessage = "";
			String[] splitMessage = null;
			String searchTerm = "";
			
			String searchURL = "";
			String getURL = "";
			String authorName = "";
			String authorURL = "";
			Icon authorIcon;
			int i;
			
			serverCalled = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverCalled);
			
			fullMessage = event.getMessageContent();
			splitMessage = fullMessage.split(" ");			
			
			if (event.getMessageAuthor().isUser())
			{
				if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length > 1)
				{
					searchURL = "https://tenor.com/search/";
					for (i = 1; i < splitMessage.length; ++i)
					{
						searchTerm = searchTerm.concat(splitMessage[i] + " ");
					}
					
					searchTerm.trim();
					
					getURL = searchGif(searchTerm, searchURL);
					
					if (!getURL.equalsIgnoreCase("noneFoundHere"))
					{	
						try 
						{
							authorName = event.getMessageAuthor().getName();
							authorIcon = event.getMessageAuthor().getAvatar();
							authorURL = event.getMessageAuthor().getAvatar().getUrl().toString();
							event.getChannel().sendMessage(buildEmbed(getURL, authorName, authorURL, authorIcon));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
				else if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length == 1)
				{
					event.getChannel().sendMessage("Please input a term to find a gif");
				}
			}
		});
	}
	
	public String searchGif(String term, String url)
	{
		String searchTerm = term;
		String searchURL = url;
		String userAgentSelect = "";
		String gifToSend = "";
		
		Document searchReference;
		Elements getDiv;
		Elements getImg;
		
		Random randNum = new Random();
		final int MAXRANDOM = 49;
		int randNumPick = 0;
		userAgentSelect = "Chrome/74.0.3729.157";
		
		searchURL = searchURL.concat(searchTerm);

		try 
		{
			searchReference = Jsoup.connect(searchURL)
					.followRedirects(true)
					.ignoreHttpErrors(true)
					.userAgent(userAgentSelect)
					.get();
			
			randNumPick = randNum.nextInt(MAXRANDOM);
			
			getDiv = searchReference.select("div.Gif");
			getImg = getDiv.get((int) (randNumPick)).select("img");
			gifToSend = getImg.attr("abs:src");
			
			if (!gifToSend.equals(""))
			{
				return gifToSend;
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return "noneFoundHere";
	}
}
