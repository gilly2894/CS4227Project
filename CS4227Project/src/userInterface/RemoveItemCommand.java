package userInterface;

import java.io.IOException;

import users.C_StaffActions;

public class RemoveItemCommand implements I_Command  
{
	//receiver
	C_StaffActions staffActions;
	
	public RemoveItemCommand(C_StaffActions c_StaffActions) {
		this.staffActions = c_StaffActions;
	}


public void execute() 
{

}


public void execute(String infoString) 
{
	try
	{
		staffActions.removeItem(infoString);
	}
	catch (IOException e) 
	{
		e.printStackTrace();
	}
}
}




