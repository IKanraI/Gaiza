package com.github.cainKaltenbaugh.gaiza;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.*;
import listener.BoredResponse;
import management.Token;

import java.io.*;

public class gaiza 
{
	static DiscordApi startUp() throws FileNotFoundException
	{
		String token = "";
		Token gaizaToken = new Token();
		
		token = gaizaToken.getToken();

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		
		return api;
	}

	public static void main(String[] args) throws FileNotFoundException 
	{
		DiscordApi startUpApi = startUp();
		
		commandInit(startUpApi);
				
		//System.out.println("invite" + api.createBotInvite());
	}
	
	static void commandInit(DiscordApi initApi)
	{
		DiscordApi myApi = initApi;
		
		System.out.println("Bot command files loading...");
		
		Ping pingInit = new Ping(initApi);
		Invite inviteInit = new Invite(initApi);
		
		System.out.println("Commands finished loading!");
		System.out.println("Bot listener files loading...");
		
		BoredResponse boredInit = new BoredResponse(initApi);
		
		System.out.println("Bot listener files loaded!");
	}
}
