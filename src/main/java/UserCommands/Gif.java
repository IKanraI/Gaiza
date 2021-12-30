package UserCommands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Command.Command;
import Management.Tenor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Management.BotInfo;

public class Gif extends Command
{
	@Getter
	public static String help = "Searches for a specified gif. Returns a random results from the search. Usage [prefix]gif [query]";

	public Gif(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
				userGifSearch(super.getChannel(), super.getMessageAuthor(), super.getArgs()));
	}

	private void userGifSearch(TextChannel channel, MessageAuthor author, List<String> args) {
		if (!onCommand()) {
			return;
		}
		if (args.size() == 0) {
			channel.sendMessage("Please enter a search term");
			return;
		}
		StringBuilder term = new StringBuilder();
		for (String s : args) {
			term.append(s + " ");
		}

		channel.sendMessage(buildEmbed(searchGif(term.toString()), author)).exceptionally(e -> {
			channel.sendMessage("Gif could not be returned");
			return null;
		});

	}

	public EmbedBuilder buildEmbed(String url, MessageAuthor author) {
		EmbedBuilder message = new EmbedBuilder()
				.setTitle("A gif for your viewing")
				.setAuthor(author.getName(), author.getAvatar().getUrl().toString(), author.getAvatar())
				.setColor(Color.magenta)
				.setImage(url)
				.setFooter(BotInfo.getBotName(), BotInfo.getBotImage())
				.setTimestampToNow();

		return message;
	}

	@SneakyThrows
	public static String searchGif(String term) {
		JSONObject searchResult = Tenor.getSearchResults(term, 25);
		JSONArray results;
		JSONObject selectedGif;

		if (ObjectUtils.isNotEmpty(searchResult)) {
			results = searchResult.getJSONArray("results");
			selectedGif = (JSONObject) results.get((int) Math.floor(Math.random() * results.length()));
		} else {
			System.err.println("Nothing came back");
			return null;
		}

		JSONObject selectMediaType = (JSONObject) selectedGif.getJSONArray("media").getJSONObject(0).get("gif");

		return String.valueOf(selectMediaType.get("url"));
	}
}