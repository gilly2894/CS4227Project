package userInterface;

import java.io.IOException;

import payment.AddToWallet;
import program.I_Receiver;

public class CF_AddFundsToWalletCommand implements I_Command {

	AddToWallet addToWallet = new AddToWallet();
	
	@Override
	public I_Command setConcreteCommand(I_Receiver receiver) {
		this.addToWallet = (AddToWallet) receiver;
		return this;
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
