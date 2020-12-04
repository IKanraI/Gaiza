package UserCommands;

import Command.Command;
import lombok.Getter;
import org.javacord.api.DiscordApi;

public class Ranks extends Command {

    @Getter
    public static String help = "Returns the leaderboards for UWU fines. Usage [prefix]ranks";

    public Ranks(DiscordApi api) {
        super(api);
    }
}
