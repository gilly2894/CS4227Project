package payment;
public class CustomerDecorator extends ReceiptDecorator {
	
	public CustomerDecorator(I_Receipt decoratedReceipt)
	{
		super(decoratedReceipt);
	}
	
	public String PrintReceipt(String media)
	{
		String customerGreeting= "Thank you for shopping with us. You're a delightful customer.";
		
		
		
		
		
		
		String total= customerGreeting + decoratedReceipt.PrintReceipt(media);
		return total;
	}

}