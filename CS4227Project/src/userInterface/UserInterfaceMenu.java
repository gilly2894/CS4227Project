package userInterface;

import java.io.IOException;

import javax.swing.JOptionPane;

import program.DatabaseFetcher;
import program.TypeOfFactoryGenerator;
import users.C_AdminActions;
import users.I_UserActions;
import users.UserClass;
import users.UserFactory;

public class UserInterfaceMenu {
	
	DatabaseFetcher databaseFetcher = new DatabaseFetcher();
	UserFactory userFactory = new UserFactory();
	UserClass currentUser;
	I_UserActions userMenu = null;
	
	public void showMainMenu() throws IOException {
		boolean quit=false;		
		while(!quit)
		{
			Object [] selection = {"Log In", "Register as a new customer", "Quit"};
			String mainMenuSelection = (String) JOptionPane.showInputDialog(null, "Welcome to MegaStream!","", 1 , null, selection, selection[0]);
			if(mainMenuSelection.equals("Log In"))
			{
				Login();	
			}
			else if(mainMenuSelection.equals("Register as a new customer"))
			{
				registerNewCustomer();
			}
			else if(mainMenuSelection.equals("Quit"))
			{
				quit=true;
			}
		}
	}
	
	public void Login() throws IOException
	{
		boolean loggedIn=false;
		while(!loggedIn)
		{
			String userName = "";
			userName = getUsernameInput();
			if(userName.equalsIgnoreCase("Exit"))
			{
				return;
			}
			UserClass userSignIn = databaseFetcher.getUserByName(userName);
			if(userSignIn!=null)
			{
				
				String password = getPasswordInput();
				if((userSignIn.getPassword()).equals(password))
				{
					currentUser = userSignIn;
					loggedIn = true;
				}
				else
					JOptionPane.showMessageDialog(null, "Incorrect Password!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, userName + " does not match a user in the database!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		JOptionPane.showMessageDialog(null, "Logged in!\n\nWelcome to MegaStream " + currentUser.getName());
		
		//  go into the correct functionality for the type of user you are
		userActionMenu();
		
	}
	
//  Register as a new User
	public void registerNewCustomer() throws IOException
	{
		UserClass newCustomer = TypeOfFactoryGenerator.getFactory("USER").getUser("CUSTOMER");
		double startingBalance = 0.0;
		boolean uniqueUsername=false;
		
		String userString="Customer,",userName = "";
		int lastUserID = databaseFetcher.getHighestUserID();
		int uniqueUserID = ++lastUserID;
		userString += uniqueUserID + ",";
		while(!uniqueUsername)
		{
			userName = getUsernameInput();
			if(userName.equalsIgnoreCase("Exit"))
				return;
			if(databaseFetcher.getUserByName(userName) != null)
			{
				JOptionPane.showMessageDialog(null, "Username is already being used", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				userString += userName + ",";
				uniqueUsername=true;
			}
		}
		String password = getPasswordInput();
		userString += password.replaceAll(",", "") + ",";
		String name = getFullNameInput();
		userString += name.replaceAll(",", "") + ",";
		String email = getEmailInput();
		userString += email.replaceAll(",", "") + ",";
		String number = getPhoneNumberInput();
		userString += number.replaceAll(",", "") + ",";
		userString += startingBalance + ",";
		String address = getAddressInput();
		userString += address.replaceAll(",", "");
		newCustomer.createUser(userString);
		currentUser = newCustomer;
		databaseFetcher.addUser(currentUser);
		JOptionPane.showMessageDialog(null, "Thanks you for registering " + currentUser.getName() + "\nYou are a " + currentUser.getType());
		
		//  go into the correct functionality for the type of user you are
		userActionMenu();
	}
	
	
	//This is the class that lets us access the business logic
		public void userActionMenu() throws IOException
		{
			boolean stillLoggedIn = true;
			String returnedSelection = "";
			
			// this checks what type of users you are
			String type = currentUser.getType();
			
			// then goes in to the correct functionality based on what type of user you are
			if(type.matches("Admin"))
			{
				while(stillLoggedIn)
				{
					// this shows the Admin dropdown menu and returns the selection from it
					returnedSelection = showAdminMenu();
					
					// userMenu is of refrence type I_UserActions, which is the base class(An interface)
					// new C_AdminActions(); calls the constructor of C_AdminActions(C is for control class) so that 
					// userMenu.methodName() will call the methods in C_AdminActions
					userMenu = new C_AdminActions();
					
					//
					if(returnedSelection.equals("Add User"))
					{
						// calls the method to get the user input for a new user
						String userToCreate = addNewUser();
						
						//calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
						//concrete class
						userMenu.userActions(returnedSelection, userToCreate);
					
					}
					
					else if(returnedSelection.equals("Delete User"))
					{
						// calls the method to get the name of the user you want to delete
						String userToRemove = UserToRemove();
						
						//calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
						//concreate class
						userMenu.userActions(returnedSelection, userToRemove);
					}
					
					else if(returnedSelection.equals("Update User"))
					{
						//  calls the method to get the user to be updated, what part is being updated, and the new value
						String updateUser = UserToUpdate();
						
						//calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
						//concreate class
						userMenu.userActions(returnedSelection, updateUser);
					}
					else if(returnedSelection.equals("Logout"))
					{
						//this will break the loop and log user out
						stillLoggedIn = false;
					}
				}
				
			}
			else if(type.matches("Customer"))
			{
				System.out.println("customerActionMenu");
			}
			else if(type.matches("Staff"))
			{
				System.out.println("staffActionMenu");
			}
		}
		
		
		
		public String addNewUser() throws IOException
		{
			UserClass newUser = null;
			String userString="",userName = "", type="";
			double startingBalance = 0.0;
			type = getTypeInput();
			userString += type + ",";
			newUser = TypeOfFactoryGenerator.getFactory("USER").getUser("CUSTOMER");
			
			int lastUserID = databaseFetcher.getHighestUserID();
			int uniqueUserID = ++lastUserID;
			userString += uniqueUserID + ",";
			boolean uniqueUsername=false;
			while(!uniqueUsername)
			{
				userName = getUsernameInput();
				if(userName.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Username Field empty", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				if(databaseFetcher.getUserByName(userName) != null)
				{
					JOptionPane.showMessageDialog(null, "Username is already being used", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					userString += userName + ",";
					uniqueUsername=true;
				}
			}
			String password = getPasswordInput();
			userString += password.replaceAll(",", "") + ",";
			String name = getFullNameInput();
			userString += name.replaceAll(",", "") + ",";
			String email = getEmailInput();
			userString += email.replaceAll(",", "") + ",";
			String number = getPhoneNumberInput();
			userString += number.replaceAll(",", "");
		    if(type.equals("Customer"))
		    {
				userString += "," + startingBalance;
				String address = getAddressInput();
				userString += "," + address.replaceAll(",", "");
		    }
			
			return userString;
		}	
		
		// returns the name of the user you want to remove.. it also check here whether the user is in the system or not based on
		//the username
		public String UserToRemove()
		{
			String userToRemoveUserName="";
			boolean validUser=false;
			while(!validUser)
			{
				userToRemoveUserName = getUserToRemove_UsernameInput();
				if(databaseFetcher.getUserByName(userToRemoveUserName) != null)
				{
					validUser=true;
				}
				else
					JOptionPane.showMessageDialog(null, "Not a valid username", "Error", JOptionPane.ERROR_MESSAGE);
			}
				
			return userToRemoveUserName;
		}
			
		// this gets the user that has to be changed and the piece of information that needs changing and passes it back as a String
			public String UserToUpdate()
			{
				String updateString="";
				String type = null, userName = null, name = null, password = null, email = null, phoneNumber = null;
				boolean validUser=false;
				while(!validUser)
				{
					String userToUpdateUserName = getUserToModify_UsernameInput();
					
					if(databaseFetcher.getUserByName(userToUpdateUserName) != null)
					{
						validUser=true;
						updateString += userToUpdateUserName + ",";
					}
					else
						JOptionPane.showMessageDialog(null, "Not a valid username", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
				String pieceToUpdate = showUserModificationMenu();
				if(pieceToUpdate.matches("User type"))
				{
					type = getTypeInput();
					updateString += "Type," + type;
				}
				else if(pieceToUpdate.matches("Username"))
				{
					String newUserName = getUsernameInput();
					if(databaseFetcher.getUserByName(newUserName) == null)
					{
						userName = newUserName;
						updateString += "Username," + userName;
					}
					else
						JOptionPane.showMessageDialog(null, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(pieceToUpdate.matches("Name"))
				{
					name = getFullNameInput();
					updateString += "Name," + name;
				}
				else if(pieceToUpdate.matches("Password"))
				{
					password = getPasswordInput();
					updateString += "Password," + password;
				}
				else if(pieceToUpdate.matches("Email"))
				{
					email = getEmailInput();
					updateString += "Email," + email;
				}
				else if(pieceToUpdate.matches("Phone Number"))
				{
					phoneNumber = getPhoneNumberInput();
					updateString += "PhoneNumber," + phoneNumber;
				}
				
				// One piece of info can be changed at once, so the update string consists of 3 comma seperated values :
				// 1)The username of the user that is being updated
				// 2)The type of information that is being updated, such as username, email, or phone number
				// 3)The new value
				return updateString;
			}
	
	
	//		 Menus
	public String showAdminMenu() {
		Object [] selection = {"Add User", "Delete User", "Update User", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Admin : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	//new John
	public String showCustomerMenu() {
		Object [] selection = {"Browse Film List", "Search By Category", "Search for Film", "Watch Film", "Add Funds to Wallet", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showStaffMenu() {
		Object [] selection = {"View Film Catalogue", "View Supplier Catalogue", "Add to Film Catalogue", "Remove from Film Catalogue", "Add Film Promotion", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Staff Member : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showSupplierMenu() {
		Object [] selection = {"Add new film to Supplier Catalogue", "View Supplier Catalogue", "Search for film in Supplier Catalogue", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Supplier : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showUserModificationMenu() {
		Object [] selection = {"User type", "Username", "Name", "Password", "Email", "Phone Number"};
		return (String) JOptionPane.showInputDialog(null, "What do you want to modify?","Admin : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	
	
	//    Inputs
	public String getTypeInput()
	{
		Object [] typeSelection = {"Admin", "Customer", "Staff", "Supplier"};
		Object userType = JOptionPane.showInputDialog(null, "User Type:","", 1 , null, typeSelection, typeSelection[0]);
		return (String) userType;
	}

	public String getUsernameInput() {
		return JOptionPane.showInputDialog(null, "Username:");
	}
	
	public String getUserToModify_UsernameInput() {
		return JOptionPane.showInputDialog(null, "Username of user you want to modify:");
	}
	
	public String getUserToRemove_UsernameInput() {
		return JOptionPane.showInputDialog(null, "Enter username of user you want to remove:");
	}

	public String getFullNameInput() {
		return JOptionPane.showInputDialog(null, "Full Name:");
	}
	
	public String getPasswordInput() {
		return JOptionPane.showInputDialog(null, "Password:");	
	}
	
	public String getEmailInput() {
		return JOptionPane.showInputDialog(null, "Email Address:");	
	}
	
	public String getPhoneNumberInput()
	{
		return JOptionPane.showInputDialog(null, "Phone Number:");
	}
	
	public String getAddressInput()
	{
		return JOptionPane.showInputDialog(null, "Address:");
	}
	
	public String getFilmNameInput()
	{
		return JOptionPane.showInputDialog(null, "Enter the name of the film:");
	}

}
