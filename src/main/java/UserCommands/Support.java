package UserCommands;

import Command.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

public class Support extends Command {
    public static String help = "Returns the invite to the support server";

    public Support(DiscordApi api) {
        super(api);
        api.addMessageCreateListener(event ->
                requestSupport(super.getApi(), super.getChannel()));
    }

    private void requestSupport(DiscordApi api, TextChannel channel) {
        if (!onCommand()) {
            return;
        }
        channel.sendMessage("Here's the invite to the support server: https://discord.gg/Zpr5qTzKmd");
    }
}
