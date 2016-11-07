package payment;

import java.io.IOException;

import database.Database;
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
		
		UserClass customer= database.getUserByName(username);
		int userID = customer.getUserID();
		CustomerClass cust= (CustomerClass)customer;
		MediaItem item= database.getMediaItemByName(mediaTitle);
		double oldBalance= Double.parseDouble(cust.getBalance());
		double price= item.getPrice();
		double newBalance= oldBalance-price;
		if(newBalance>=0.0)
		{
			// ENOUGH MONEY!
		
			
			if(purchaseType.equalsIgnoreCase("Store in Online Repository"))
			{
				// TRYS TO ADD TO ONLINE REPOSITORY
				boolean alreadyInRepository = database.updateOnlineMediaRepository(Integer.toString(userID), item.getMediaID());

				// ERROR & PAYMENT CANCELLED IF ALREADY IN REPOSITORY
				if(alreadyInRepository)
				{				
					String message = ("Payment Cancelled!\n" + item.getTitle() + " is already in your online repository!");
					userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
					execute.displayErrorMessage(message);
					return;
				}
			}
			
			
			// MAKES PAYMENT : UPDATES CUSTOMER BALANCE
			String updatedBalance= Double.toString(newBalance);
			cust.setBalance(updatedBalance);
			
			I_Receipt receipt= new ReceiptA();
			receipt= new CustomerDecorator(receipt);
			userInterface.UserInterfaceMenu execut = new UserInterfaceMenu();
			execut.displayReceipt(item);
		
				
				// THIS UPDATES USERS.TXT WITH THE NEW BALANCE FOR CUSTOMER
			database.updateUsers();
		}
		else
		{
			String message = ("Payment Cancelled : Insufficient Funds!");
			userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
			execute.displayErrorMessage(message);
		}
		
		if(item.getMediaType().equalsIgnoreCase("GAME"))
		{
			GameClass itemG= (GameClass)item;
			PlatformChoice pChoice= new PlatformChoice();
			pChoice.nullPLatform(itemG);
		}
	}
}
