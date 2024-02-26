package UserCommands;

import Command.Command;
import Management.AudioPlayerSource;
import Management.BotInfo;
import com.google.common.collect.Iterables;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.Getter;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.audio.AudioSourceBase;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class Sound extends Command {

    @Getter public static String help = "Include something in this later";

    public Sound(DiscordApi api) {
        super(api);

        api.addMessageCreateListener(event ->
                playSound(api, super.getChannel(), super.getMessageAuthor(), super.getArgs()));
    }

    private void playSound(DiscordApi api, TextChannel channel, MessageAuthor author, List<String> args) {
        if (!onCommand() || !author.asUser().get().getIdAsString().equals(BotInfo.getOwnerId())) {
            return;
        }
        if (author.asUser().get().getConnectedVoiceChannels().size() == 0) {
            channel.sendMessage("User must be connected to a voice channel");
            return;
        }
        if (args.size() == 0) {
            channel.sendMessage("Please input a sound name");
            return;
        }
        if (args.get(0).equals("list")) {
            channel.sendMessage("Put list here");
            return;
        }

        String vc = Iterables.get(author.asUser().get().getConnectedVoiceChannels(), 0).getIdAsString();
        api.getVoiceChannelById(vc).get().asServerVoiceChannel().get().connect().thenAccept(connection -> {

            AudioPlayerManager manager = new DefaultAudioPlayerManager();
            manager.registerSourceManager(new LocalAudioSourceManager());
            AudioPlayer player = manager.createPlayer();

            AudioSource source = new AudioPlayerSource(api, player);
            connection.setAudioSource(source);
            player.setVolume(1000);

            manager.loadItem("/home/kanra/projects/Gaiza/bin/Sound/Eargasm.mp3", new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {
                    player.playTrack(track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    for (AudioTrack track : playlist.getTracks()) {
                        player.playTrack(track);
                    }
                }

                @Override
                public void noMatches() {
                    channel.sendMessage("Can't do that :(");
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    channel.sendMessage("Everything blew up :(");
                }
            });
            api.addMessageCreateListener(event -> {
                if (api.getYourself().getConnectedVoiceChannels().size() == 0 && event.getMessageContent().equalsIgnoreCase("!disconnect")) {
                    event.getChannel().sendMessage("I am not in a voice currently");
                    return;
                }
                if (event.getMessageContent().equalsIgnoreCase("!disconnect")) {
                    connection.close();
                }
            }).removeAfter(5, TimeUnit.MINUTES);
        }).exceptionally(e -> {
            channel.sendMessage("Something went wrong");
            e.printStackTrace();
            return null;
        });
    }
}
