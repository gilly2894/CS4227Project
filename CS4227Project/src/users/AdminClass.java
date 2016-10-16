package users;
import java.io.*;
public class AdminClass extends UserClass
{
	public AdminClass()
	{
		super();
	}
	public AdminClass(String type, int userID, String username, String password, String name, String email, String phoneNumber)
	{
		super(type, userID, username, name, password, email, phoneNumber);
	}
	public void createUser(String aLine) throws IOException
	{
		super.createUser(aLine);
	}
}	