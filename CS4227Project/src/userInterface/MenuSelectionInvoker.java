package userInterface;

public class MenuSelectionInvoker {
	private I_Command command;
	
	public void setCommand(I_Command command)
	{
		this.command = command;
	}
	
	public void optionSelected()
	{
		command.execute();
	}
	
	public void optionSelectedWithStringParam(String choice)
	{
		command.execute(choice);
	}

}
