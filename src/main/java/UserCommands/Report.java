package UserCommands;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;

import java.util.List;

public class Report extends Command {

    @Getter public static String help = "Send a bug or a problem with the bot to the owner";

    public Report(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
            reportCommand(api, super.getMessageAuthor(), super.getMessage(), super.getChannel(), super.getArgs()));
    }

    private void reportCommand(DiscordApi api, MessageAuthor author, Message message, TextChannel channel, List<String> args) {
        if (!onCommand()) {
            return;
        }
        if (args.size() == 0) {
            message.addReaction("\uD83D\uDEAB").join();
            channel.sendMessage("Please explain the issue you are encountering: " + getKey() + " [reason]").join();
            return;
        }

        StringBuilder msg = new StringBuilder();
        for (String s : args) {
            msg.append(s + " ");
        }
        message.addReaction("\u2705");
        api.getServerById("692871508590067823")
                .get()
                .getChannelById("704097798102057071")
                .get().asTextChannel()
                .get().sendMessage("[" + author.getDiscriminatedName() + "]: " + msg.toString());
    }
}
