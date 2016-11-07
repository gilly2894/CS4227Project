package users;

import java.io.*;

import payment.AddToWallet;
import streaming.StreamMedia;
import media.*;
public class C_CustomerActions implements I_UserActions 
{
	public void userActions(String dropdownSelection, String returnedString) throws IOException
	{
		if(dropdownSelection.equals("Browse Film List"))
		{
			
		}
		else if(dropdownSelection.equals("Search for Media Item"))
		{
			buy(returnedString);
		}
		else if(dropdownSelection.equals("Search By Category"))
		{

		}
		else if(dropdownSelection.equals("View Media Repository"))
		{
			//returnedString for "View Media Repository" is the name of the media item selected 
			StreamMedia streamMedia = new StreamMedia();
			streamMedia.stream(returnedString);
		}
		else if(dropdownSelection.equals("Add Funds to Wallet"))
		{
			payment.AddToWallet execute = new AddToWallet();
			execute.wallet(returnedString);
		}
		
		else if(dropdownSelection.equals("Choose Platform"))
		{
			decidePlatform(returnedString);
		}
	}
	
	public void rent(String username_FilmName)
	{

	}
	
	
	public static void buy(String username_MediaName_confirm) throws IOException
	{
		String[] returnedStrArr = username_MediaName_confirm.split(",");
		String username = returnedStrArr[0];
		String MediaName = returnedStrArr[1];
		UserClass customer= database.getUserByName(username);
		CustomerClass cust= (CustomerClass)customer;
		media.MediaItem item= database.getMediaItemByName(MediaName);
		double oldBalance= Double.parseDouble(cust.getBalance());
		double price= item.getPrice();
		double newBalance= oldBalance-price;
		String updatedBalance= Double.toString(newBalance);
		cust.setBalance(updatedBalance);
		database.updateUsers();
		
	}
	
	public static void decidePlatform(String username_MediaName_Platform)
	{
		String[] returnedStrArr = username_MediaName_Platform.split(",");
		String username = returnedStrArr[0];
		String MediaName = returnedStrArr[1];
		String Platform = returnedStrArr[2];
		media.GameClass item= (media.GameClass)database.getMediaItemByName(MediaName);
		if(Platform.equalsIgnoreCase("XBOX"))
		{
			item.setBundler(new XBoxGame());
			item.bundle();
		}
		
		if(Platform.equalsIgnoreCase("PS4"))
		{
			item.setBundler(new PS4Game());
			item.bundle();
		}
		
		if(Platform.equalsIgnoreCase("NULLPLATFORM"))
		{
			double bPrice= item.getPrice();
			double bRate= item.reset();
			double originalPrice= bPrice/bRate;
			item.setPrice(originalPrice);
			item.setBundler(null);
			
		}
			
	}
	
	
	
	//John
	public static void watch(String userName_FilmName) throws IOException
	{

	}
	
	
	
	//John
	public static void addWallet(String userID_amount_conformation) throws IOException
	{

	}
}










