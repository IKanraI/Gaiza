package listener;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MarcoPolo implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (!event.getMessageAuthor().getIdAsString().equals("136785961622306816")) {
            return;
        }
        if (event.getMessageContent().replaceAll("[^a-zA-Z]", "").equalsIgnoreCase("marco")) {
            event.getChannel().sendMessage("Polo");
        }
    }
}
