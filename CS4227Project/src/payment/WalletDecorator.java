package payment;

public class WalletDecorator extends ReceiptDecorator {

	public WalletDecorator(I_Receipt decoratedReceipt) {
		super(decoratedReceipt);
		// TODO Auto-generated constructor stub
	}
	
	public String PrintReceipt(String media)
	{
		String wallet= "You successfully paid by wallet \n";
		return decoratedReceipt.PrintReceipt(media) + wallet; 
	}

}
