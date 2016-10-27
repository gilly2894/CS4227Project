package streaming;

import java.io.IOException;

public class AudioPlayer implements I_MediaPlayer {

	MediaAdapter mediaAdapter; 

	@Override
	public void play(String audioType, String fileName) throws IOException  
	{		
		//inbuilt support to play mp3 music files
		if(audioType.equalsIgnoreCase(".mp3"))
		{
			System.out.println("Playing mp3 file. Name: " + fileName);
			
			Runtime.getRuntime().exec("C:\\Program Files\\Windows Media Player\\wmplayer.exe \"C:\\Users\\shaun\\git\\CS4227Project\\CS4227Project\\media\\" + fileName + "\"");
		} 
	      
		//mediaAdapter is providing support to play other file formats
	    else if(audioType.equalsIgnoreCase(".vlc") || audioType.equalsIgnoreCase(".mp4"))
	    {
	         mediaAdapter = new MediaAdapter(audioType);
	         mediaAdapter.play(audioType, fileName);
	    }
	      
	    else
	    {
	    	System.out.println("Invalid media. " + audioType + " format not supported");
	    }
	}   
	
}
