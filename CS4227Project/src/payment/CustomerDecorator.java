package payment;

import java.io.FileNotFoundException;

public class CustomerDecorator extends ReceiptDecorator {
	
	public CustomerDecorator(I_Receipt decoratedReceipt)
	{
		super(decoratedReceipt);
	}
	
	public String PrintReceipt(String media) throws FileNotFoundException
	{
		String customerGreeting= "Thank you for shopping with us. You're a delightful customer.\n";
		return customerGreeting + decoratedReceipt.PrintReceipt(media);
		
	}

}