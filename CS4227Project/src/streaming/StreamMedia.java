package streaming;

import java.io.IOException;

import database.Database;
import media.MediaItem;

public class StreamMedia {
	
	Database databaseInstance = Database.getInstance();
	public void stream(String mediaName) throws IOException {
		AudioPlayer player = new AudioPlayer();
		MediaItem mediaItem = databaseInstance.getMediaItemByName(mediaName);
		String format = mediaItem.getFormat();
		String filmID = mediaItem.getMediaID();
		String fileToPlay = filmID + format;
		player.play(format, fileToPlay);
	}
}