package payment;
import java.util.HashMap;

import database.Database;
import media.MediaItem;

public class ReceiptA implements I_Receipt {

	private Database database= Database.getInstance();
	@Override
	public String PrintReceipt(String media) {
		
		return "You paid €" + database.getMediaItemByName(media).getPrice() + " for " + media + "\n";
		
	}
	
}
