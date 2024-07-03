package userCommands;

import lombok.Getter;
import lombok.SneakyThrows;
import util.BotInfo;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import util.GaizaUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Define implements SlashCommandCreateListener {
	@Getter
	public static String help = "Search urban dictionary. Usage [prefix]define [term]";
	private final String command = "define";
	private final String urbanDictionaryUrl = "https://www.urbandictionary.com/define.php?term=";
	private List<String> definition;

	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent event) {
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		String term = "";

		if (!StringUtils.equalsIgnoreCase(interaction.getCommandName(), command))
			return;

		term = GaizaUtil.getPassedArgument(interaction.getArguments());

		interaction.createImmediateResponder()
				.addEmbed(urbanSearch(term))
				.respond()
				.exceptionally(e -> {
					interaction.createImmediateResponder()
							.setContent(definition.get(0) + " | " + "Credit: Contributed " + definition.get(2))
							.respond();

					return null;
				});
	}

	@SneakyThrows
	private EmbedBuilder urbanSearch(String requestedTerm) {
		String term = requestedTerm.replaceAll(" ", "+");

		Elements css = Jsoup.connect(urbanDictionaryUrl + term)
				.followRedirects(true)
				.ignoreHttpErrors(true)
				.userAgent("Mozilla/5.0")
				.get()
				.select("div.meaning");

		definition = new ArrayList<>();
		definition.add(css.get(0).text());
		definition.add(css.parents().select("div.example").get(0).text());
		definition.add(css.parents().select("div.contributor").get(0).text());

		return buildEmbed(term.replaceAll("\\+", " ").trim(),urbanDictionaryUrl + term, definition);

	}

	private EmbedBuilder buildEmbed(String message, String link, List<String> def) {
		return new EmbedBuilder()
				.setTitle(message.substring(0, 1).toUpperCase() + message.substring(1))
				.setColor(Color.magenta)
				.addField("Definition", def.get(0))
				.addField("Example:", def.get(1))
				.addField("Credit:", "Contributed " + def.get(2))
				.addField("Link", link)
				.setTimestampToNow()
				.setFooter(BotInfo.getInstance().getBotName(), BotInfo.getInstance().getBotImage());
	}
}