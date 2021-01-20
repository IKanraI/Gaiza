package UserCommands;

import Command.Command;
import Management.BotInfo;
import com.google.common.collect.Iterables;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.concurrent.TimeUnit;

public class Disconnect extends Command {
    public static String help = "Disconnects the bot from voice";

    public Disconnect(DiscordApi api) {
        super(api);

    }

    public Disconnect(DiscordApi api, AudioConnection connection) {
        super(api);
        api.addMessageCreateListener(event ->
                disconnectBot(api, connection, super.getChannel())).removeAfter(1000, TimeUnit.MILLISECONDS);
    }

    public void disconnectBot(DiscordApi api, AudioConnection connection, TextChannel channel) {
        if (!onCommand()) {
            return;
        }
        if (api.getYourself().getConnectedVoiceChannels().size() == 0) {
            channel.sendMessage("I am not connected to a voice right now");
            return;
        }
        connection.close();
    }
}
