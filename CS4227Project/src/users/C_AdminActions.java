package users;

import java.io.IOException;

import program.TypeOfFactoryGenerator;

public class C_AdminActions implements I_UserActions
{
	UserFactory userFactory = new UserFactory();
	public void userActions(String dropdownSelection, String returnedString) throws IOException 
	{	
//		if(dropdownSelection.equals("Add User"))
//		{
//			UserClass newUser = null;
//			String type = returnedString.substring(0, returnedString.indexOf(","));
//			newUser = TypeOfFactoryGenerator.getFactory("USER").getUser(type);
//			newUser.createUser(returnedString);
//			database.addUser(newUser);
//			
//		}
//		else if(dropdownSelection.equals("Delete User"))
//		{
//			UserClass userToRemove = null;
//			userToRemove = database.getUserByName(returnedString);
//			database.removeUser(userToRemove);
//		}
//		else if(dropdownSelection.equals("Update User"))
//		{
//			String[] arr = returnedString.split(",");
//			if(arr!=null)
//			{
//				UserClass userToUpdate = database.getUserByName(arr[0]);
//				String partBeingUpdated = arr[1];
//				if(partBeingUpdated.equals("Type"))
//				{
//					userToUpdate.setType(arr[2]);
//				}
//				else if(partBeingUpdated.equals("Username"))
//				{
//					userToUpdate.setUsername(arr[2]);
//				}
//				else if(partBeingUpdated.equals("Name"))
//				{
//					userToUpdate.setName(arr[2]);
//				}
//				else if(partBeingUpdated.equals("Password"))
//				{
//					userToUpdate.setPassword(arr[2]);
//				}
//				else if(partBeingUpdated.equals("Email"))
//				{
//					userToUpdate.setEmail(arr[2]);
//				}
//				else if(partBeingUpdated.equals("PhoneNumber"))
//				{
//					userToUpdate.setPhoneNumber(arr[2]);
//				}
//				database.updateUsers();
//			}
//		}
	}
	
	public void addNewUser(String userToAdd) throws IOException
	{
		UserClass newUser = null;
		String type = userToAdd.substring(0, userToAdd.indexOf(","));
		newUser = TypeOfFactoryGenerator.getFactory("USER").getUser(type);
		newUser.createUser(userToAdd);
		database.addUser(newUser);
	}
	
	public void removeUser(String userToRemoveParam) throws IOException
	{
		UserClass userToRemove = null;
		userToRemove = database.getUserByName(userToRemoveParam);
		database.removeUser(userToRemove);
	}
	
	public void updateUser(String userToUpdate_attribute_newValue) throws IOException
	{
		String[] arr = userToUpdate_attribute_newValue.split(",");
		if(arr!=null)
		{
			UserClass userToUpdate = database.getUserByName(arr[0]);
			String partBeingUpdated = arr[1];
			if(partBeingUpdated.equals("Type"))
			{
				userToUpdate.setType(arr[2]);
			}
			else if(partBeingUpdated.equals("Username"))
			{
				userToUpdate.setUsername(arr[2]);
			}
			else if(partBeingUpdated.equals("Name"))
			{
				userToUpdate.setName(arr[2]);
			}
			else if(partBeingUpdated.equals("Password"))
			{
				userToUpdate.setPassword(arr[2]);
			}
			else if(partBeingUpdated.equals("Email"))
			{
				userToUpdate.setEmail(arr[2]);
			}
			else if(partBeingUpdated.equals("PhoneNumber"))
			{
				userToUpdate.setPhoneNumber(arr[2]);
			}
			else if(userToUpdate.getType().equalsIgnoreCase("CUSTOMER"))
			{
				if(partBeingUpdated.equals("Address"))
				{
					((CustomerClass) userToUpdate).setAddress(arr[2]);
				}	
			}
			
			database.updateUsers();
		}
	}
	
	
		
		
}
