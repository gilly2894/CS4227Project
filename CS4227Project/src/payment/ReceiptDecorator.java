package payment;

public class ReceiptDecorator implements I_Receipt {

	protected I_Receipt decoratedReceipt;
	
	public ReceiptDecorator(I_Receipt decoratedReceipt)
	{
		this.decoratedReceipt= decoratedReceipt;
	}
	@Override
	public String PrintReceipt() {
		String receipt="";
		receipt= decoratedReceipt.PrintReceipt();
		 return receipt;
	}

}
