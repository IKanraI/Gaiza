package UserCommands;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Command.Command;
import lombok.Getter;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Management.BotInfo;
import Management.Keywords;

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
		Elements css = Jsoup.connect("https://tenor.com/search/" + term)
				.followRedirects(true)
				.ignoreHttpErrors(true)
				.userAgent("Chrome/74.0.3729.157")
				.get()
				.select("Figure.GifListItem")
				.select("a[href]");

		if (css.isEmpty()) {
			return null;
		}

		List<String> validElements = new ArrayList();
		for (Element e : css) {
			for (Element a : e.children()) {
				if (a.className().equals("Gif")) {
					validElements.add(a.parent().attr("href"));
				}
			}
		}

		String searchUrl = "";
		int select = (int) Math.floor(Math.random() * (validElements.size() > 30 ? validElements.size() / 2 : validElements.size()));
		searchUrl = "https://tenor.com" + validElements.get(select);

		if (searchUrl.equalsIgnoreCase("https://tenor.com/gif-maker?utm_source=search-page&utm_medium=internal&utm_campaign=gif-maker-entrypoints")) {
			if (select == 0) {
				select += 1;
			} else if (select == validElements.size()) {
				select -= 1;
			} else {
				select += 1;
			}
			searchUrl = "https://tenor.com" + validElements.get(select);
		}

		Elements search = Jsoup.connect(searchUrl)
				.followRedirects(true)
				.ignoreHttpErrors(true)
				.userAgent("Chrome/74.0.3729.157")
				.get()
				.select("div.Gif")
				.get(0)
				.select("img");

		return search.attr("abs:src");
	}
}