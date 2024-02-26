package Command;

import Database.InitDatabase;
import Management.BotInfo;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Command {
    private DiscordApi api;
    private TextChannel channel;
    private Message message;
    private MessageAuthor messageAuthor;
    private User user;
    private Server server;
    private String key;
    private String command;
    private List<String> args;

    public Command(DiscordApi api) {
        this.api = api;

        api.addMessageCreateListener(event -> {
            message = event.getMessage();
            channel = event.getChannel();
            server = event.getServer().get();
            messageAuthor = event.getMessageAuthor();
            key = message.getContent().split("\\s")[0];
            args = new ArrayList();
        });
    }

    public boolean onCommand(DiscordApi api, Message message,  Server server, ArrayList args) {
        command = Thread.currentThread().getStackTrace()[3].getClassName().split("\\.")[1];
        api.addMessageCreateListener(event -> {
            setServer(event.getServer().get());
        });

        if (!(InitDatabase.getData().get(server.getIdAsString()).getPrefix() + command).equalsIgnoreCase(key)) {
            return false;
        }
        if (!messageAuthor.isRegularUser() || channel.getIdAsString().equals("519563130603307018")) {
            return false;
        }

        for (String arg : message.getContent().split("\\s")) {
            if (arg.equals(key)) {
                continue;
            }
            args.add(arg);
        }
       return true;
    }

	public boolean onCommand() {
        return onCommand(this.api, this.message, this.server, (ArrayList) this.args);
    }

    public boolean onAdminCommand() {
            return (messageAuthor.isServerAdmin() || messageAuthor.getIdAsString().equals(BotInfo.getOwnerId()))
                    && onCommand(this.api, this.message, this.server, (ArrayList) this.args);
    }

    public boolean isIgnoredChannel() {
       return channel.getIdAsString().equals("519563130603307018") || channel.getIdAsString().equals("794780598895902811");
    }
}
