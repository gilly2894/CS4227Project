package userInterface;

import media.MediaItem;
import media.PlatformChoice;
import payment.*;
import payment.Payment;
import program.I_AbstractFactory;
import program.I_Receiver;
import streaming.StreamMedia;
import users.C_AdminActions;
import users.C_StaffActions;
import users.UserClass;

public class ReceiverFactory implements I_AbstractFactory {

	@Override
	public I_Receiver getReceiver(String receiverType) {
		
		if(receiverType == null)
			return null;
		else if(receiverType.equalsIgnoreCase("C_AdminActions"))
			return new C_AdminActions();
		else if(receiverType.equalsIgnoreCase("C_StaffActions"))
			return new C_StaffActions();
		else if(receiverType.equalsIgnoreCase("AddToWallet"))
			return new AddToWallet();
		else if(receiverType.equalsIgnoreCase("StreamMedia"))
			return new StreamMedia();
		else if(receiverType.equalsIgnoreCase("PlatformChoice"))
			return new PlatformChoice();
		else if(receiverType.equalsIgnoreCase("Payment"))
			return new Payment();
		else if(receiverType.equalsIgnoreCase("CartOperation"))
			return new CartOperation();
		
		
		
		return null;
	}
	
	@Override
	public UserClass getUser(String userType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MediaItem getMediaItem(String mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_Command getCommand(String commandType) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
