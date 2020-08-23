package commands;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import management.Keywords;

public class MentionOtherUsers 
{
	private List<String> mentionableList = new ArrayList<String>();
	
	public MentionOtherUsers(DiscordApi getApi)
	{
		DiscordApi mentionApi = getApi;
		
		setMentionables();
		getMentionType(mentionApi);
	}
	
	//Pat, boob, slap, hug, snuggle, kiss, poke, stare
	
	public EmbedBuilder buildEmbed (String gifToSend)
	{
		EmbedBuilder embedToReturn;
		String gifToEmbed = gifToSend;
		
		embedToReturn = new EmbedBuilder()
				.setColor(Color.magenta)
				
				.setImage(gifToEmbed)
				.setTimestampToNow();
		
		return embedToReturn;
	}
	
	public void getMentionType(DiscordApi getApi)
	{
		DiscordApi mentionUserTypeApi = getApi;
		
		mentionUserTypeApi.addMessageCreateListener(event ->
		{
			String userMessage = "";
			String checkMessage = "";
			String userCommandCalled = "";
			String[] splitMessage = null;
			String getServerFromMessage = "";
			String serverKey = "";
			char checkKeyAsChar;
			
			getServerFromMessage = event.getServer().get().getIdAsString();
			serverKey = Keywords.getKey(getServerFromMessage);
			
			if (event.getMessageAuthor().isUser())
			{
				userMessage = event.getMessageContent();
				splitMessage = userMessage.split(" ");
				
				checkKeyAsChar = serverKey.charAt(0);
				
				if (splitMessage[0].length() > 0 && splitMessage[0].charAt(0) == checkKeyAsChar)
				{
					checkMessage = splitMessage[0].replace(serverKey, "");
					
					userCommandCalled = getCalledCommand(checkMessage);
					
					if (userCommandCalled != null && splitMessage.length == 2)
					{
						GifSearch getSearchGif;
						EmbedBuilder messageEmbedToSend;
						String userMentioned = "";
						String messageAuthor = "";
						String searchTerm = "";
						String finalGifSend = "";
						char lastCharInCommand = '\n';
						
						searchTerm = "Anime " + userCommandCalled;

						try
						{
							userMentioned = event.getMessage().getMentionedUsers().get(0).getIdAsString();
							messageAuthor = event.getMessageAuthor().getIdAsString();
							getSearchGif = new GifSearch(searchTerm);
							
							finalGifSend = getSearchGif.getGifReturnUrl();
							
							messageEmbedToSend = buildEmbed(finalGifSend);
							
							lastCharInCommand = userCommandCalled.charAt(userCommandCalled.length() - 1);
							
							switch (lastCharInCommand)
							{
								case 'e':
									userCommandCalled += "d";
									break;
									
								case 't':
									userCommandCalled += "ted";
									break;
								
								case 'p':
									userCommandCalled += "ped";
									break;
								
								case 'g':
									userCommandCalled += "ged";
									break;
									
								default:
									userCommandCalled += "ed";
									break;
							}
							
							event.getChannel().sendMessage("<@" + userMentioned + ">, you have been " + userCommandCalled + " by <@" + messageAuthor + ">");
							event.getChannel().sendMessage(messageEmbedToSend);
						}
						catch(Exception e)
						{
							event.getChannel().sendMessage("User was not able to be mentioned");
						}
					}
					else if (userCommandCalled != null && splitMessage.length == 1)
					{
						event.getMessage().addReaction("❌");
						event.getChannel().sendMessage("Please mention a user");
					}
				}
			}			
		});
	}
	
	public void setMentionables()
	{
		File fileToImport;
		Scanner grabFromFile;
		
		try
		{
			fileToImport = new File("C:\\Users\\17244\\Documents\\JavaProjects\\Gaiza\\bin\\Resource\\mentionUserList.txt");
			grabFromFile = new Scanner(fileToImport);
			
			while (grabFromFile.hasNext())
			{
				mentionableList.add(grabFromFile.next());
			}
			
			grabFromFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getCalledCommand(String userCommand)
	{
		int i;
		String getCommandFromUserCall = "";
		
		for (i = 0; i < mentionableList.size(); ++i)
		{
			if (mentionableList.get(i).equalsIgnoreCase(userCommand))
			{
				getCommandFromUserCall = mentionableList.get(i);
				break;
			}
			else
			{
				getCommandFromUserCall = null;
			}
		}

		return getCommandFromUserCall;
	}

}
