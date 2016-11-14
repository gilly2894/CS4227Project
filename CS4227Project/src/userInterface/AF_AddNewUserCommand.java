package userInterface;

import java.io.IOException;

import program.I_Receiver;
import users.C_AdminActions;

public class AF_AddNewUserCommand implements I_Command {

	//receiver
	C_AdminActions adminActions;
	
	
	@Override
	public I_Command setConcreteCommand(I_Receiver receiver) {
		this.adminActions = (C_AdminActions) receiver;
		return this;
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
