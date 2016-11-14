package payment;

import java.io.IOException;

import database.Database;
import interceptor.contextObjects.PaymentInfoContext;
import interceptor.dispatchers.ClientRequestDispatcher;
import media.GameClass;
import media.MediaItem;
import media.PlatformChoice;
import program.I_Receiver;
import userInterface.UserInterfaceMenu;
import users.CustomerClass;
import users.UserClass;

public class Payment implements I_Receiver {
	
	Database database = Database.getInstance();

	
	//TODO! should take out Credit card functionality altogether and just us wallet
	public void processPayment(String username_mediaTitle_purchaseType_paymentOption) throws IOException
	{
		String[] arr = username_mediaTitle_purchaseType_paymentOption.split(",");
		String username = arr[0];
		String mediaTitle = arr[1];
		String purchaseType = arr[2];
		String paymentOption = arr[3];                
		I_Receipt receipt= new ReceiptA();	//receipt object created
		
		UserClass customer= database.getUserByName(username);
		int userID = customer.getUserID();
		CustomerClass cust= (CustomerClass)customer; //Casting from Userclass to CustomerClass
		MediaItem item= database.getMediaItemByName(mediaTitle);
		double oldBalance= Double.parseDouble(cust.getBalance());
		double price= item.getPrice();
		double newBalance= oldBalance-price;
			
		if((newBalance>=0.0 && paymentOption.equalsIgnoreCase("Wallet")) || paymentOption.equalsIgnoreCase("Credit Card") ) //both cases mean their payment is valid
		{
			// ENOUGH MONEY!
		
			
			if(purchaseType.equalsIgnoreCase("Store in Online Repository"))
			{
				// TRYS TO ADD TO ONLINE REPOSITORY
				boolean alreadyInRepository = database.updateOnlineMediaRepository(Integer.toString(userID), item.getMediaID());

				// ERROR & PAYMENT CANCELLED IF ALREADY IN REPOSITORY
				if(alreadyInRepository)
				{				
					String message = ("Payment Cancelled! " + item.getTitle() + " is already in online repository!");
					userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
					execute.displayErrorMessage(message);
					logPaymentSuccessOrFailure(Integer.toString(userID), message, false);
					return;
				}
				receipt= new CustomerDecorator(new OnlineDecorator(receipt)); //receipt decorated so that a customer has bought an item to be stored in online repository
				
			}
			
			else if(purchaseType.equalsIgnoreCase("Ship to Address"))
			{
				receipt= new CustomerDecorator(new ShipmentDecorator(receipt)); //receipt decorated so that a customer has bought an item to be shipped to them
			}
			
			if(paymentOption.equalsIgnoreCase("Wallet"))
			{	
			// MAKES PAYMENT : UPDATES CUSTOMER BALANCE

			String updatedBalance= Double.toString(newBalance);
			cust.setBalance(updatedBalance);  //updating customer wallet
			String message = "Successful payment : Paid: " + price + " for " + mediaTitle;
			logPaymentSuccessOrFailure(Integer.toString(userID), message, true); //interceptor method??
			

				
				receipt= new WalletDecorator(receipt); //receipt decorated to show that the customer paid by wallet
			}
			
			if(paymentOption.equalsIgnoreCase("Credit Card"))
			{	
				receipt= new CreditCardDecorator(receipt); //receipt decorated to show that the customer paid by credit card
			}
			

			userInterface.UserInterfaceMenu execut = new UserInterfaceMenu(); //call to user interface
			execut.displayReceipt(item,receipt); //displayReceipt method of UI is called which implements the PrintReceipt() method and prints out all the decorators that are dynamically bound to the Receipt
		
				
				// THIS UPDATES USERS.TXT WITH THE NEW BALANCE FOR CUSTOMER
			database.updateUsers(); //refresh users so that their new wallet balance is updated to the text file
		}
		else
		{
			String message = ("Payment Cancelled! : Insufficient Funds!");
			userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
			execute.displayErrorMessage(message);
			logPaymentSuccessOrFailure(Integer.toString(userID), message, false); //interceptor??
			return;
		}
		
		if(item.getMediaType().equalsIgnoreCase("GAME"))
		{
			GameClass itemG= (GameClass)item; 
			PlatformChoice pChoice= new PlatformChoice();
			pChoice.nullPLatform(itemG); //When a platform is chosen for a game item the price is changed depending on the platform, nullPlatform is called to reset the game item back to its original price
		}
	}
	
	public void logPaymentSuccessOrFailure(String userID, String description, boolean isSuccessful) throws IOException
	{
		ClientRequestDispatcher.getInstance().dispatchClientRequestInterceptorPaymentLogging(new PaymentInfoContext(userID, description, isSuccessful));
	}
}
