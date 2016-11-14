package userInterface;

import java.io.IOException;

import payment.Payment;
import program.I_Receiver;

public class CF_BuyMediaItemCommand implements I_Command {

Payment payment = new Payment();
	
	
	@Override
	public I_Command setConcreteCommand(I_Receiver receiver) {
		this.payment = (Payment) receiver;
		return this;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String infoString) {
		try {
			payment.processPayment(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
