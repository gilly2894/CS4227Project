package userInterface;

import java.io.IOException;

import program.I_Receiver;
import users.C_AdminActions;

public class AF_UpdateUserCommand implements I_Command {

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
			adminActions.updateUser(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
