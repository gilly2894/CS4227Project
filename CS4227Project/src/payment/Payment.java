package payment;

import java.io.IOException;

import database.Database;
import interceptor.contextObjects.PaymentInfoContext;
import interceptor.dispatchers.ClientRequestDispatcher;
import media.GameClass;
import media.MediaItem;
import media.PlatformChoice;
import userInterface.UserInterfaceMenu;
import users.CustomerClass;
import users.UserClass;

public class Payment {
	
	Database database = Database.getInstance();

	
	//TODO! should take out Credit card functionality altogether and just us wallet
	public void processPayment(String username_mediaTitle_purchaseType_paymentOption) throws IOException
	{
		String[] arr = username_mediaTitle_purchaseType_paymentOption.split(",");
		String username = arr[0];
		String mediaTitle = arr[1];
		String purchaseType = arr[2];
		String paymentOption = arr[3];
		I_Receipt receipt= new ReceiptA();
		
		UserClass customer= database.getUserByName(username);
		int userID = customer.getUserID();
		CustomerClass cust= (CustomerClass)customer;
		MediaItem item= database.getMediaItemByName(mediaTitle);
		double oldBalance= Double.parseDouble(cust.getBalance());
		double price= item.getPrice();
		double newBalance= oldBalance-price;
			
		if((newBalance>=0.0 && paymentOption.equalsIgnoreCase("Wallet")) || paymentOption.equalsIgnoreCase("Credit Card") )
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
				receipt= new CustomerDecorator(new OnlineDecorator(receipt));
				
			}
			
			else if(purchaseType.equalsIgnoreCase("Ship to Address"))
			{
				receipt= new CustomerDecorator(new ShipmentDecorator(receipt));
			}
			
			if(paymentOption.equalsIgnoreCase("Wallet"))
			{	
			// MAKES PAYMENT : UPDATES CUSTOMER BALANCE

			String updatedBalance= Double.toString(newBalance);
			cust.setBalance(updatedBalance);
			String message = "Successful payment : Paid: " + price + " for " + mediaTitle;
			logPaymentSuccessOrFailure(Integer.toString(userID), message, true);
			

				updatedBalance= Double.toString(newBalance);
				cust.setBalance(updatedBalance);
				receipt= new WalletDecorator(receipt);
			}
			
			if(paymentOption.equalsIgnoreCase("Credit Card"))
			{	
				receipt= new CreditCardDecorator(receipt);
			}
			

			userInterface.UserInterfaceMenu execut = new UserInterfaceMenu();
			execut.displayReceipt(item,receipt);
		
				
				// THIS UPDATES USERS.TXT WITH THE NEW BALANCE FOR CUSTOMER
			database.updateUsers();
		}
		else
		{
			String message = ("Payment Cancelled! : Insufficient Funds!");
			userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
			execute.displayErrorMessage(message);
			logPaymentSuccessOrFailure(Integer.toString(userID), message, false);
			return;
		}
		
		if(item.getMediaType().equalsIgnoreCase("GAME"))
		{
			GameClass itemG= (GameClass)item;
			PlatformChoice pChoice= new PlatformChoice();
			pChoice.nullPLatform(itemG);
		}
	}
	
	public void logPaymentSuccessOrFailure(String userID, String description, boolean isSuccessful) throws IOException
	{
		ClientRequestDispatcher.getInstance().dispatchClientRequestInterceptorPaymentLogging(new PaymentInfoContext(userID, description, isSuccessful));
	}
}
