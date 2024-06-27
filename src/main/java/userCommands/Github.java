package userCommands;

import command.Command;
import management.BotInfo;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

public class Github extends Command {
    @Getter
    public static String help = "Returns the repo of the bot";

    public Github(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
            inviteBot(super.getChannel()));
    }

    private void inviteBot(TextChannel channel) {
        if (!onCommand()) {
            return;
        }
        channel.sendMessage("Here is the repo! : " + BotInfo.getBotRepo());
    }
}
