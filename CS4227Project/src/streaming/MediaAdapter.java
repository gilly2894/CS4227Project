package streaming;

import java.io.IOException;

public class MediaAdapter implements I_MediaPlayer {

	I_AdvancedMediaPlayer advancedMediaPlayer;
	
	public MediaAdapter(String audioType)
	{
		if(audioType.equalsIgnoreCase(".vlc") )
		{
			advancedMediaPlayer = new VlcPlayer();			     
	    }
		else if (audioType.equalsIgnoreCase(".mp4"))
		{
			advancedMediaPlayer = new Mp4Player();
	    }	
	}

	@Override
	public void play(String audioType, String fileName) throws IOException 
	{
		if(audioType.equalsIgnoreCase(".vlc"))
		{
			advancedMediaPlayer.playVlc(fileName);
	    }
	    else if(audioType.equalsIgnoreCase(".mp4"))
	    {
	    	advancedMediaPlayer.playMp4(fileName);
	    }
	}
	
}
