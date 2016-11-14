package userInterface;

import java.io.IOException;

import program.I_Receiver;
import users.C_StaffActions;

public class SF_UndoCommand implements I_Command
{
	

		C_StaffActions staffActions;
		
		@Override
		public I_Command setConcreteCommand(I_Receiver receiver) {
			this.staffActions = (C_StaffActions) receiver;
			return this;
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
				staffActions.updateItemFromMemento(infoString);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

}
