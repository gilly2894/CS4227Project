package userInterface;

import java.io.IOException;

import program.I_Receiver;
import users.C_AdminActions;

public class AF_RemoveUserCommand implements I_Command {

	//receiver
		C_AdminActions adminActions;
		
		@Override
		public I_Command setConcreteCommand(I_Receiver receiver) {
			this.adminActions = (C_AdminActions) receiver;
			return this;
		}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String infoString) {
		try {
			adminActions.removeUser(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
