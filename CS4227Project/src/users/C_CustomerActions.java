package users;

import java.io.*;

import payment.AddToWallet;
import streaming.StreamMedia;

public class C_CustomerActions implements I_UserActions 
{
	public void userActions(String dropdownSelection, String returnedString) throws IOException
	{
		if(dropdownSelection.equals("Browse Film List"))
		{

		}
		else if(dropdownSelection.equals("Search For Film"))
		{

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
	}
	
	public void rent(String username_FilmName)
	{

	}
	
	//John
	public static void buy(String username_FilmName) throws IOException
	{

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










