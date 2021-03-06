package payment;

import java.io.IOException;

import userInterface.UserInterfaceMenu;
import database.Database;
import program.I_Receiver;
import users.*;

public class AddToWallet implements I_Receiver {
	
	Database database= Database.getInstance();
	public void wallet(String userID_amount) throws IOException
	{
		String[] returnedStrArr = userID_amount.split(",");
		String username = returnedStrArr[0];
		String amount = returnedStrArr[1];
		processWallet(username,amount);
		
	}
	
	public void processWallet(String userName, String amount) throws IOException
	{
		UserClass customer= database.getUserByName(userName);
		CustomerClass cust= (CustomerClass)customer;
		double oldAmount = Double.parseDouble(cust.getBalance());
		String updatedWallet = amount.replaceAll("\\D+","");
		double updateAmount = Double.parseDouble(updatedWallet);
		double currentamount = (oldAmount + updateAmount);
		String newWallet = Double.toString(currentamount);
		cust.setBalance(newWallet);
		database.updateUsers();
	//	database.updateWallet(userName, newWallet);
		String message = ("New Wallet balance is �" + newWallet);
		userInterface.UserInterfaceMenu execute = new UserInterfaceMenu();
		execute.showNewWallet(message);
	}

}

