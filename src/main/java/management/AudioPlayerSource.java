package management;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.audio.AudioSourceBase;

public class AudioPlayerSource extends AudioSourceBase {
    private final AudioPlayer player;
    private AudioFrame lastFrame;

    /**
     * Creates a new audio source player with lavaplayer
     *
     * @param api Discordapi instance
     * @param player audio player from lavaplayer
     */
    public AudioPlayerSource(DiscordApi api, AudioPlayer player) {
        super(api);
        this.player = player;
    }

    @Override
    public byte[] getNextFrame() {
        if (lastFrame == null) {
            return null;
        }
        return applyTransformers(lastFrame.getData());
    }

    @Override
    public boolean hasFinished() {
        return false;
    }

    @Override
    public boolean hasNextFrame() {
        lastFrame = player.provide();
        return lastFrame != null;
    }

    @Override
    public AudioSource copy() {
        return new AudioPlayerSource(getApi(), player);
    }
}
