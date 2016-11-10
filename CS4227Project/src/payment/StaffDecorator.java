package payment;

public class StaffDecorator extends ReceiptDecorator {
	public StaffDecorator(I_Receipt decoratedReceipt)
	{
		super(decoratedReceipt);
	}
	
	public String PrintReceipt(String media)
	{
		String staffGreeting= "You've successfully ordered such items to be supplied to you.\n";
		
		String total= staffGreeting + decoratedReceipt.PrintReceipt(media);
		return total;
	}


}
