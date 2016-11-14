package payment;

import java.io.FileNotFoundException;

public class ReceiptDecorator implements I_Receipt {

	protected I_Receipt decoratedReceipt;
	
	public ReceiptDecorator(I_Receipt decoratedReceipt) //Object to be decorated
	{
		this.decoratedReceipt= decoratedReceipt;
	}
	@Override
	public String PrintReceipt(String media) throws FileNotFoundException {
		String receipt="";
		receipt= decoratedReceipt.PrintReceipt(media);
		 return receipt;
		 
		 
		 
		 
		 
	}

}
