package users;

import java.io.*;
public abstract class UserClass
{
	private String type = "", username = "", password = "", name = "", email = "", phoneNumber = "";	
	private int userID = 0;
	public UserClass()
	{
		
	}
	public UserClass(String type, int userID, String username, String password, String name, String email, String phoneNumber) {
		this.type = type;
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUserID() {
		return userID;
	}


	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void createUser(String aLine) throws IOException {
		String[] arr = aLine.split(",");
		setType(arr[0]);
		setUserID(Integer.parseInt(arr[1]));
		setUsername(arr[2]);
		setPassword(arr[3]);
		setName(arr[4]);
		setEmail(arr[5]);
		setPhoneNumber(arr[6]);
	}
	
	public String toString()
	{
		String returnString="";
		returnString += getType() + "," + getUserID() + "," + getUsername() + "," + getPassword() + "," + getName()
		+ "," + getEmail() + "," + getPhoneNumber();
		return returnString;
	}
}