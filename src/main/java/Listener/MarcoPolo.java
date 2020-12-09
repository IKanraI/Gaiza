package Listener;

import Management.BotInfo;
import org.javacord.api.DiscordApi;

public class MarcoPolo {

    public MarcoPolo(DiscordApi api) {
        response(api);
    }

    private void response(DiscordApi api) {
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().getIdAsString().equals("136785961622306816")) {
                return;
            }
            if (event.getMessageContent().replaceAll("[^a-zA-Z]", "").equalsIgnoreCase("marco")) {
                event.getChannel().sendMessage("Polo");
            }
        });
    }
}
