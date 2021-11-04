package Listener;

import java.awt.Color;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import Command.Command;
import Database.InitDatabase;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Database.GlobalUserInformation;
import Management.*;

public class UwuListener extends Command {
	private static String key = "Fine Amount";
	private static final int fine = 350;

	public UwuListener(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
				uwuListener(super.getChannel(), super.getServer(), super.getMessageAuthor(), super.getMessage()));
	}

	@SneakyThrows
	public static Long getUserFine(String path) {
		JSONParser data = new JSONParser();
		JSONObject obj = (JSONObject) data.parse(new FileReader(path + ".json"));

		return (long) obj.get(key);
	}

	@SuppressWarnings("unchecked")
	@SneakyThrows
	public void setUserFine(String id) {
		JSONObject saveData = new JSONObject();
		saveData.put(key, getUserFine(GlobalUserInformation.filePath + id) + fine);
		Files.write(Paths.get(GlobalUserInformation.filePath + id + ".json"), saveData.toJSONString().getBytes());
	}

	@SneakyThrows
	public void uwuListener(TextChannel channel, Server server, MessageAuthor author, Message message) {
		if (author.getIdAsString().equals(BotInfo.getOwnerId()) || author.getIdAsString().equals(BotInfo.getFoxxId()) || !author.isRegularUser() || isIgnoredChannel()) {
			return;
		}
		if (!Boolean.parseBoolean(InitDatabase.getData().get(server.getIdAsString()).getUwu())) {
			return;
		}

		StringBuilder msg = new StringBuilder(message.getContent().replaceAll("\\s", ""));
		if (msg.toString().toLowerCase().contains("fuckmeuwu.com")) {
			return;
		}

		if (msg.toString().equalsIgnoreCase("uwu")) {
			channel.sendMessage(violationEmbed(author.asUser().get()));
			setUserFine(author.getIdAsString());
			Thread.sleep(1500);
			message.delete();
		} else {
			for (int i = 0; i < msg.length() - 2; ++i) {
				if (msg.charAt(i) == 'u'
						&& msg.charAt(i + 1) == 'w'
						&& (msg.charAt(i + 2) == 'u')) {

					channel.sendMessage(violationEmbed(author.asUser().get()));
					setUserFine(author.getIdAsString());
					Thread.sleep(1500);
					message.delete();
					break;
				}
			}
		}
	}

	private EmbedBuilder violationEmbed (User user) {
		return new EmbedBuilder()
				.setTitle("Stop " + user.getName() + "! You have violated the law for the last time")
				.setImage("https://b.catgirlsare.sexy/oWAgq-fc.png")
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow()
				.setColor(Color.MAGENTA);
	}
}
