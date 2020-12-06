package Listener;

import java.awt.Color;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Database.GlobalUserInformation;
import Management.*;

public class Uwu
{
	private String policeEnforcement = "https://b.catgirlsare.sexy/zWCg.png";
	private static String keyFielduwu = "Fine Amount";
	private static int fine = 350;
	
	public Uwu(DiscordApi getApi)
	{
		DiscordApi uwuApi = getApi;
		uwuListener(uwuApi);
	}
	
	public EmbedBuilder createEmbed(ArrayList<UserFineObject> sortedUserList, boolean checkType, DiscordApi getApi) throws InterruptedException, ExecutionException
	{
		DiscordApi userApi = getApi;
		CompletableFuture<User> currUser;
		ArrayList<UserFineObject> userInfo = sortedUserList;
		String userName = "";
		String userRank = "";
		String formatFine = "";
		long userFine = 0;
		int i, count = 1, userArraySize = 0;
		final int MAXPRINTOUT = 10;
		
		userArraySize = userInfo.size() - 1;
		
		EmbedBuilder messageEmbed = new EmbedBuilder()
				.setTitle("Top Criminals")
				.setColor(Color.magenta);
		
		if (checkType)
		{
			for (i = userInfo.size() - 1; i > 0; --i)
			{
				currUser = userApi.getUserById(userInfo.get(i).getUserID());
				userFine = userInfo.get(i).getFineValue();
				userName = currUser.get().getDiscriminatedName();
				
				userRank = Integer.toString(count);
				//formatFine = formatCurrency(userFine);
				
				messageEmbed.addField("-----------------------", userRank + ": " + userName + " Fine Amount: " + formatFine);
				
				++count;
			}
		}
		else
		{

			for (i = 0; i < MAXPRINTOUT; ++i)
			{
				currUser = userApi.getUserById(userInfo.get(userArraySize - i).getUserID());
				userFine = userInfo.get(userArraySize - i).getFineValue();
				userName = currUser.get().getDiscriminatedName();
				
				userRank = Integer.toString(count);
				//formatFine = formatCurrency(userFine);
				
				messageEmbed.addField("-----------------------", userRank + ": " + userName + " Fine Amount: " + formatFine);
				
				++count;
			}
		}
		
		messageEmbed.setTimestampToNow();
		
		return messageEmbed;		
	}
	
	public static Long getUserFine(String id)
	{
		JSONObject userInfo;
		JSONParser parseInfo = new JSONParser();
		Object checkStorage;
		String filePath = id;
		long userFine = 0;
		
		try
		{
			checkStorage = parseInfo.parse(new FileReader(id));
			userInfo = (JSONObject) checkStorage;
			
			userFine = (long) userInfo.get(keyFielduwu);
			
			return userFine;
			
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void leaderboardRequest(DiscordApi getApi)
	{
		DiscordApi leaderBoardApi = getApi;
		
		leaderBoardApi.addMessageCreateListener(event ->
		{
			String getMessage;
			String splitMessage[] = null;
			String serverID = "";
			String myKey = "";
			String currUser = "";
			String userPath = "";
			int userReportSize = 0;
			long userFine = 0;
			ArrayList<UserFineObject> userSortList = new ArrayList<>();
			UserFineObject usersObject;
			boolean checkEmbedType = false;
			final int MAXPRINTOUT = 10;
			
			serverID = event.getServer().get().getIdAsString();
			myKey = Keywords.getKey(serverID);
			
			if (event.getMessageAuthor().isUser())
			{
				getMessage = event.getMessageContent();
				splitMessage = getMessage.split(" ");
				
				if (splitMessage[0].equalsIgnoreCase(myKey + "leaderboard") && splitMessage.length == 1)
				{
					for (User userInfo : event.getServer().get().getMembers())
					{
						currUser = userInfo.getIdAsString();
						userPath = GlobalUserInformation.getUserIDList().get(currUser);
						userFine = getUserFine(userPath);
						
						usersObject = new UserFineObject(currUser, userFine);
						userSortList.add(usersObject);
					}
					
					Collections.sort(userSortList);
					userReportSize = userSortList.size();
					
					if (userReportSize < MAXPRINTOUT)
					{
						checkEmbedType = true;
					}
					
					try
					{
						event.getChannel().sendMessage(createEmbed(userSortList, checkEmbedType, leaderBoardApi));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}	
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public void setUserFine(String id)
	{
		JSONObject saveData = new JSONObject();
		saveData.put(keyFielduwu, getUserFine(GlobalUserInformation.getUserByIdDb(id)) + fine);
		Files.write(Paths.get(GlobalUserInformation.getUserByIdDb(id)), saveData.toJSONString().getBytes());
	}
	
	public void uwuListener(DiscordApi getApi)
	{
		DiscordApi uwuApi = getApi;
		
		uwuApi.addMessageCreateListener(event -> {
			if (event.getMessage().getAuthor().getIdAsString().equals(BotInfo.getOwnerId())) {
				return;
			}
			if (!event.getMessage().getAuthor().isRegularUser()) {
				return;
			}
			StringBuilder msg = new StringBuilder(event.getMessageContent().replaceAll("\\s", ""));

			if (msg.toString().equalsIgnoreCase("uwu")) {
				event.getChannel().sendMessage(violationEmbed());
				setUserFine(event.getMessage().getAuthor().getIdAsString());
			} else {
				for (int i = 0; i < msg.length() - 2; ++i) {
					if (msg.charAt(i) == 'u'
							&& msg.charAt(i + 1) == 'w'
							&& (msg.charAt(i + 2) == 'u')) {

						event.getChannel().sendMessage(violationEmbed());
						setUserFine(event.getMessage().getAuthor().getIdAsString());
						break;
					}
				}
			}
		});
	}

	private EmbedBuilder violationEmbed () {
		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Stop! you've violated the law for the last time")
				.setImage(policeEnforcement)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();
		return embed;
	}
}
