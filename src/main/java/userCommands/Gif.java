package userCommands;

import java.awt.Color;

import client.Tenor;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.json.JSONArray;
import org.json.JSONObject;

import util.BotInfo;
import util.GaizaUtil;

public class Gif implements SlashCommandCreateListener {
	@Getter
	public static String help = "Searches for a specified gif. Returns a random results from the search. Usage [prefix]gif [query]";
	private final String command = "gif";
	private static final int resultLimit = 13;

	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent event) {
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();

		if (!StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
			return;

		String term = GaizaUtil.getPassedArgument(interaction.getArguments());

		interaction.createImmediateResponder()
				.addEmbed(userGifSearch(term, interaction.getUser()))
				.respond()
				.exceptionally(e -> {
					interaction.createImmediateResponder()
							.setContent("Could not find the search term")
							.respond();
					return null;
				});
	}

	private EmbedBuilder userGifSearch(String searchTerm, User author) {
		String term = searchTerm.replaceAll(" ", "+");

		return buildEmbed(searchGif(term), author);
	}

	public static String searchGif(String term) {
		JSONObject searchResult = Tenor.getSearchResults(term, resultLimit);
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

	public EmbedBuilder buildEmbed(String url, User author) {
		return new EmbedBuilder()
				.setTitle("A gif for your viewing")
				.setAuthor(author.getName(), author.getAvatar().getUrl().toString(), author.getAvatar())
				.setColor(Color.magenta)
				.setImage(url)
				.setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage())
				.setTimestampToNow();
	}
}