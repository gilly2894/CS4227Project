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
import userInterface.UserInterfaceMenu;
import users.CustomerClass;
import users.UserClass;

public class CartCheckout {
	
	Database database = Database.getInstance();

	
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
				}
				
				else if(paymentOption.equalsIgnoreCase("Credit Card"))
				{	
					receipt= new CreditCardDecorator(receipt); //receipt decorated to show that the customer paid by credit card
				}
				
				userInterface.UserInterfaceMenu execut = new UserInterfaceMenu(); //call to user interface
				execut.displayReceipt(username,receipt); //displayReceipt method of UI is called which implements the PrintReceipt() method and prints out all the decorators that are dynamically bound to the Receipt
			
					
					// THIS UPDATES USERS.TXT WITH THE NEW BALANCE FOR CUSTOMER
				database.updateUsers(); //refresh users so that their new wallet balance is updated to the text file
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
