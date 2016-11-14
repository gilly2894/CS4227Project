package users;

import program.I_AbstractFactory;
import program.I_Receiver;
import userInterface.I_Command;
import media.MediaItem;

public class UserFactory implements I_AbstractFactory{
	
	@Override
	public UserClass getUser(String userType)
	{
		if(userType == null)
			return null;
		else if(userType.equalsIgnoreCase("ADMIN"))
			return new AdminClass();
		else if(userType.equalsIgnoreCase("CUSTOMER"))
			return new CustomerClass();
		else if(userType.equalsIgnoreCase("STAFF"))
			return new StaffClass();
		
		return null;
	}
	
	@Override
	public MediaItem getMediaItem(String mediaItemType)
	{
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