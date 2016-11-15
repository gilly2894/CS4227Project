package payment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import database.Database;
import interceptor.contextObjects.PaymentInfoContext;
import interceptor.dispatchers.ClientRequestDispatcher;
import media.GameClass;
import media.MediaItem;
import media.PlatformChoice;
import media.ShoppingCart;
import program.I_Receiver;
import userInterface.UserInterfaceMenu;
import users.CustomerClass;
import users.UserClass;

public class CartOperation implements I_Receiver {
	
	Database database = Database.getInstance();

	
	public void updateQty(String username_mediaName_qty) throws Exception
	{
		String[] arr = username_mediaName_qty.split(",");
		String username = arr[0];
		String mediaName = arr[1];
		String qty = arr[2];
		UserClass customer= database.getUserByName(username);
		CustomerClass cust= (CustomerClass)customer; //Casting from Userclass to CustomerClass
		ShoppingCart cart = cust.getCart();
		MediaItem m = database.getMediaItemByName(mediaName);
		if(!cart.checkIfItemExists(m)){
			cart.addItem(m,qty);
		}
		if(Integer.parseInt(qty) == 0)
			cart.removeItem(m);
		else
			cart.updateQuantity(m, qty);
		database.updateShoppingCartFile(Integer.toString(cust.getUserID()),
				database.getMediaItemByName(mediaName).getMediaID(), qty);
		
	}
	
	public void clearCart(String username)
	{
		UserClass customer= database.getUserByName(username);
		CustomerClass cust= (CustomerClass)customer; 
		cust.getCart().clearCart();
		try {
			database.clearUsersCart(Integer.toString(cust.getUserID()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//TODO! should take out Credit card functionality altogether and just us wallet
	public void processPayment(String username_cartID_purchaseType_paymentOption) throws IOException
	{
		String[] arr = username_cartID_purchaseType_paymentOption.split(",");
		String username = arr[0];
		String cartID = arr[1];
		String purchaseType = arr[2];
		String paymentOption = arr[3];
		I_Receipt receipt= new CartReceipt();	//receipt object created
		
		UserClass customer= database.getUserByName(username);
		CustomerClass cust= (CustomerClass)customer; //Casting from Userclass to CustomerClass
		ShoppingCart cart = cust.getCart();
		
		double oldBalance= Double.parseDouble(cust.getBalance());
		double price = cart.getTotalCost();
		double newBalance= oldBalance-price;
		boolean printReceipt = false;
		if((newBalance>=0.0) || paymentOption.equals("Credit Card")) //both cases mean their payment is valid
		{
					// TRYS TO ADD TO ONLINE REPOSITORY
					receipt= new CustomerDecorator(new ShipmentDecorator(receipt)); //receipt decorated so that a customer has bought an item to be shipped to them
				
				if(paymentOption.equalsIgnoreCase("Wallet"))
				{	
				// MAKES PAYMENT : UPDATES CUSTOMER BALANCE
	
				String updatedBalance= Double.toString(newBalance);
				cust.setBalance(updatedBalance);  //updating customer wallet
				String message = "Successful payment : Paid: €" + price + " for Items";
				logPaymentSuccessOrFailure(Integer.toString(cust.getUserID()), message, true); //interceptor method??
				
					receipt= new WalletDecorator(receipt); //receipt decorated to show that the customer paid by wallet
					printReceipt = true;
					database.updateUsers();
				}
				
				else if(paymentOption.equalsIgnoreCase("Credit Card"))
				{	
					receipt= new CreditCardDecorator(receipt); //receipt decorated to show that the customer paid by credit card
					printReceipt = true;
				}
				
				if(printReceipt)
				{	
					userInterface.UserInterfaceMenu execut = new UserInterfaceMenu(); //call to user interface
					execut.displayReceipt(username,receipt); //displayReceipt method of UI is called which implements the PrintReceipt() method and prints out all the decorators that are dynamically bound to the Receipt
				
				}
				
			
				cust.getCart().clearCart();
				// must clear the cart afterwards
				database.clearUsersCart(Integer.toString(cust.getUserID()));
					
					// THIS UPDATES USERS.TXT WITH THE NEW BALANCE FOR CUSTOMER
				
				 //refresh users so that their new wallet balance is updated to the text file
		}
		else
		{
			String message = ("Payment Cancelled! : Insufficient Funds!");
			userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
			execute.displayErrorMessage(message);
			logPaymentSuccessOrFailure(Integer.toString(cust.getUserID()), message, false); //interceptor??
			return;
		}
		
	}
	
	public void logPaymentSuccessOrFailure(String userID, String description, boolean isSuccessful) throws IOException
	{
		ClientRequestDispatcher.getInstance().dispatchClientRequestInterceptorPaymentLogging(new PaymentInfoContext(userID, description, isSuccessful));
	}
}
