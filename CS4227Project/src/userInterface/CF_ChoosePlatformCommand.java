package userInterface;

import java.io.IOException;

import media.PlatformChoice;

public class CF_ChoosePlatformCommand implements I_Command {

	PlatformChoice pChoice= new PlatformChoice();
	
	public CF_ChoosePlatformCommand(PlatformChoice pChoice)
	{
		this.pChoice=pChoice;
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
