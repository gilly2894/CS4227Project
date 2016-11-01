package payment;

public class StaffDecorator extends ReceiptDecorator {
	public StaffDecorator(I_Receipt decoratedReceipt)
	{
		super(decoratedReceipt);
	}
	
	public String PrintReceipt()
	{
		String staffGreeting= "You've successfully ordered such items to be supplied to you.";
		
		String total= staffGreeting + decoratedReceipt.PrintReceipt();
		return total;
	}


}