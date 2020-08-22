package commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.javacord.api.DiscordApi;

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
	
	//Pat, boop, slap, hug, snuggle, kiss, poke, stare
	
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
				
				if (splitMessage[0].charAt(0) == checkKeyAsChar)
				{
					checkMessage = splitMessage[0].replace(serverKey, "");
					
					userCommandCalled = getCalledCommand(checkMessage);
					
					if (splitMessage.length == 2 && userCommandCalled != null)
					{
						GifSearch getSearchGif;
						String userMentioned = "";
						String getGifToSend = "";
						String searchTerm = "";
						String finalGifSend = "";
						
						searchTerm = "Anime " + userCommandCalled;

						try
						{
							userMentioned = event.getMessage().getMentionedUsers().get(0).getIdAsString();
							getSearchGif = new GifSearch(searchTerm);
							
							finalGifSend = getSearchGif.getGifReturnUrl();
							
							event.getChannel().sendMessage(finalGifSend);
						}
						catch(Exception e)
						{
							event.getChannel().sendMessage("User was not able to be mentioned");
						}
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
