package com.github.cainKaltenbaugh.gaiza;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.*;
import listener.*;
import management.*;

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
		//Initializes all the commands, listeners, and management
		DiscordApi myApi = initApi;
		
		System.out.println("Bot command files loading... \n");
		
		Ping pingInit = new Ping(initApi);
		Invite inviteInit = new Invite(initApi);
		Avatar avatarInit = new Avatar(initApi);
		Help helpInit = new Help(initApi);
		
		System.out.println("\nCommands finished loading!");
		System.out.println("--------------------------------");
		System.out.println("Bot listener files loading... \n");
		
		BoredResponse boredInit = new BoredResponse(initApi);
		MicrowaveResponse microInit = new MicrowaveResponse(initApi);
		
		System.out.println("\nBot listener files loaded!");
		System.out.println("---------------------------------");
		System.out.println("Management files loading... \n");
		
		BotInfo bInfoInit = new BotInfo(initApi);
		
		System.out.println("\nManagement files loaded!");
		
		myApi.updateActivity(BotInfo.getBotActivity());
	}
}
