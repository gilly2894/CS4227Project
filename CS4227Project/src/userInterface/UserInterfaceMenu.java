package userInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;
import payment.*;
import media.MediaItem;
import program.DatabaseFetcher;
import program.TypeOfFactoryGenerator;
import users.C_AdminActions;
import users.C_CustomerActions;
import users.C_StaffActions;
import users.I_UserActions;
import users.UserClass;
import users.UserFactory;

public class UserInterfaceMenu {
	
	DatabaseFetcher databaseFetcher = new DatabaseFetcher();
	UserFactory userFactory = new UserFactory();
	UserClass currentUser;
	I_UserActions userMenu = null;
	I_Command command = null;
	MenuSelectionInvoker invoker = new MenuSelectionInvoker();
	
	// receivers
	
	
	
	// Menus
	
	public void showMainMenu() throws IOException 
	{
		boolean quit=false;		
		while(!quit)
		{
			Object [] selection = {"Log In", "Register as a new customer", "Quit"};
			String mainMenuSelection = (String) JOptionPane.showInputDialog(null, "Welcome to A-Z!","", 1 , null, selection, selection[0]);
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
	
	public void showNewWallet(String newWallet) throws IOException
	{
		JOptionPane.showMessageDialog(null, newWallet);
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
		JOptionPane.showMessageDialog(null, "Logged in!\n\nWelcome to A-Z " + currentUser.getName());
		
		//  go into the correct functionality for the type of user you are
		//userActionMenu();
		
		newActionMenu();
		
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
	
	public void newActionMenu() throws IOException
	{
		boolean stillLoggedIn = true;
		String returnedMenuSelection = "";
		
		// this checks what type of users you are
		String type = currentUser.getType();
		
		// then goes in to the correct functionality based on what type of user you are
		if(type.matches("Admin"))
		{
			while(stillLoggedIn)
			{
				// this shows the Admin dropdown menu and returns the selection from it
				returnedMenuSelection = showAdminMenu();
				
				// userMenu is of reference type I_UserActions, which is the base class(An interface)
				// new C_AdminActions(); calls the constructor of C_AdminActions(C is for control class) so that 
				// userMenu.methodName() will call the methods in C_AdminActions
				
				// DONT NEED(I Think)!
				userMenu = new C_AdminActions();
					
				
				
				if(returnedMenuSelection.equals("Add User"))
				{
					// calls the method to get the user input for a new user
					String userToCreate = addNewUser();
					
					//calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
					//concrete class
					//userMenu.userActions(returnedMenuSelection, userToCreate);
					
					invoker.setCommand(new AF_AddNewUserCommand(new C_AdminActions()));
					invoker.optionSelectedWithStringParam(userToCreate);
				
				}
				
				else if(returnedMenuSelection.equals("Delete User"))
				{
					// calls the method to get the name of the user you want to delete
					String userToRemove = UserToRemove();
					
					// calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
					// concrete class
					// userMenu.userActions(returnedMenuSelection, userToRemove);
					
					
					invoker.setCommand(new AF_RemoveUserCommand(new C_AdminActions()));
					invoker.optionSelectedWithStringParam(userToRemove);
				}
				
				else if(returnedMenuSelection.equals("Update User"))
				{
					//  calls the method to get the user to be updated, what part is being updated, and the new value
					String updateUser = UserToUpdate();
					
					//calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
					//concrete class
					//userMenu.userActions(returnedMenuSelection, updateUser);
					
					invoker.setCommand(new AF_UpdateUserCommand(new C_AdminActions()));
					invoker.optionSelectedWithStringParam(updateUser);
				}
				else if(returnedMenuSelection.equals("Logout")){
					//this will break the loop and log user out
					stillLoggedIn = false;
				}
			}
		}
		else if(type.matches("Staff"))
		{
			while(stillLoggedIn)
			{
				// this shows the Admin drop down menu and returns the selection from it
				returnedMenuSelection = showStaffMenu();
				
				// userMenu is of reference type I_UserActions, which is the base class(An interface)
				// new C_AdminActions(); calls the constructor of C_AdminActions(C is for control class) so that 
				// userMenu.methodName() will call the methods in C_AdminActions
				
				// DONT NEED(I Think)!
				//userMenu = new C_StaffActions();
					
				
				
				if(returnedMenuSelection.equals("View Catalogue"))
				{
					MediaItem media = browseStaffMediaList();
					
					if(media!=null)
					{
						String returnString = currentUser.getUsername() + ",";
						returnString+= media.getTitle() + ",";
						String choice = StaffMediaItemDetails(media);
						if(!choice.equals("Cancel")) 
						{
							if(choice.equals("Edit"))
							{
								returnString+= ItemToUpdate();

								invoker.setCommand(new UpdateItemCommand(new C_StaffActions()));
								invoker.optionSelectedWithStringParam(returnString);
							}
							
							else if(choice.equals("Remove"))
							{
								invoker.setCommand(new RemoveItemCommand(new C_StaffActions()));
								invoker.optionSelectedWithStringParam(returnString);
							}
						}

					}
				}
				
				else if (returnedMenuSelection.equals("View Supplier Catalogue"))
				{
					MediaItem media = ViewSupplierCatalogue();
					String returnString = currentUser.getUsername() + ",";
					returnString+= media.getTitle() + ",";
					String choice = SupplierItemDetails(media);
					if(!choice.equals("Cancel"))
					{
						if (choice.equals("Add to Catalogue"))
						{
							invoker.setCommand(new AddItemCommand(new C_StaffActions()));
							invoker.optionSelectedWithStringParam(returnString);
						}
					}
				}
				
				else if(returnedMenuSelection.equals("Search Media Item"))
				{
					String catalogue = WhichCatalogue();
					if (!catalogue.equals("Cancel"))
					{
						if (catalogue.equals("Search Our Catalogue"))
						{
							MediaItem media = searchforItem("MEGASTREAM");
							
							if(media!=null)
							{
								String returnString = currentUser.getUsername() + ",";
								returnString+= media.getTitle() + ",";
								String choice = StaffMediaItemDetails(media);
								if(!choice.equals("Cancel")) 
								{
									if(choice.equals("Edit"))
									{
										returnString+= ItemToUpdate();
		
										invoker.setCommand(new UpdateItemCommand(new C_StaffActions()));
										invoker.optionSelectedWithStringParam(returnString);
									}
									
									else if(choice.equals("Remove"))
									{
										invoker.setCommand(new RemoveItemCommand(new C_StaffActions()));
										invoker.optionSelectedWithStringParam(returnString);
									}
								}
		
						}
					}
					else if (catalogue.equals("Search Supplier Catalogue"))
					{
						MediaItem media = searchforItem("SUPPLIER");
						String returnString = currentUser.getUsername() + ",";
						returnString+= media.getTitle() + ",";
						String choice = SupplierItemDetails(media);
						if(!choice.equals("Cancel"))
						{
							if (choice.equals("Add to Catalogue"))
							{
								invoker.setCommand(new AddItemCommand(new C_StaffActions()));
								invoker.optionSelectedWithStringParam(returnString);
							}
						}
					}
			}
		}
				
			else if(returnedMenuSelection.equals("Logout"))
			{
				//this will break the loop and log user out
				stillLoggedIn = false;
			}		
		}
	}
				
				else if(returnedMenuSelection.equals("Update User"))
				{
					//  calls the method to get the user to be updated, what part is being updated, and the new value
					String updateUser = UserToUpdate();
					
					//calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
					//concrete class
					//userMenu.userActions(returnedMenuSelection, updateUser);
					
					invoker.setCommand(new AF_UpdateUserCommand(new C_AdminActions()));
					invoker.optionSelectedWithStringParam(updateUser);
				}
				else if(returnedMenuSelection.equals("Logout")){
					//this will break the loop and log user out
					stillLoggedIn = false;
				
			
			}
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
						
						// calls the userActions method that is in C_AdminActions as userMenu was created with C_AdminActions as the
						// concrete class
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
					else if(returnedSelection.equals("Logout")){
						//this will break the loop and log user out
						stillLoggedIn = false;
					}
				}
			}
			else if(type.matches("Customer"))
			{
				while(stillLoggedIn)
				{
					// this shows the customer dropdown menu and returns the selection from it
					returnedSelection = showCustomerMenu();
					
					// new C_AdminActions(); calls the constructor of C_AdminActions(C is for control class) so that 
					// userMenu.methodName() will call the methods in C_AdminActions
					userMenu = new C_CustomerActions();
					
					//
					if(returnedSelection.equals("Browse Media List"))
					{
						browseMediaList();
					}
					
					else if(returnedSelection.equals("Search by Category"))
					{
						//to be filled
					}
					
					else if(returnedSelection.equals("View Shopping Cart"))
					{	
						Object [] selection = {"Display Items in Console", "Change Quantity", "Quit"};
						String choice = (String) JOptionPane.showInputDialog(null, "Shopping Cart Menu!","", 1 , null, selection, selection[0]);
						
						 if(choice.equals("Display Items in Console"))
						{
							 Map<MediaItem,String> cartList = databaseFetcher.getShoppingCart(Integer.toString(currentUser.getUserID()));
								for(Map.Entry<MediaItem, String> entry : cartList.entrySet()){
									System.out.println("Title of Movie: " + entry.getKey().getTitle() + "\t Quantity: " + entry.getValue());
								}
						}
						 
						 else if(choice.equals("Change Quantity"))
							{
							 String id = JOptionPane.showInputDialog(null, "Enter Media ID to change quantity of:");
							 String qty = JOptionPane.showInputDialog(null, "Enter quantity:");
							 databaseFetcher.updateShoppingCart(id, qty, currentUser.getUserID());
							}
						 
						 else if(choice.equals("Quit"))
							{
								break;
							} 
					}
					
					else if(returnedSelection.equals("Search for Media Item"))
					{
						MediaItem media = searchforItem("MEGASTREAM");
						
						if(media!=null)
						{
							String returnString = currentUser.getUsername() + ",";
							returnString+= media.getTitle() + ",";
							String choice = customerMediaItemDetailsAndReturnedChoice(media);
							if(!choice.equals("Cancel")) 
							{
								String choice2 = paymentMethod();
								if(!choice2.equals("Cancel"))
								{	
									String choice3 = confirmPurchase();
									returnString += (choice + ",");
									returnString += (choice2 + ",");
									
									returnString += choice3;
									if(choice3.equals("Confirm Purchase"))
									{
										displayReceipt(media);
									}
									//returnedSelection is the result of the first dropdown : "Search For Film"
									//returnString contains 3 comma separated values : currentUsers username, name of film,
									//and the choice of whether they want to buy or rent it
									userMenu.userActions(returnedSelection, returnString);
								}
							}
						}
					}
					
					else if(returnedSelection.equals("View Media Repository"))
					{
						//to be filled
						//String returnString = currentUser.getUserID() + ",";
						int userID = currentUser.getUserID();
						String chosenMediaItemID = viewCustomersMediaRepository(userID);
						if(!chosenMediaItemID.equals("Cancel"))
						{	
							String returnFilmName = chosenMediaItemID;
							userMenu.userActions(returnedSelection, returnFilmName);
						}
						
					}
					
					else if(returnedSelection.equals("Add Funds to Wallet"))
					{
						String username= currentUser.getUsername();
						String ammount= amountToAddToWallet();
						
						String confirmation= confirmPurchase();
						if (confirmation!= "Cancel" || ammount!= "Cancel")
						{
							String id_amount= username + "," + ammount;
							userMenu.userActions(returnedSelection, id_amount);
						}
						
					}
					
					else if(returnedSelection.equals("Logout"))
					{
						//this will break the loop and log user out
						stillLoggedIn = false;
					}
				}
			}
			else if(type.matches("Staff"))
			{
				while(stillLoggedIn)
				{
					// this shows the staff dropdown menu and returns the selection from it
					returnedSelection = showStaffMenu();

					//userMenu = new C_CustomerActions();
					
					if(returnedSelection.equals("View Catalogue"))
					{
						//browseStaffMediaList();
					}
					else if(returnedSelection.equals("Search Media Item"))
					{
						MediaItem media = searchforItem("MEGASTREAM");
						
						if(media!=null)
						{
							String returnString = currentUser.getUsername() + ",";
							returnString+= media.getTitle() + ",";
							String choice = StaffMediaItemDetails(media);
							if(!choice.equals("Cancel")) 
							{
								if(choice.equals("Edit"))
								{
									
									// ItemToUpdate(media);

									//userMenu.userActions(returnedSelection, updateItem);
								}
								
								else if(choice.equals("Remove"))
								{
									
								}
							}
						}
					}
				}
			}
		}
		
		
	

		public String addNewUser() throws IOException
		{			
			// Don't think we need this
			UserClass newUser = null;
			
			
			String userString="",userName = "", type="";
			double startingBalance = 0.0;
			type = getTypeInput();
			userString += type + ",";
			
			// Don't think we need this
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
				else if(databaseFetcher.getUserByName(userName) != null)
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
				String type = null, userName = null, name = null, password = null, email = null, phoneNumber = null, address = null;
				boolean validUser=false, isCustomer=false;
				while(!validUser)
				{
					String userToUpdateUserName = getUserToModify_UsernameInput();
					UserClass userToUpdate = databaseFetcher.getUserByName(userToUpdateUserName);
					if(userToUpdate != null)
					{
						validUser=true;
						if(userToUpdate.getType().equalsIgnoreCase("CUSTOMER"))
							isCustomer = true;
						updateString += userToUpdateUserName + ",";
					}
					else
						JOptionPane.showMessageDialog(null, "Not a valid username", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
				String pieceToUpdate = showUserModificationMenu(isCustomer);
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
				
				else if(pieceToUpdate.matches("Address"))
				{
					address = getAddressInput();
					updateString += "Address," + address;
				}
				
				
				
				// One piece of info can be changed at once, so the update string consists of 3 comma seperated values :
				// 1)The username of the user that is being updated
				// 2)The type of information that is being updated, such as username, email, or phone number
				// 3)The new value
				return updateString;
			}
			
			public void browseMediaList(){
				ArrayList<MediaItem> mediaList=null;
				mediaList = databaseFetcher.getMediaItems();
				boolean stillSearching = true, firstFive = false;
				int k = 0;
				String selectedItem = "";
				int remainder = mediaList.size()%5;
				if(mediaList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "There are no media items in the database!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				while(stillSearching)
				{
					firstFive = false;
					if(mediaList.size() <= 5)
					{
						if(mediaList.size() == 5)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 1)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 2)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 3)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 4)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
					}
					if(!firstFive)
					{
						if(k == 0)
						{	
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Show Next 5", "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
						}
						else if(k == mediaList.size()-remainder)
						{
							if(remainder == 1)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 2)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 3)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 4)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
						}
						else
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k + 1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k + 2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k + 3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k + 4).getTitle(), "Show Previous 5", "Show Next 5", "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
						}
					}
					
					if(selectedItem.equals("Quit"))
						stillSearching=false;
					else if(selectedItem.equals("Show Next 5"))
					{
						k += 5;
					}
					else if(selectedItem.equals("Show Previous 5"))
					{
						k -= 5;
					}
					 else 
					{
						stillSearching=false;
						boolean filmFound=false;
						for(int i = 0; i < mediaList.size() && !filmFound; i++)
						{
							if(selectedItem.equals(mediaList.get(i).getMediaType()+" - " + mediaList.get(i).getTitle()))
							{
								filmFound = true;
							//	return mediaList.getTitle(i);
								customerMediaItemDetailsAndReturnedChoice(mediaList.get(i));
							}
						}
					} 
				}
				return;
			}
			public MediaItem browseStaffMediaList()
			{
				ArrayList<MediaItem> mediaList=null;
				MediaItem item = null;
				mediaList = databaseFetcher.getMediaItems();
				boolean stillSearching = true, firstFive = false;
				int k = 0;
				String selectedItem = "";
				int remainder = mediaList.size()%5;
				if(mediaList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "There are no media items in the database!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				while(stillSearching)
				{
					firstFive = false;
					if(mediaList.size() <= 5)
					{
						if(mediaList.size() == 5)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 1)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 2)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 3)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 4)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
					}
					if(!firstFive)
					{
						if(k == 0)
						{	
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Show Next 5", "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
						}
						else if(k == mediaList.size()-remainder)
						{
							if(remainder == 1)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 2)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 3)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 4)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
						}
						else
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k + 1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k + 2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k + 3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k + 4).getTitle(), "Show Previous 5", "Show Next 5", "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
						}
					}
					
					if(selectedItem.equals("Quit"))
						stillSearching=false;
					else if(selectedItem.equals("Show Next 5"))
					{
						k += 5;
					}
					else if(selectedItem.equals("Show Previous 5"))
					{
						k -= 5;
					}
					 else 
					{
						stillSearching=false;
						boolean filmFound=false;
						for(int i = 0; i < mediaList.size() && !filmFound; i++)
						{
							if(selectedItem.equals(mediaList.get(i).getMediaType()+" - " + mediaList.get(i).getTitle()))
							{
								item = databaseFetcher.getMediaItemByName(mediaList.get(i).getTitle());
								filmFound = true;
								//StaffMediaItemDetails(item);
							}
						}
					} 
				}
				return item;
			}
			
			public MediaItem ViewSupplierCatalogue()
			{
				ArrayList<MediaItem> mediaList=null;
				MediaItem item = null;
				mediaList = databaseFetcher.getSupplierItems();
				boolean stillSearching = true, firstFive = false;
				int k = 0;
				String selectedItem = "";
				int remainder = mediaList.size()%5;
				if(mediaList.size() == 0)
				{
					JOptionPane.showMessageDialog(null, "There are no media items in supplier catalogue!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				while(stillSearching)
				{
					firstFive = false;
					if(mediaList.size() <= 5)
					{
						if(mediaList.size() == 5)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 1)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 2)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 3)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
						else if(remainder == 4)
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							firstFive = true;
						}
					}
					if(!firstFive)
					{
						if(k == 0)
						{	
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Show Next 5", "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
						}
						else if(k == mediaList.size()-remainder)
						{
							if(remainder == 1)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 2)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 3)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
							else if(remainder == 4)
							{
								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Show Previous 5", "Quit"};
								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
							}
						}
						else
						{
							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k + 1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k + 2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k + 3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k + 4).getTitle(), "Show Previous 5", "Show Next 5", "Quit"};
							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
						}
					}
					
					if(selectedItem.equals("Quit"))
						stillSearching=false;
					else if(selectedItem.equals("Show Next 5"))
					{
						k += 5;
					}
					else if(selectedItem.equals("Show Previous 5"))
					{
						k -= 5;
					}
					 else 
					{
						stillSearching=false;
						boolean filmFound=false;
						for(int i = 0; i < mediaList.size() && !filmFound; i++)
						{
							if(selectedItem.equals(mediaList.get(i).getMediaType()+" - " + mediaList.get(i).getTitle()))
							{
								item = databaseFetcher.getSupplierItemByName(mediaList.get(i).getTitle());
								filmFound = true;
							}
						}
					} 
				}
				return item;
		}

			
			public String ItemToUpdate()
			{
				String updateString="";
				String release = null, price = null, rating = null;									   
				boolean isStaff=false;
				while(!isStaff)
				{	
					String pieceToUpdate = showItemModificationMenu(isStaff);

					 if(pieceToUpdate.matches("Release"))
					{
						release = getReleaseInput();
						updateString += "Release," + release;
					}
					
					else if(pieceToUpdate.matches("Price"))
					{
						price = getPriceInput();
						updateString += "Price," + price;
						isStaff=true;
					}
					
					else if(pieceToUpdate.matches("Rating"))
					{
						rating = getRatingInput();
						updateString += "Rating," + rating;
					}
					
					// One piece of info can be changed at once, so the update string consists of 3 comma seperated values :
					// 1)The username of the user that is being updated
					// 2)The type of information that is being updated, such as username, email, or phone number
					// 3)The new value
			}
				return updateString;
		}
			
			public String ItemToRemove()
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
			
			public String viewCustomersMediaRepository(int userID)
			{
				ArrayList<MediaItem> mediaItems = databaseFetcher.getCustomersMediaRepository(userID);
				Object[] fullListToDisplay = new Object [mediaItems.size() + 1];
				for(int i=0; i<mediaItems.size(); i++)
				{
					fullListToDisplay[i] = mediaItems.get(i).getTitle() + " - " + mediaItems.get(i).getMediaType();
					
					
				}
				fullListToDisplay[mediaItems.size()] = "Cancel";
				String selection = (String) JOptionPane.showInputDialog(null, "Choose Media Item", "Customer : " + currentUser.getName(), 1 , null, fullListToDisplay, fullListToDisplay[0]);
				return selection.substring(0, selection.indexOf("-")-1);
			}

			
			
	
	
	//		 Menus
	public String showAdminMenu() {
		Object [] selection = {"Add User", "Delete User", "Update User", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Admin : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	
	//new John
	public String showCustomerMenu() {
		Object [] selection = {"Browse Media List", "Search By Category","View Shopping Cart", "Search for Media Item", "View Media Repository", "Add Funds to Wallet", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showStaffMenu() {
		Object [] selection = {"View Catalogue", "View Supplier Catalogue", "Search Media Item", "Add Promotion", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Staff Member : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showSupplierMenu() {
		Object [] selection = {"Add new film to Supplier Catalogue", "View Supplier Catalogue", "Search for film in Supplier Catalogue", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Supplier : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showUserModificationMenu(boolean isCustomer) {
		Object [] selection = null;
		String[] userArr = {"User type", "Username", "Name", "Password", "Email", "Phone Number"};
		String[] customerArr = {"User type", "Username", "Name", "Password", "Email", "Phone Number", "Address"};
		if(isCustomer)
			selection = customerArr;
		else
			selection = userArr;
		return (String) JOptionPane.showInputDialog(null, "What do you want to modify?","Admin : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showItemModificationMenu(boolean isStaff) {
		Object [] selection = null;
		String[] itemArr = {"Release", "Price","Rating"};
		selection = itemArr;

		return (String) JOptionPane.showInputDialog(null, "What do you want to modify?","Staff : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	
	
	//    Inputs
	public String getTypeInput()
	{
		Object [] typeSelection = {"Admin", "Customer", "Staff"};
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
	
	public String getItemTypeInput()
	{
		return JOptionPane.showInputDialog(null, "Type:");
	}
	
	public String getItemNameInput()
	{
		return JOptionPane.showInputDialog(null, "Name:");
	}
	
	public String getIDInput()
	{
		return JOptionPane.showInputDialog(null, "ID:");
	}
	
	public String getDirectorInput()
	{
		return JOptionPane.showInputDialog(null, "Director:");
	}
	
	public String getGenreInput()
	{
		return JOptionPane.showInputDialog(null, "Genre:");
	}
	
	public String getReleaseInput()
	{
		return JOptionPane.showInputDialog(null, "Release:");
	}
	
	public String getPriceInput()
	{
		return JOptionPane.showInputDialog(null, "Price:");
	}
	
	public String getDescriptionInput()
	{
		return JOptionPane.showInputDialog(null, "Description:");
	}
	
	public String getRatingInput()
	{
		return JOptionPane.showInputDialog(null, "Rating:");
	}
	
	public String getFormatInput()
	{
		return JOptionPane.showInputDialog(null, "Format:");
	}
	
	public MediaItem searchforItem(String mediaItemType) throws FileNotFoundException
	{
		MediaItem item=null;
		boolean validMedia=false;
		while (!validMedia)
		{
			String nameOfItem= JOptionPane.showInputDialog(null,"Enter name of media item: ");
			if (mediaItemType.equalsIgnoreCase("MEGASTREAM"))
					item= databaseFetcher.getMediaItemByName(nameOfItem);
			else if(mediaItemType.equalsIgnoreCase("SUPPLIER"))
				item=databaseFetcher.searchSuppliersDatabase(nameOfItem);
			
			if(item!=null)
				validMedia=true;
			else if(item==null)
			{
				JOptionPane.showMessageDialog(null, "Item not found in database!", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}

		}
		return item;
	}
	
	public String customerMediaItemDetailsAndReturnedChoice(MediaItem media)
	{
		Object [] selection = {"Rent Film", "Buy Film", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, media.toString(),"Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String StaffMediaItemDetails(MediaItem media)
	{
		Object [] selection = {"Edit", "Remove", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, media.toString(),"Staff : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String SupplierItemDetails(MediaItem media)
	{
		Object [] selection = {"Add to Catalogue", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, media.toString(),"Staff : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String WhichCatalogue()
	{
		Object [] selection = {"Search Our Catalogue", "Search Supplier Catalogue", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Method of Payment?", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String paymentMethod()
	{
		Object [] selection = {"Credit Card", "Wallet", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Method of Payment?", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String confirmPurchase()
	{
		Object [] selection = {"Confirm Purchase", "Cancel", "Exit"};
		return (String) JOptionPane.showInputDialog(null, "Please confirm purchase", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
		
	}
	
	public void displayReceipt(MediaItem media) throws IOException
	{
		I_Receipt receipt= new ReceiptA();
		receipt= new CustomerDecorator(receipt);
		JOptionPane.showMessageDialog(null,receipt.PrintReceipt(media.getTitle()));
		userActionMenu();
	}
	
	public String amountToAddToWallet()
	{
		Object [] selection = {"€100", "€50", "€20", "€10", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Choose amount to add to wallet", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}

}
