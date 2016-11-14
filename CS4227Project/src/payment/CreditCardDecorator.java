package payment;

import java.io.FileNotFoundException;

public class CreditCardDecorator extends ReceiptDecorator {

	public CreditCardDecorator(I_Receipt decoratedReceipt) {
		super(decoratedReceipt);
		// TODO Auto-generated constructor stub
	}
	
	public String PrintReceipt(String media) throws FileNotFoundException
	{
		String credit= "You successfully paid by credit card \n";
		return decoratedReceipt.PrintReceipt(media) + credit; 
	}

}
