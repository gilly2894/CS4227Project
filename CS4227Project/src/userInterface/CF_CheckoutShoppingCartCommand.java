package userInterface;

import java.io.IOException;

import payment.CartCheckout;
import program.I_Receiver;

public class CF_CheckoutShoppingCartCommand implements I_Command {

	CartCheckout payment;
		
		@Override
		public I_Command setConcreteCommand(I_Receiver receiver) {
			this.payment=(CartCheckout) receiver;
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
