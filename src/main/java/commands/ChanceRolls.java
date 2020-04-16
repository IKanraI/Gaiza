package commands;

import java.awt.Color;
import java.io.File;
import java.util.Random;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import management.Keywords;

public class ChanceRolls 
{
	/*Uhhh I need a coin flip
	A six-sided die
	And maybe a ten sided
	20 sided die*/
	
	private String commandCoin = "flip";
	private String commandRoll = "roll";
	private String command6 = "6";
	private String command10 = "10";
	private String command20 = "20";
	
	public ChanceRolls(DiscordApi getApi)	
	{
		DiscordApi chanceApi = getApi;
		
		coinFlip(chanceApi);
		rollSix(chanceApi);
		rollTen(chanceApi);
		rollTwenty(chanceApi);
		
		System.out.println("ChanceRolls.java loaded!");
	}
	
	public void coinFlip(DiscordApi getApi)
	{
		DiscordApi coinApi = getApi;
		final int HEADSIDE = 1;
		
		coinApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverID = "";
			String userMessage = "";
			String finalResponse = "";
			String heads = "Heads";
			String tails = "Tails";
			int getFlip = 0;
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			userMessage = event.getMessageContent().toString();
			
			if (event.getMessageAuthor().isUser())
			{
				if (userMessage.equalsIgnoreCase(myKey + commandCoin))
				{
					Random getSide = new Random();
					
					getFlip = getSide.nextInt(2);
					
					if (getFlip == HEADSIDE)
					{
						finalResponse = heads;
					}
					else
					{
						finalResponse = tails;
					}
					
					event.getChannel().sendMessage(finalResponse);
				}
			}
		});
	}
	
	public void rollSix(DiscordApi getApi)
	{
		DiscordApi rollApi = getApi;
		
		rollApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverID = "";
			String userMessage = "";
			String splitMessage[] = null;
			String finalPath = "";
			String fileType = ".png";
			String currPath = "C:\\Users\\Cain\\Documents\\javaDocs\\gaiza\\bin\\Resource\\6 Side\\";
			int randomRoll = 0;
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			userMessage = event.getMessageContent();
			
			splitMessage = userMessage.split(" ");
			
			if (event.getMessageAuthor().isUser())
			{
				if (splitMessage[0].equalsIgnoreCase(myKey + commandRoll) && splitMessage.length == 2)
				{
					if (splitMessage[1].equals(command6))
					{
						Random getRandom = new Random();
						
						randomRoll = getRandom.nextInt(6);
						finalPath = currPath.concat(randomRoll + fileType);
						
						EmbedBuilder sendRoll = new EmbedBuilder()
								.setColor(Color.magenta)
								.setImage(new File(finalPath));
						
						event.getChannel().sendMessage(sendRoll);
					}
				}
			}
		});
	}
	
	public void rollTen(DiscordApi getApi)
	{
		DiscordApi rollApi = getApi;
		final int MAX = 10;
		final int MIN = 1;
		
		rollApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverID = "";
			String userMessage = "";
			String splitMessage[] = null;
			String sendRoll = "";
			int randomRoll;
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			userMessage = event.getMessageContent();
			
			splitMessage = userMessage.split(" ");
			
			if (event.getMessageAuthor().isUser())
			{
				if (splitMessage[0].equalsIgnoreCase(myKey + commandRoll) && splitMessage.length == 2)
				{
					if (splitMessage[1].equals(command10))
					{
						Random getRandom = new Random();
						
						randomRoll = getRandom.nextInt((MAX - MIN) + MIN) + MIN;
						sendRoll = Integer.toString(randomRoll);
						
						event.getChannel().sendMessage(sendRoll);
					}
				}
			}
		});
	}
	
	public void rollTwenty(DiscordApi getApi)
	{
		DiscordApi rollApi = getApi;
		final int MAX = 20;
		final int MIN = 1;
		
		rollApi.addMessageCreateListener(event ->
		{
			String myKey = "";
			String serverID = "";
			String userMessage = "";
			String splitMessage[] = null;
			String sendRoll = "";
			int randomRoll = 0;
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			userMessage = event.getMessageContent();
			
			splitMessage = userMessage.split(" ");
			
			if (event.getMessageAuthor().isUser())
			{
				if (splitMessage[0].equalsIgnoreCase(myKey + commandRoll) && splitMessage.length == 2)
				{
					if (splitMessage[1].equals(command20))
					{
						Random getRandom = new Random();
						
						randomRoll = getRandom.nextInt((MAX - MIN) + MIN) + MIN;						
						sendRoll = Integer.toString(randomRoll);
						
						event.getChannel().sendMessage(sendRoll);
					}
				}
			}
		});
	}
}
