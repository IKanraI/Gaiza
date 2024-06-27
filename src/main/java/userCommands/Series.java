package userCommands;

import command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;

public class Series extends Command {
    @Getter public static String help = "Include something in this later";

    public Series(DiscordApi api) {
        super(api);
    }
}
