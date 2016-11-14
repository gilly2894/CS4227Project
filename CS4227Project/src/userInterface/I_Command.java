package userInterface;

import program.I_Receiver;

public interface I_Command {

	public I_Command setConcreteCommand(I_Receiver receiver);
	public void execute();
	public void execute(String infoString);
}
