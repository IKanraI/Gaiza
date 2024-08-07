package fixThese;

import java.awt.Color;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import util.BotInfo;
import model.InitDatabase;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.GlobalUserInformation;

public class UwuListener {
	private static String key = "Fine Amount";
	private static final int fine = 350;

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
//		if (author.getIdAsString().equals(BotInfo.getOwnerId()) || !author.isRegularUser() || isIgnoredChannel()) {
//			return;
//		}
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
				if (StringUtils.equalsIgnoreCase(String.valueOf(msg.charAt(i)),"u")
						&& StringUtils.equalsIgnoreCase(String.valueOf(msg.charAt(i)),"w")
						&& StringUtils.equalsIgnoreCase(String.valueOf(msg.charAt(i)),"u")) {

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
				.setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage())
				.setTimestampToNow()
				.setColor(Color.MAGENTA);
	}
}
