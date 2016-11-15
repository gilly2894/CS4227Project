package userInterface;

import payment.CartOperation;
import program.I_Receiver;

public class CF_ChangeQuantityCommand implements I_Command {

	CartOperation CartOp;
	@Override
	public I_Command setConcreteCommand(I_Receiver receiver) {
		CartOp = (CartOperation) receiver;
		return this;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(String infoString) {
		try {
			CartOp.updateQty(infoString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
