package userInterface;

import java.io.IOException;

import payment.CartCheckout;

public class CF_CheckoutShoppingCartCommand implements I_Command {

	CartCheckout payment = new CartCheckout();
		
		public CF_CheckoutShoppingCartCommand(CartCheckout payment) {
			this.payment = payment;
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
