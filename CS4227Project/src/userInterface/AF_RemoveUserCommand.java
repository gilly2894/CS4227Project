package userInterface;

import java.io.IOException;

import users.C_AdminActions;

public class AF_RemoveUserCommand implements I_Command {

	//receiver
		C_AdminActions adminActions;
		
		public AF_RemoveUserCommand(C_AdminActions c_AdminActions) {
			this.adminActions = c_AdminActions;
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
