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
        api.addMessageCreateListener(event -> {
            reportCommand(api, super.getChannel(), super.getMessage(), super.getMessageAuthor(), super.getArgs());
        });
    }

    private void reportCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor messageAuthor, List<String> args) {
        if (!onCommand(api, getChannel(), getMessage(), getMessageAuthor(), getArgs())) {
            return;
        }
        if (args.size() == 0) {
            channel.sendMessage("Please explain the issue you are encountering");
            return;
        }
        api.getServerById("692871508590067823")
                .get()
                .getChannelById("704097798102057071")
                .get().asTextChannel()
                .get().sendMessage(args.toString());
        //remote.sendMessage("meow");
    }
}
