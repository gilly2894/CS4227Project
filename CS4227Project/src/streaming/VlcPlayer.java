package streaming;

import java.io.IOException;

public class VlcPlayer implements I_AdvancedMediaPlayer {
	
	@Override
	public void playVlc(String fileName) throws IOException {
		System.out.println("Playing vlc file. Name: "+ fileName);	
		
		Runtime.getRuntime().exec("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe \"C:\\Users\\shaun\\git\\CS4227Project\\CS4227Project\\media\\" + fileName + "\"");
	}																							

	@Override
	public void playMp4(String fileName) {
		//do nothing
	}

}
