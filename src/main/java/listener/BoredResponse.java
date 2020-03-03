package listener;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.javacord.api.DiscordApi;

public class BoredResponse 
{
	private ArrayList<String> reprimandPhrases = new ArrayList<String>();
	
	public BoredResponse(DiscordApi getApi)
	{
		DiscordApi boredApi = getApi;
		
		setupPhrases();		
		respondToBored(boredApi);
		
		System.out.println("BoredResponse.java Loaded!");
	}
	
	public String pickPhrase()
	{
		String returnMessage = "";
		int numPick = 0;
		
		numPick = pickRandomNum();
		
		returnMessage = reprimandPhrases.get(numPick); 
		
		return returnMessage;
	}
	
	public int pickRandomNum()
	{
		Random genNum = new Random();
		int setNum = 0;
		
		setNum = genNum.nextInt(reprimandPhrases.size());
		
		return setNum;
	}
	
	public void respondToBored(DiscordApi getApi)
	{
		DiscordApi boredApi = getApi;		
		
		boredApi.addMessageCreateListener(event ->
		{
			String selectString;
			
			selectString = pickPhrase();
			
			if (event.getMessageContent().equalsIgnoreCase("im bored") || event.getMessageContent().equalsIgnoreCase("i'm bored") || event.getMessageContent().equalsIgnoreCase("bored"))
			{
				event.addReactionsToMessage("🚫");
				
				event.getChannel().sendMessage("Hi bored, I'm dad");
				
				try 
				{
					Thread.sleep(1000);
				
				
					event.getChannel().sendMessage("<@" + event.getMessageAuthor().getIdAsString() + ">" + selectString);
				
				
					Thread.sleep(1000);
				
				
					event.getChannel().sendMessage("I'm going out to the store for some cigs and milk. Don't tell your mother");
				
				
					Thread.sleep(1000);
				
				}
				catch (InterruptedException e) 
				{	
					e.printStackTrace();
				}
			}
				
		});
		
	}

	public void setupPhrases()
	{
		File phraseFile;
		Scanner phraseScan;
		
		try
		{
			phraseFile = new File("../gaiza/bin/Resource/boredPhrases.txt");
			phraseScan = new Scanner(phraseFile);
			
			while(phraseScan.hasNextLine())
			{
				reprimandPhrases.add(phraseScan.nextLine());
			}
			
			phraseScan.close();
			
		}
		catch(Exception e)
		{
			System.out.println("File not found");
			e.printStackTrace();
		}		
	}
	
	
}
