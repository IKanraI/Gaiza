package listener;

import management.BotInfo;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MentionKanra {

    public MentionKanra(DiscordApi api) {
        listenForMention(api);
    }


    public void listenForMention(DiscordApi api) {
        api.addMessageCreateListener(event -> {
            boolean mentioned = false;
            for (User user : event.getMessage().getMentionedUsers()) {
                if (user.getIdAsString().equals(BotInfo.getOwnerId())) {
                    mentioned = true;
                    break;
                }
            }
            if (!mentioned || !event.getServer().get().getIdAsString().equals("256490441120284672")) {
                return;
            }


            try {
                FileWriter file = new FileWriter("/home/kanra/projects/data/hidden/mentions", true);
                StringBuilder message = new StringBuilder();
                message.append(event.getMessage().getAuthor().getDisplayName()
                        + ": " + event.getMessageContent()
                        + " :: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd HH:mm")) + "\n");
                file.write(message.toString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
            //event.getChannel().sendMessage("I'm sorry. Who ARE you?");
        });
    }
}
