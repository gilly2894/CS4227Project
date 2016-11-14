package userInterface;

import java.io.IOException;

import payment.AddToWallet;

public class CF_AddFundsToWalletCommand implements I_Command {

	AddToWallet addToWallet = new AddToWallet();
	
	public CF_AddFundsToWalletCommand(AddToWallet addToWallet) {
		this.addToWallet = addToWallet;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String infoString) {
		try {
			addToWallet.wallet(infoString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
