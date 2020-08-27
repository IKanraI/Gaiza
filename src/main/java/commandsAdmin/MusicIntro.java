package commandsAdmin;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;

public class MusicIntro 
{
	private String CakeID = "136785961622306816";
	
	public MusicIntro(DiscordApi getApi)
	{
		DiscordApi musicApi = getApi;
		
		checkIfUserJoin(musicApi);
	}
	
	public void checkIfUserJoin(DiscordApi getApi)
	{
		DiscordApi musicApi = getApi;
		
		musicApi.addServerVoiceChannelMemberJoinListener( event ->
		{
			AudioSource musicSource = new YoutubeAudioSourceBuilder()
					.setUrl("https://www.youtube.com/watch?v=otOBHT4E5hs")
					.build();
			int i = 0;
			int maxUsersInChannel = event.getChannel().getConnectedUserIds().size();
			String[] idArray = (String[]) event.getChannel().getConnectedUserIds().toArray();
			boolean inVoice = false;
			
			for (i = 0; i < maxUsersInChannel; ++i)
			{
				if (idArray[i].equals(CakeID))
				{
					inVoice = true;
					break;
				}
			}
			
			if (inVoice)
			{
				
			}
		});
	}
}
