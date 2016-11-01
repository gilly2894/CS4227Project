package userInterface;

import java.io.IOException;

import users.C_AdminActions;

public class AF_AddNewUserCommand implements I_Command {

	//receiver
	C_AdminActions adminActions;
	
	public AF_AddNewUserCommand(C_AdminActions c_AdminActions) {
		this.adminActions = c_AdminActions;
	}

	@Override
	public void execute() {
		// null op method
	}

	@Override
	public void execute(String infoString) {
		try {
			adminActions.addNewUser(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
