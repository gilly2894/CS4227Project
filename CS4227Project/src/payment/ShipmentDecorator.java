package payment;

import java.io.FileNotFoundException;

public class ShipmentDecorator extends ReceiptDecorator {

	public ShipmentDecorator(I_Receipt decoratedReceipt) {
		super(decoratedReceipt);
		// TODO Auto-generated constructor stub
	}
	
	public String PrintReceipt(String media) throws FileNotFoundException
	{
		String postage= "Your item(s) will be shipped to your address very soon \n";
		return decoratedReceipt.PrintReceipt(media) + postage; 
		 
	}

}
