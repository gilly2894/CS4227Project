package streaming;

import java.io.IOException;

public class Mp4Player implements I_AdvancedMediaPlayer {

	@Override
	public void playVlc(String fileName) {
		//do nothing
	}

	@Override
	public void playMp4(String fileName) throws IOException {
		System.out.println("Playing mp4 file. Name: "+ fileName);	
		
		Runtime.getRuntime().exec("C:\\Program Files\\Windows Media Player\\wmplayer.exe \"C:\\Users\\shaun\\git\\CS4227Project\\CS4227Project\\media\\" + fileName + "\"");
	}
	
}
