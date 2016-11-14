package userInterface;

import media.AlbumClass;
import media.FilmClass;
import media.GameClass;
import media.MediaItem;
import program.I_AbstractFactory;
import program.I_Receiver;
import users.UserClass;

public class CommandFactory implements I_AbstractFactory {

	@Override
	public I_Command getCommand(String commandType) {
		
		if(commandType == null)
			return null;
		
		else if(commandType.equalsIgnoreCase("AF_AddNewUserCommand"))
			return new AF_AddNewUserCommand();
		else if(commandType.equalsIgnoreCase("AF_RemoveUserCommand"))
			return new AF_RemoveUserCommand();
		else if(commandType.equalsIgnoreCase("AF_UpdateUserCommand"))
			return new AF_UpdateUserCommand();
		
		
		else if(commandType.equalsIgnoreCase("CF_AddFundsToWalletCommand"))
			return new CF_AddFundsToWalletCommand();
		else if(commandType.equalsIgnoreCase("CF_BuyMediaItemCommand"))
			return new CF_BuyMediaItemCommand();
		else if(commandType.equalsIgnoreCase("CF_CheckoutShoppingCartCommand"))
			return new CF_CheckoutShoppingCartCommand();
		else if(commandType.equalsIgnoreCase("CF_ChoosePlatformCommand"))
			return new CF_ChoosePlatformCommand();
		else if(commandType.equalsIgnoreCase("CF_StreamMediaCommand"))
			return new CF_StreamMediaCommand();
		
		
		else if(commandType.equalsIgnoreCase("SF_AddItemCommand"))
			return new SF_AddItemCommand();
		else if(commandType.equalsIgnoreCase("SF_RemoveItemCommand"))
			return new SF_RemoveItemCommand();
		else if(commandType.equalsIgnoreCase("SF_UndoCommand"))
			return new SF_UndoCommand();
		else if(commandType.equalsIgnoreCase("SF_UpdateItemCommand"))
			return new SF_UpdateItemCommand();
		
		
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
	public I_Receiver getReceiver(String receiverType) {
		return null;
	}

}
