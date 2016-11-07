package userInterface;

import java.io.IOException;

import users.C_StaffActions;

public class UpdateItemCommand implements I_Command 
{

	C_StaffActions staffActions;
	
	public UpdateItemCommand(C_StaffActions c_StaffActions) 
	{
		this.staffActions = c_StaffActions;
	}
	
	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String infoString) 
	{
		try 
		{
			staffActions.updateItem(infoString);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

}
