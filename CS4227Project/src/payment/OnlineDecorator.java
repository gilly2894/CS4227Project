package payment;

public class OnlineDecorator extends ReceiptDecorator {

	public OnlineDecorator(I_Receipt decoratedReceipt) {
		super(decoratedReceipt);
		// TODO Auto-generated constructor stub
	}
	
	public String PrintReceipt(String media)
	{
		String repository= "Your item(s) is currently available in your online repository \n";
		return decoratedReceipt.PrintReceipt(media) + repository; 
	}

}
