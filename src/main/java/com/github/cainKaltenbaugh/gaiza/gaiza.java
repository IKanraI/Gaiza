package com.github.cainKaltenbaugh.gaiza;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import commands.*;
import commandsAdmin.*;
import jsonDatabase.*;
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

	public static void main(String[] args) throws Exception 
	{
		DiscordApi startUpApi = startUp();
		
		commandInit(startUpApi);
				
		//System.out.println("invite" + api.createBotInvite());
	}
	
	@SuppressWarnings("unused")
	static void commandInit(DiscordApi initApi) throws Exception
	{
		//Initializes all the commands, listeners, and management
		DiscordApi myApi = initApi;
		
		System.out.println("\nServer List");
		
		InitDatabase dbInit = new InitDatabase(initApi);
		GlobalUserInformation initUsers = new GlobalUserInformation(initApi);
		
		System.out.println("\n--------------------------------");
		System.out.println("Bot command files loading... \n");
		
		Ping pingInit = new Ping(initApi);
		Invite inviteInit = new Invite(initApi);
		Avatar avatarInit = new Avatar(initApi);
		Help helpInit = new Help(initApi);
		UrbanDictionary UDInit = new UrbanDictionary(initApi);
		GifSearch gifInit = new GifSearch(initApi);
		ChanceRolls initRoll = new ChanceRolls(initApi);
		
		System.out.println("\nCommands finished loading!");
		System.out.println("--------------------------------");
		System.out.println("Admin commands loading... \n");
		
		PrefixChange initPChange = new PrefixChange(initApi);
		AdminHelp initAdminHelp = new AdminHelp(initApi);
		KickUser initKick = new KickUser(initApi);
		BanUser initBan = new BanUser(initApi);
		WelcomeMessage initWelcome = new WelcomeMessage(initApi);
		
		System.out.println("\nAdmin commands loaded!");
		System.out.println("--------------------------------");
		System.out.println("Bot listener files loading... \n");
		
		BoredResponse boredInit = new BoredResponse(initApi);
		MicrowaveResponse microInit = new MicrowaveResponse(initApi);
		UwuResponse uwuInit = new UwuResponse(initApi);
		
		System.out.println("\nBot listener files loaded!");
		System.out.println("---------------------------------");
		System.out.println("Management files loading... \n");
		
		BotInfo bInfoInit = new BotInfo(initApi);
		
		System.out.println("\nManagement files loaded!\n");
		
		myApi.updateActivity(BotInfo.getBotActivity());
	}
}
