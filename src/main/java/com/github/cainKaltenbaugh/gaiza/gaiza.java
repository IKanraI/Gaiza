package com.github.cainKaltenbaugh.gaiza;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import management.Token;

import java.io.*;

public class gaiza {

	public static void main(String[] args) throws FileNotFoundException 
	{
		String token = "";
		Token gaizaToken = new Token();
		
		token = gaizaToken.getToken();

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
				
		api.addMessageCreateListener(event ->{
			
			if (event.getMessageContent().equalsIgnoreCase("$ping"))
			{
				event.getChannel().sendMessage("Oh boy can't wait to be abused while my new source code is written");
			}
		});
				
		//System.out.println("invite" + api.createBotInvite());
	}

}
