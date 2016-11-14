package media;

import program.I_AbstractFactory;
import program.I_Receiver;
import userInterface.I_Command;
import users.UserClass;

public class MediaFactory implements I_AbstractFactory
{

	@Override
	public UserClass getUser(String userType)
	{
		return null;
	}
	
	@Override
	public MediaItem getMediaItem(String mediaType)
	{
		if(mediaType == null)
			return null;
		else if(mediaType.equalsIgnoreCase("FILM"))
			return new FilmClass();
		
		else if(mediaType.equalsIgnoreCase("ALBUM"))
			return new AlbumClass();

		else if(mediaType.equalsIgnoreCase("GAME"))
			return new GameClass();
		
		
		return null;
	}

	@Override
	public I_Command getCommand(String commandType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_Receiver getReceiver(String receiverType) {
		// TODO Auto-generated method stub
		return null;
	}
}
