package userInterface;

import java.io.IOException;

import payment.CartOperation;
import program.I_Receiver;

public class CF_CheckoutShoppingCartCommand implements I_Command {

	CartOperation payment;
		
		@Override
		public I_Command setConcreteCommand(I_Receiver receiver) {
			this.payment=(CartOperation) receiver;
			return this;
		}
		
		@Override
		public void execute() {

		}

		@Override
		public void execute(String infoString) {
			try {
				payment.processPayment(infoString);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
}
