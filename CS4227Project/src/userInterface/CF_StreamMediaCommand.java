package userInterface;

import java.io.IOException;

import streaming.StreamMedia;

public class CF_StreamMediaCommand implements I_Command {

StreamMedia streamMedia = new StreamMedia();
	
	public CF_StreamMediaCommand(StreamMedia streamMedia) {
		this.streamMedia = streamMedia;
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
