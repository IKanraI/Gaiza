package listener;

import java.io.FileReader;

import command.Command;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BoredResponse implements MessageCreateListener {

	@Override
	@SneakyThrows
	public void onMessageCreate(MessageCreateEvent event) {
		TextChannel channel = event.getChannel();
		Message message = event.getMessage();
		MessageAuthor author = event.getMessageAuthor();

		if (StringUtils.equalsAnyIgnoreCase(message.getContent(), "im bored", "i'm bored", "bored")) {
			message.addReaction("🚫");
			channel.sendMessage("Hi bored, I'm dad");
			Thread.sleep(1000);

			Object file = new JSONParser().parse(new FileReader("/home/kanra/projects/Gaiza/bin/Resource/boredPhrases.json"));

			JSONObject obj = (JSONObject) file;

			channel.sendMessage("<@" + author.getIdAsString() + ">"
					+ obj.get(String.valueOf(Math.round(Math.random() * obj.size()))));
			Thread.sleep(1000);

			channel.sendMessage("I'm going out to the store for some cigs and milk. Don't tell your mother");
		}

	}
}
