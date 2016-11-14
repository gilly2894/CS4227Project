package payment;

import java.io.FileNotFoundException;

public class StaffDecorator extends ReceiptDecorator {
	public StaffDecorator(I_Receipt decoratedReceipt)
	{
		super(decoratedReceipt);
	}
	
	public String PrintReceipt(String media) throws FileNotFoundException
	{
		String staffGreeting= "You've successfully ordered such items to be supplied to you.\n";
		
		String total= staffGreeting + decoratedReceipt.PrintReceipt(media);
		return total;
	}


}
