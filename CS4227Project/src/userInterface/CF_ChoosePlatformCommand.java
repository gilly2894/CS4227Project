package userInterface;

import java.io.IOException;

import media.PlatformChoice;
import program.I_Receiver;

public class CF_ChoosePlatformCommand implements I_Command {

	PlatformChoice pChoice= new PlatformChoice();
	
	
	@Override
	public I_Command setConcreteCommand(I_Receiver receiver) {
		this.pChoice=(PlatformChoice) receiver;
		return this;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(String infoString) {
		try {
			pChoice.decidePlatform(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	

}
