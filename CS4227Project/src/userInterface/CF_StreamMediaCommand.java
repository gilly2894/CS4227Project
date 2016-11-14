package userInterface;

import java.io.IOException;

import program.I_Receiver;
import streaming.StreamMedia;

public class CF_StreamMediaCommand implements I_Command {

StreamMedia streamMedia = new StreamMedia();
	
	
	@Override
	public I_Command setConcreteCommand(I_Receiver receiver) {
		this.streamMedia = (StreamMedia) receiver;
		return this;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String infoString) {
		try {
			streamMedia.stream(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
