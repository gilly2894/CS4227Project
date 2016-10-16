package users;

import java.io.IOException;

public class StaffClass extends UserClass
{
	public StaffClass()
	{
		super();
	}
	
	public StaffClass(String type, int userID, String username, String password, String name, String email, String phoneNumber)
	{
		super(type, userID, username, password, name, email, phoneNumber);
	}
	
	public void createUser(String aLine) throws IOException
	{
		super.createUser(aLine);
	}
}