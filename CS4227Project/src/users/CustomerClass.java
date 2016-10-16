package users;

import java.io.IOException;

public class CustomerClass extends UserClass
{
	private String balance="", address="";
	
	public CustomerClass()
	{
		super();
	}
	
	public CustomerClass(String type, int userID, String username, String password, String name, String email, String phoneNumber, String balance, String address)
	{
		super(type, userID, username, password, name, email, phoneNumber);
		this.balance = balance;
		this.address = address;
	}
	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void createUser(String aLine) throws IOException
	{
		super.createUser(aLine);
		String[] arr = aLine.split(",");
		if(arr.length > 7)
		{
			setBalance(arr[7]);
			setAddress(arr[8]);
		}
			
	}
	
	@Override
	public String toString() 
	{
		String returnString="";
		returnString += getType() + "," + getUserID() + "," + getUsername() + "," + getPassword() + "," + getName()
		+ "," + getEmail() + "," + getPhoneNumber() + "," + getBalance() + "," + getAddress();
		return returnString;
	}
}