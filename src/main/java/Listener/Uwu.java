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
	private static String key = "Fine Amount";
	private static int fine = 350;
	
	public Uwu(DiscordApi getApi) {
		DiscordApi uwuApi = getApi;
		uwuListener(uwuApi);
	}

	@SneakyThrows
	public static Long getUserFine(String path) {
		JSONParser data = new JSONParser();
		JSONObject obj = (JSONObject) data.parse(new FileReader(path));

		return (long) obj.get(key);
	}
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public void setUserFine(String id) {
		JSONObject saveData = new JSONObject();
		saveData.put(key, getUserFine(GlobalUserInformation.getUserByIdDb(id)) + fine);
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
				.setTimestampToNow()
				.setColor(Color.MAGENTA);
		return embed;
	}
}
