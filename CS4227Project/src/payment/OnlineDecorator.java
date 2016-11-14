package payment;

import java.io.FileNotFoundException;

public class OnlineDecorator extends ReceiptDecorator {

	public OnlineDecorator(I_Receipt decoratedReceipt) {
		super(decoratedReceipt);
		// TODO Auto-generated constructor stub
	}
	
	public String PrintReceipt(String media) throws FileNotFoundException
	{
		String repository= "Your item(s) is currently available in your online repository \n";
		return decoratedReceipt.PrintReceipt(media) + repository; 
	}
	
	/*public String PrintReceipt()
	{
		String repository= "Your item(s) is currently available in your online repository \n";
		return decoratedReceipt.PrintReceipt(media) + repository; 
	}*/

}
