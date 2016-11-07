package userInterface;

import java.io.IOException;

import users.C_StaffActions;

public class AddItemCommand implements I_Command 
{

	//receiver
	C_StaffActions staffActions;
	
	public AddItemCommand(C_StaffActions c_StaffActions)
	{
		this.staffActions = c_StaffActions;
	}

	@Override
	public void execute() 
	{
		
	}

	@Override
	public void execute(String infoString) 
	{
		try 
		{
			staffActions.addItem(infoString);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}



