package Command;

import Management.Keywords;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public abstract class Command {
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
            key = message.getContent().split(" ")[0];
            for (String arg : message.toString().split(" ")) {
                if (arg.equals(key)) {
                    args = new ArrayList();
                    continue;
                }
                args.add(arg);
            }
        });
    }

    public boolean onCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor messageAuthor, User user, Server server, ArrayList args) {
        api.addMessageCreateListener(event -> {
            setServer(event.getServer().get());
        });

        String serverKey = Keywords.getKey(server.getIdAsString());
        if (!(serverKey + command).equalsIgnoreCase(key)) {
            System.err.println(serverKey + " " + command + " " + message);
            return false;
        }

        return true;
    }

    private void setServer(Server server2) {
		server = server2;
		
	}

	public boolean onCommand(DiscordApi api, TextChannel channel, Message message, MessageAuthor messageAuthor) {
        return onCommand(api, channel, message, messageAuthor, this.user, this.server, (ArrayList) args) ? true : false;
    }

	public DiscordApi getApi() {
		// TODO Auto-generated method stub
		return api;
	}

	public void setCommand(String string) {
		// TODO Auto-generated method stub
		command = string;
	}

	public TextChannel getChannel() {
		// TODO Auto-generated method stub
		return channel;
	}

	public Message getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public MessageAuthor getMessageAuthor() {
		// TODO Auto-generated method stub
		return messageAuthor;
	}





}
