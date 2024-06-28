package fixThese;

import command.Command;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.entity.channel.TextChannel;

import java.util.concurrent.TimeUnit;

public class Disconnect {
    public static String help = "Disconnects the bot from voice";

    public void disconnectBot(DiscordApi api, AudioConnection connection, TextChannel channel) {
        if (api.getYourself().getConnectedVoiceChannels().size() == 0) {
            channel.sendMessage("I am not connected to a voice right now");
            return;
        }
        connection.close();
    }
}
