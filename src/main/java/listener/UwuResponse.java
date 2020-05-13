package listener;

import java.awt.Color;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jsonDatabase.GlobalUserInformation;
import management.*;

public class UwuResponse 
{
	private String policeEnforcement = "https://b.catgirlsare.sexy/zWCg.png";
	private String keyFielduwu = "Fine Amount";
	private String command = "fines";
	private String commandLeader = "leaderboard";
	private int uwuAmount = 350;
	
	public UwuResponse(DiscordApi getApi)
	{
		DiscordApi uwuApi = getApi;
		
		uwuListener(uwuApi);
		userRequestFineAmount(uwuApi);
		leaderboardRequest(uwuApi);
		
		System.out.println("UwuResponse.java loaded!");
	}
	
	public void addUserFine(String ID)
	{
		String userID = ID;
		String userFilePath = "";
		long userAmountStored = 0;
		long finalAmountToStore = 0;
		long errorReturn = -1;
		
		userFilePath = getUserPath(userID);
		userAmountStored = getCurrentFine(userFilePath);
		
		if (userAmountStored != errorReturn)
		{
			finalAmountToStore = userAmountStored + uwuAmount;
			
			storeUserAmount(userFilePath, finalAmountToStore);
		}
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
				formatFine = formatCurrency(userFine);
				
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
				formatFine = formatCurrency(userFine);
				
				messageEmbed.addField("-----------------------", userRank + ": " + userName + " Fine Amount: " + formatFine);
				
				++count;
			}
		}
		
		messageEmbed.setTimestampToNow();
		
		return messageEmbed;		
	}
	
	public String formatCurrency(long moneyToFormat)
	{
		NumberFormat formatToCurrency = NumberFormat.getCurrencyInstance(Locale.US);
		long getMoney = moneyToFormat;
		String formattedOutput;
		
		formattedOutput = formatToCurrency.format(getMoney);
		
		return formattedOutput;
	}
	
	public long getCurrentFine(String userPath)
	{
		JSONObject userInfo;
		JSONParser parseInfo = new JSONParser();
		Object checkStorage;
		String filePath = userPath;
		long errorReturn = -1;
		long userFine = 0;
		
		try
		{
			checkStorage = parseInfo.parse(new FileReader(filePath));
			userInfo = (JSONObject) checkStorage;
			
			userFine = (long) userInfo.get(keyFielduwu);
			
			return userFine;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return errorReturn;
		}
	}
	
	public String getUserPath(String ID)
	{
		Map<String, String> userList;
		String userID = ID;
		String userPath = "";
		
		userList = GlobalUserInformation.getUserIDList();
		userPath = userList.get(userID);
		
		return userPath;
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
				
				if (splitMessage[0].equalsIgnoreCase(myKey + commandLeader) && splitMessage.length == 1)
				{
					for (User userInfo : event.getServer().get().getMembers())
					{
						currUser = userInfo.getIdAsString();
						userPath = getUserPath(currUser);
						userFine = getCurrentFine(userPath);
						
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
	public void storeUserAmount(String userPath, long finalAmountToStore)
	{
		JSONObject saveData = new JSONObject();
		long amountToStore = finalAmountToStore;
		String filePath = userPath;
		
		saveData.put(keyFielduwu, amountToStore);
		
		try
		{
			Files.write(Paths.get(filePath), saveData.toJSONString().getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void userRequestFineAmount(DiscordApi getApi)
	{
		DiscordApi requestApi = getApi;
		
		requestApi.addMessageCreateListener(event ->
		{
			String messageString = "";
			String[] splitMessage = null;
			String myKey = "";
			String serverID = "";
			String userID = "";
			String userPath = "";
			String finalCurrency = "";
			long userFine = 0;
			long errorReturn = -1;
			
			if (event.getMessageAuthor().isUser())
			{
				serverID = event.getServer().get().getIdAsString();
				myKey = Keywords.getKey(serverID);
				userID = event.getMessageAuthor().getIdAsString();
				
				messageString = event.getMessageContent();
				splitMessage = messageString.split(" ");
				
				
				if (splitMessage[0].equalsIgnoreCase(myKey + command) && splitMessage.length == 1)
				{
					userPath = getUserPath(userID);
					userFine = getCurrentFine(userPath);
					finalCurrency = formatCurrency(userFine);
					
					if (userFine != errorReturn)
					{
						event.getChannel().sendMessage("<@" + userID + ">, you currently owe: " + finalCurrency);
					}
				}
			}
		});
	}
	
	public void uwuListener(DiscordApi getApi)
	{
		DiscordApi uwuApi = getApi;
		
		uwuApi.addMessageCreateListener(event ->
		{
			final int PROTECTARRAYLENGTH = 2;
			String getWholeMessage = "";
			String splitMessage = "";
			String messageToGet = "uwu";
			String userID = "";
			ArrayList<Character> charMessageArray = null;
			int i = 0;
			
			if (event.getMessage().getAuthor().isUser())
			{
				getWholeMessage = event.getMessageContent();
				getWholeMessage = getWholeMessage.toLowerCase();
				splitMessage = getWholeMessage.replaceAll("\\s", "");
				userID = event.getMessageAuthor().getIdAsString();
				
				charMessageArray = new ArrayList<Character>(splitMessage.length());
				
				for (i = 0; i < splitMessage.length(); ++i)
				{
					charMessageArray.add(splitMessage.charAt(i));
				}
			}

			try
			{
				
				if (messageToGet.equalsIgnoreCase(getWholeMessage))
				{
					EmbedBuilder embed = new EmbedBuilder()
							.setTitle("Stop! you've violated the law for the last time")
							.setImage(policeEnforcement)
							.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
							.setTimestampToNow();
					
					event.getChannel().sendMessage(embed);
					
					addUserFine(userID);
				}
				else
				{
					for (i = 0; i < splitMessage.length() - PROTECTARRAYLENGTH; ++i)
					{
						if (charMessageArray.get(i).equals('u') && charMessageArray.get(i + 1).equals('w') && charMessageArray.get(i + 2).equals('u'))
						{
							EmbedBuilder embed = new EmbedBuilder()
									.setTitle("Stop! you've violated the law for the last time")
									.setImage(policeEnforcement)
									.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
									.setTimestampToNow();
							
							event.getChannel().sendMessage(embed);
							
							addUserFine(userID);
							
							break;
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
}
