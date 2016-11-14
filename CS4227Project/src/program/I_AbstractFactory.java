package program;

import media.MediaItem;
import userInterface.I_Command;
import users.UserClass;

public interface I_AbstractFactory {

	public UserClass getUser(String userType);
	public MediaItem getMediaItem(String mediaType);
	public I_Command getCommand(String commandType);
	public I_Receiver getReceiver(String receiverType);

}