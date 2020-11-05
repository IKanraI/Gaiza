/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Gaiza;
import AdminCommands.*;
import Listener.BoredResponse;
import Listener.MicrowaveResponse;
import Listener.UwuResponse;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import UserCommands.*;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JavacordHandler;
import Database.*;
import Management.*;

import java.io.*;

public class Library
{
	
	 public boolean someLibraryMethod() {
	        return true;
	 }

	public static void main(String[] args) throws Exception {
		Token token = new Token();
		DiscordApi api = new DiscordApiBuilder().setToken(token.getToken()).login().join();
		api.updateActivity(BotInfo.getBotActivity());
		
		commandInit(api);
	}
	
	@SuppressWarnings("unused")
	static void commandInit(DiscordApi api) throws Exception {
		//Initializes all the commands, listeners, and management
		String fileA = "Ping";
		
		File folder = new File("C:\\Users\\17244\\Documents\\JavaProjects\\Gaiza\\src\\main\\java\\commands");
		
		InitDatabase dbInit = new InitDatabase(api);
		GlobalUserInformation initUsers = new GlobalUserInformation(api);

		//Ping ping = new Ping(api);

		System.out.println("\n--------------------------------");
		System.out.println("Bot command files loading... \n");

		Ping pingInit = new Ping(api);
		Invite inviteInit = new Invite(api);
		Avatar avatarInit = new Avatar(api);
		Help helpInit = new Help(api);
		UrbanDictionary UDInit = new UrbanDictionary(api);
		GifSearch gifInit = new GifSearch(api);
		ChanceRolls initRoll = new ChanceRolls(api);
		MentionOtherUsers initMention = new MentionOtherUsers(api);

		System.out.println("\nCommands finished loading!");
		System.out.println("--------------------------------");
		System.out.println("Admin commands loading... \n");

		PrefixChange initPChange = new PrefixChange(api);
		AdminHelp initAdminHelp = new AdminHelp(api);
		KickUser initKick = new KickUser(api);
		BanUser initBan = new BanUser(api);
		WelcomeMessage initWelcome = new WelcomeMessage(api);

		System.out.println("\nAdmin commands loaded!");
		System.out.println("--------------------------------");
		System.out.println("Bot listener files loading... \n");

		BoredResponse boredInit = new BoredResponse(api);
		MicrowaveResponse microInit = new MicrowaveResponse(api);
		UwuResponse uwuInit = new UwuResponse(api);

		System.out.println("\nBot listener files loaded!");
		System.out.println("---------------------------------");
		System.out.println("Management files loading... \n");

		BotInfo bInfoInit = new BotInfo(api);

		System.out.println("\nManagement files loaded!\n");

		api.updateActivity(BotInfo.getBotActivity());
		
		/*try {
			for (final File commandName : folder.listFiles()) {
				if (!commandName.isDirectory()) {
					String[] fileName = commandName.getName().split(".");
					cHandler.registerCommand((CommandExecutor) Class.forName(fileName[0]).newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}