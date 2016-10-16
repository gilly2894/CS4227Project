package program;

import media.MediaItem;
import users.UserClass;

public interface I_AbstractFactory {

	public UserClass getUser(String userType);
	public MediaItem getMediaItem(String mediaType);

}