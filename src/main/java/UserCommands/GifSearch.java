package UserCommands;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Management.BotInfo;
import Management.Keywords;

public class GifSearch 
{
	@Getter
	public static String help = "Searches for a specified gif";
	private String command = "gif";
	private String searchURL = "https://tenor.com/search/";
	private String returnGifUrl = "";
	
	public GifSearch(DiscordApi getApi)
	{
		DiscordApi gifApi = getApi;
		
		gifSearch(gifApi);
	}
	
	public GifSearch(String searchTerm)
	{
		String gifUrl = "";
		
		gifUrl = searchGif(searchTerm, searchURL);
		
		setGifReturnUrl(gifUrl);
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
					else
					{
						//event.getMessage().addReaction("");
						event.getChannel().sendMessage("No image found");
						
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
		String fullSizeUrl = "";
		
		Document searchReference;
		Document secondSearchRef;
		Elements getDiv;
		Elements getImg;
		Elements fullDiv;
		Elements fullImg;
		
		Random randNum = new Random();
		final int REDUCEIMAGES = 2;
		final int MAXIMAGES = 30;
		int maxImagesFound = 0;
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
							
			getDiv = searchReference.select("figure.GifListItem");
			
			maxImagesFound = getDiv.size();
			
			if (maxImagesFound > MAXIMAGES)
			{
				maxImagesFound /= REDUCEIMAGES;
			}
			if (maxImagesFound == 0)
			{
				gifToSend = "";
			}
			else
			{
				do
				{
			
					randNumPick = randNum.nextInt(maxImagesFound);
				
					getImg = getDiv.get((int) (randNumPick)).select("a");
					fullSizeUrl = getImg.attr("abs:href").toString();
				
					secondSearchRef = Jsoup.connect(fullSizeUrl)
							.followRedirects(true)
							.ignoreHttpErrors(true)
							.userAgent(userAgentSelect)
							.get();

					
				} while (fullSizeUrl.equalsIgnoreCase("https://tenor.com/gif-maker?utm_source=search-page&utm_medium=internal&utm_campaign=gif-maker-entrypoints"));
					
					fullDiv = secondSearchRef.select("div.Gif");
					fullImg = fullDiv.get(0).select("img");
					gifToSend = fullImg.attr("abs:src");
				
			}	
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if (!gifToSend.equals(""))
		{
			return gifToSend;
		}
		else
		{
			return "noneFoundHere";
		}
	}
	
	public void setGifReturnUrl(String gifURL)
	{
		returnGifUrl = gifURL;
	}
	
	public String getGifReturnUrl()
	{
		return returnGifUrl;
	}
}
