package payment;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import database.Database;
import media.MediaItem;
import media.ShoppingCart;
import users.CustomerClass;
import users.UserClass;

public class CartReceipt implements I_Receipt {

	private Database database= Database.getInstance();

	@Override
	public String PrintReceipt(String username) throws FileNotFoundException {
	String checkoutItems= "You have successfully checked out the following items: \n";
	UserClass user=database.getUserByName(username);
	CustomerClass customer = ((CustomerClass)user);
	HashMap <MediaItem, String> cartList= customer.getCart().getCartList();
	for(Map.Entry<MediaItem, String> entry : cartList.entrySet())
	{
		checkoutItems+= entry.getKey().getTitle()+ ": €" + entry.getKey().getPrice() + " x" + entry.getValue() + "\n";
	}
		checkoutItems+= "Total: €" + customer.getCart().getTotalCost() + "\n";
	
		return checkoutItems;
	}
}