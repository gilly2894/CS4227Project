package users;

import java.io.IOException;

import javax.swing.JOptionPane;

import media.ShoppingCart;

public class CustomerClass extends UserClass
{
	private String balance="", address="";
	private ShoppingCart cart;
	


	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public CustomerClass()
	{
		super();
		cart = new ShoppingCart();
	}
	
	public CustomerClass(String type, int userID, String username, String password, String name, String email, String phoneNumber, String balance, String address)
	{
		super(type, userID, username, password, name, email, phoneNumber);
		this.balance = balance;
		this.address = address;
		cart = new ShoppingCart();
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

	public boolean purchase(double price) throws NumberFormatException, IOException{
		if(price <= Double.parseDouble(balance)){
			setBalance(Double.toString(Double.parseDouble(balance) - price));
			return true;
		}
		else{
			JOptionPane.showMessageDialog(null, "You do not have sufficient funds. Please add funds to your wallet! Current Balance is: €" + getBalance(),"Insufficient Funds", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public ShoppingCart getCart() {
		return cart;	}
}