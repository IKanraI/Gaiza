package UserCommands;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Report extends Command {

    @Getter public static String help = "Send a bug or a problem with the bot to the owner";

    public Report(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event -> {
            reportCommand(api, super.getMessageAuthor(), super.getChannel(), super.getArgs());
        });
    }

    private void reportCommand(DiscordApi api, MessageAuthor author, TextChannel channel, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (args.size() == 0) {
            channel.sendMessage("Please explain the issue you are encountering");
            return;
        }

        StringBuilder msg = new StringBuilder();
        for (String s : args) {
            msg.append(s + " ");
        }

        api.getServerById("692871508590067823")
                .get()
                .getChannelById("704097798102057071")
                .get().asTextChannel()
                .get().sendMessage("[" + author.getDiscriminatedName() + "]: " + msg.toString());
    }
}
