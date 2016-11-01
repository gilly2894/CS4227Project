package payment;
import media.MediaItem;
import database.Database;
public class CustomerDecorator extends ReceiptDecorator {
	
	public CustomerDecorator(I_Receipt decoratedReceipt)
	{
		super(decoratedReceipt);
	}
	
	public String PrintReceipt(String media)
	{
		String customerGreeting= "Thank you for shopping with us. You're a delightful customer.";
		
		Database database= Database.getInstance();
		MediaItem item= database.getMediaItemByName(media);
		
		
		
		String total= customerGreeting + decoratedReceipt.PrintReceipt(media);
		return total;
	}

}