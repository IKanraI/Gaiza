package UserCommands;

import Command.Command;
import Management.BotInfo;
import lombok.Getter;
import org.javacord.api.DiscordApi;

import org.javacord.api.entity.channel.TextChannel;

public class Invite extends Command {
	@Getter
	public static String help = "Returns an invite link for the bot";

	public Invite(DiscordApi api) {
		super(api);
		api.addMessageCreateListener(event ->
			inviteBot(super.getChannel()));
	}

	private void inviteBot(TextChannel channel) {
		if (!onCommand()) {
			return;
		}
		channel.sendMessage("Here is your invite! : " + BotInfo.getBotInvite());
	}
}
