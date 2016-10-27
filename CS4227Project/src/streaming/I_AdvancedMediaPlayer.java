package streaming;

import java.io.IOException;

public interface I_AdvancedMediaPlayer {

	public void playVlc(String fileName) throws IOException;
	public void playMp4(String fileName) throws IOException;
	
}
