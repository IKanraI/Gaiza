package listener;

import java.io.FileReader;

import command.Command;
import lombok.SneakyThrows;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BoredResponse extends Command {
	
	public BoredResponse(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
				respondToBored(super.getChannel(), super.getServer(), super.getMessage(), super.getMessageAuthor()));
	}

	@SneakyThrows
	public void respondToBored(TextChannel channel, Server server, Message message, MessageAuthor author) {
		if (message.getContent().equalsIgnoreCase("im bored") || message.getContent().equalsIgnoreCase("i'm bored") || message.getContent().equalsIgnoreCase("bored")) {
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
