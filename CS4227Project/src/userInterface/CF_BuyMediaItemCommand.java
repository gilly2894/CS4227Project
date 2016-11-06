package userInterface;

import java.io.IOException;

import payment.Payment;

public class CF_BuyMediaItemCommand implements I_Command {

Payment payment = new Payment();
	
	public CF_BuyMediaItemCommand(Payment payment) {
		this.payment = payment;
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
