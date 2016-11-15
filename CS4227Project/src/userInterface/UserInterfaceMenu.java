package userInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import payment.*;
import media.GameClass;
import media.MediaItem;
import media.PlatformChoice;
import program.DatabaseFetcher;
import program.I_Receiver;
import program.TypeOfFactoryGenerator;
import streaming.StreamMedia;
import users.C_AdminActions;
import users.C_CustomerActions;
import users.C_StaffActions;
import users.CustomerClass;
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
    Originator originator = new Originator();
    Caretaker careTaker = new Caretaker();
    int numberOfStates = 0, currentState = 0;
    
	// concrete Command and Receiver
	String concreteCommandName = null;
	String concreteReceiverName = null;
	
	
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
		newActionMenu();
	}
	
	public void newActionMenu() throws IOException
	{
		boolean stillLoggedIn = true;
		String returnedMenuSelection = "";
		
		// this checks what type of users you are
		String type = currentUser.getType();
		
		if(type.equalsIgnoreCase("Admin"))
		{
			while(stillLoggedIn)
			{
				returnedMenuSelection = showAdminMenu();

				if(returnedMenuSelection.equalsIgnoreCase("Logout")){
					stillLoggedIn = false;
				}
				else
				{
					adminMenuChoices(returnedMenuSelection);
				}
			}
		}
		
		if(type.matches("Customer"))
		{
			CustomerClass customer = ((CustomerClass)currentUser);
			customer.setCart(databaseFetcher.initializeUsersShoppingCart(Integer.toString(customer.getUserID())));
			while(stillLoggedIn)
			{
				// this shows the Customer dropdown menu and returns the selection from it
				returnedMenuSelection = showCustomerMenu();
				
				if(returnedMenuSelection.equalsIgnoreCase("Browse Media Catalogue"))
				{
					MediaItem item = browseMediaList("OUR_DATABASE");
					if(item!=null)
						purchasingOptions(item);
				}
				
				else if(returnedMenuSelection.equalsIgnoreCase("Search for Media Item"))
				{
					MediaItem media = searchforItem("MEGASTREAM");

					// move purchasing code to a separate purchasingOptions(media) function that browse media can use too
					// purchasingOpions(media) will return the info string that gets passed to invoker
					
					if(media!=null)
					{
						// infoString : username_mediaTitle_purchaseType_paymentOption
						String infoString = currentUser.getUsername() + ",";
						infoString += media.getTitle() + ",";
						
						purchasingOptions(media);

					}
				}
				
				else if(returnedMenuSelection.equalsIgnoreCase("Activate Promotion"))					
				{	
					String choice = displayItemsFromCart(customer);		
					customer.getCart().updatePrice(databaseFetcher.getMediaItemByName(choice));
				}
				
				else if(returnedMenuSelection.equalsIgnoreCase("View Shopping Cart"))					
				{
					
					String choice = displayShoppingCartMenu();
					// userMenu.userActions(returnedSelection, choice);
					if (choice.equals("Display Items")) {
					
						String mediaTitle = displayItemsFromCart(customer);
					}

					
					//
					else if (choice.equals("Delete Items")) {
						String infoString = "";
						boolean done = false;
						do {
							
							String select = displayItemsFromCart(customer);
							if(!select.equals("Cancel"))
							{
								infoString = currentUser.getUsername() + "," + select + "," + "0";
								
								concreteCommandName = "CF_ChangeQuantityCommand";
								concreteReceiverName = "CartOperation";
								executeInvoker(infoString, concreteCommandName, concreteReceiverName);
								
							}
							//so, this was going to jsut check if they were done but I'd say I just but in a boolean when implementing it. We're going to find a lot of those
							// ok cool so this is the method that I have extracted :) i'm gonna just have this one method for browse update qty and delete!
							done = true;
						} while (!done);
					}

					else if (choice.equals("Change Quantity of an Item")) {
						String qty;
					
						choice = displayItemsFromCart(customer);
						String changeQuantityCommand = choice;
						boolean valid = false;
						boolean changeDB = false;
						do {
							qty = (String) JOptionPane.showInputDialog(null,
									"What quantity would you like to change " + choice + " to?");
							// gets qty, chnage to int
							if (Integer.parseInt(qty) < 0) {
								JOptionPane.showMessageDialog(null, "You cannot enter a quantity less than 0!",
										"Quantity Input Error", JOptionPane.ERROR_MESSAGE);
							}

							else if (Integer.parseInt(qty) == 0) {
								int response = JOptionPane.showConfirmDialog(null,
										"This will remove the Item from your shopping cart. Do you wish to continue?",
										"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (response == JOptionPane.NO_OPTION || response == JOptionPane.CANCEL_OPTION) {
									valid = true;
								} else if (response == JOptionPane.YES_OPTION) {
									valid = true;
									changeDB = true;
								}
								else
									valid = false;
							}

							else if (qty == "" || qty == null || qty.length() <= 0)
								valid = false;
							else {
								valid = true;
								changeDB = true;
							}
						} while (!valid);
						if(changeDB){
							String infoString = currentUser.getUsername() + "," + choice + "," + qty;
							concreteCommandName = "CF_ChangeQuantityCommand";
							concreteReceiverName = "CartOperation";
							executeInvoker(infoString, concreteCommandName, concreteReceiverName);
						
						}
						
						
						changeQuantityCommand += "," + qty; 
					}

					else if (choice.equals("Checkout")) {
						// option to buy or cancel
						String message = "";
						String infoString = "";
						for (Map.Entry<MediaItem, String> entry : customer.getCart().getCartList().entrySet()) {

							message += entry.getKey().getTitle() + "      Quantity: " + entry.getValue()
							+ "\t          €" + entry.getKey().getPrice() + "\n";

						}
						message += "\nTotal Discount : € " + (String.format("%.2f",customer.getCart().getDiscountTotal())) + "\nTotal Price is : € " + customer.getCart().getTotalCost();
						JOptionPane.showMessageDialog(null, message);

						JFrame frame = new JFrame();

						Object stringArray[] = { "Ship to Address", "Cancel" };
						int response = JOptionPane.showOptionDialog(frame,
								"All Items will be shipped to registered Address!",
								"Select an Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								stringArray, stringArray[0]);

						if (response == 0) {
							// ship all items to address
							{
								infoString = customer.getUsername()+","+customer.getUserID()+","+"Ship to Address"+",";
								
									//TODO! take out this choice : don't use Credit card anymore
									String paymentChoice = paymentMethod();
									if(!paymentChoice.equals("Cancel"))
									{
										infoString += paymentChoice;
										
										concreteCommandName = "CF_CheckoutShoppingCartCommand";
										concreteReceiverName = "CartOperation";
										executeInvoker(infoString, concreteCommandName, concreteReceiverName);
										
										
									}
							}
						} else if (response == JOptionPane.CLOSED_OPTION || response == 1) {
							break;
						}

					} else if (choice.equals("Clear Shopping Cart")) {
						int response = JOptionPane.showConfirmDialog(null,
								"This will clear all Items in your shopping cart!! Do you want to continue?", "Confirm",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.NO_OPTION) {
							break;
						} else if (response == JOptionPane.YES_OPTION) {
							concreteCommandName = "CF_ClearCartCommand";
							concreteReceiverName = "CartOperation";
							executeInvoker(currentUser.getUsername(), concreteCommandName, concreteReceiverName);
						} else if (response == JOptionPane.CLOSED_OPTION)
							break;
					}

					else if (choice.equals("Quit")) {
						break;
					}
				}
				else if(returnedMenuSelection.equalsIgnoreCase("View Media Repository"))
				{
					//to be filled
					//String returnString = currentUser.getUserID() + ",";
					int userID = currentUser.getUserID();
					String chosenMediaItemName = viewCustomersMediaRepository(userID);
					if(!chosenMediaItemName.equals("Cancel"))
					{	
						concreteCommandName = "CF_StreamMediaCommand";
						concreteReceiverName = "StreamMedia";
						executeInvoker(chosenMediaItemName, concreteCommandName, concreteReceiverName);
						
					}
				}
				else if(returnedMenuSelection.equalsIgnoreCase("Add Funds to Wallet"))
				{
					String username= currentUser.getUsername();
					String ammount= amountToAddToWallet();
					
					
					if(ammount!= "Cancel")
					{
						String confirmation= confirmPurchase();
						if(confirmation!= "Cancel")
						{
							String id_amount= username + "," + ammount;
							
							concreteCommandName = "CF_AddFundsToWalletCommand";
							concreteReceiverName = "AddToWallet";
							executeInvoker(id_amount, concreteCommandName, concreteReceiverName);
						}
					}
				}
				
				else if(returnedMenuSelection.equalsIgnoreCase("View Profile"))
				{
					
				}
				else if(returnedMenuSelection.equals("Logout")){
					//this will break the loop and log user out
					stillLoggedIn = false;
				}
			}
		}
		else if(type.equalsIgnoreCase("Staff"))
		{
			while(stillLoggedIn)
			{
				returnedMenuSelection = showStaffMenu();

				if(returnedMenuSelection.equalsIgnoreCase("Logout")){
					stillLoggedIn = false;
				}
				else
				{
					staffMenuChoices(returnedMenuSelection);
				}
			}
		}
	}
	
	public void adminMenuChoices(String menuSelection) throws IOException
	{
		if(menuSelection.equalsIgnoreCase("Add User"))
		{
			// calls the method to get the user input for a new user
			String userToCreate = addNewUser();
			
			concreteCommandName = "AF_AddNewUserCommand";
			concreteReceiverName = "C_AdminActions";
			executeInvoker(userToCreate, concreteCommandName, concreteReceiverName);
		
		}
		
		else if(menuSelection.equalsIgnoreCase("Delete User"))
		{
			// calls the method to get the name of the user you want to delete
			String userToRemove = UserToRemove();
			
			concreteCommandName = "AF_RemoveUserCommand";
			concreteReceiverName = "C_AdminActions";
			executeInvoker(userToRemove, concreteCommandName, concreteReceiverName);
			
		}
		
		else if(menuSelection.equalsIgnoreCase("Update User"))
		{
			//  calls the method to get the user to be updated, what part is being updated, and the new value
			String updateUser = UserToUpdate();
			
			concreteCommandName = "AF_UpdateUserCommand";
			concreteReceiverName = "C_AdminActions";
			executeInvoker(updateUser, concreteCommandName, concreteReceiverName);
			
		}
	}
	
	public void staffMenuChoices(String menuSelection) throws IOException
	{
		if(menuSelection.equals("View Catalogue"))
		{
			String viewCatalogue = ViewWhichCatalogue();
			if (!viewCatalogue.equals("Cancel"))
			{
				if (viewCatalogue.equals("View Our Catalogue"))
				{					
					MediaItem media = browseMediaList("OUR_DATABASE");
					if(media!=null)
					{
						staffOptionsOurCatalogue(media);
					}
				}
				else if (viewCatalogue.equals("View Supplier Catalogue"))
				{
					MediaItem media = browseMediaList("SUPPLIERS_DATABASE");
					staffOptionsSuppliersCatalogueS(media);
				}	
			}
		}
		else if(menuSelection.equals("Search Media Item"))
		{
			String catalogue = SearchWhichCatalogue();
			if (!catalogue.equals("Cancel"))
			{
				if (catalogue.equals("Search Our Catalogue"))
				{
					MediaItem media = searchforItem("MEGASTREAM");

					if(media!=null)
					{
						staffOptionsOurCatalogue(media);
					}
				}
				else if (catalogue.equals("Search Supplier Catalogue"))
				{
					MediaItem media = searchforItem("SUPPLIER");
					staffOptionsSuppliersCatalogueS(media);
				}
			}
		}
	}
	
	
	/**
	 * @param paramString
	 * @param concreteCommandName
	 * @param concreteReceiverName
	 */
	private void executeInvoker(String paramString, String concreteCommandName, String concreteReceiverName) {
		System.out.println("ParamString : " + paramString + "\nCommand : " + concreteCommandName + "\nReceiver : " + concreteReceiverName);
		I_Command concreteCommand = TypeOfFactoryGenerator.getFactory("COMMAND").getCommand(concreteCommandName);
		I_Receiver concreteReceiver = TypeOfFactoryGenerator.getFactory("RECEIVER").getReceiver(concreteReceiverName);
		invoker.setCommand(concreteCommand.setConcreteCommand(concreteReceiver));
		invoker.optionSelectedWithStringParam(paramString);
	}
	
	/**
	 * @param media
	 * @return
	 */
	private void staffOptionsOurCatalogue(MediaItem media) {
		boolean viewing = true;
		while(viewing)
		{
			String returnString = currentUser.getUsername() + ",";
			returnString+= media.getTitle() + ",";
			String choice = StaffMediaItemDetails(media);

			if(!choice.equals("Return to Menu")) 
			{

				if(choice.equals("Edit"))
				{
					originator.setState(media.toFileString());
					returnString+= ItemToUpdate();

					concreteCommandName = "SF_UpdateItemCommand";
					concreteReceiverName = "C_StaffActions";
					executeInvoker(returnString, concreteCommandName, concreteReceiverName);
					
					careTaker.addMemento(originator.saveStateToMemento());
					numberOfStates++;
					currentState++;

				}
				//browse
				else if(choice.equals("Remove"))
				{
					concreteCommandName = "SF_RemoveItemCommand";
					concreteReceiverName = "C_StaffActions";
					executeInvoker(returnString, concreteCommandName, concreteReceiverName);
					

					viewing = false;
				}
				else if (choice.equals("Undo"))
				{
					if(currentState >= 1)
					{
						currentState--;
						String oldState = originator.restoreFromMemento(careTaker.getMemento(currentState));
						
						concreteCommandName = "SF_UndoCommand";
						concreteReceiverName = "C_StaffActions";
						executeInvoker(oldState, concreteCommandName, concreteReceiverName);
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Cannot perform operation!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if (choice.equals("Redo"))
				{
					if((numberOfStates -1) > currentState)
					{
						currentState++;
						String redoState = originator.restoreFromMemento(careTaker.getMemento(currentState));
						
						concreteCommandName = "SF_UndoCommand";
						concreteReceiverName = "C_StaffActions";
						executeInvoker(redoState, concreteCommandName, concreteReceiverName);
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Cannot perform operation!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
			else //Returns back to menu
			{
				viewing = false;
			}
		}
	}

	/**
	 * @param media
	 */
	private void staffOptionsSuppliersCatalogueS(MediaItem media) {
		boolean viewing = true;
		if(media!=null)
		{					
			while(viewing)
			{
				String returnString = currentUser.getUsername() + ",";
				returnString+= media.getTitle() + ",";
				String choice = SupplierItemDetails(media);
				if(!choice.equals("Cancel"))
				{
					if (choice.equals("Add to Catalogue"))
					{
						concreteCommandName = "SF_AddItemCommand";
						concreteReceiverName = "C_StaffActions";
						executeInvoker(returnString, concreteCommandName, concreteReceiverName);
						
					}
					else if (choice.equals("Return to Menu"))
					{
						viewing = false;
					}
				}
			}
		}
	}



	/**
	 * @param customer
	 */
	private String displayItemsFromCart(CustomerClass customer) {
		Map<MediaItem, String> cartList;
		cartList = customer.getCart().getCartList();
		int i = 0;
		Object[] fullListToDisplay = new Object[cartList.size() + 1];
		for (Map.Entry<MediaItem, String> entry : cartList.entrySet()) {
			fullListToDisplay[i] = entry.getKey().getMediaType() + " - " + entry.getKey().getTitle();
			i++;
		}
		fullListToDisplay[cartList.size()] = "Cancel";
		String selection = (String) JOptionPane.showInputDialog(null, "Choose Media Item",
				"Customer : " + currentUser.getName(), 1, null, fullListToDisplay,
				fullListToDisplay[0]);
		if(!selection.equals("Cancel"))
			selection = selection.substring(selection.indexOf("-") + 2);
		return selection;
	}

	
	private String displayShoppingCartMenu() {
		Object[] choices = { "Display Items", "Delete Items", "Change Quantity of an Item", "Checkout",
				"Clear Shopping Cart", "Quit" };
		return (String) JOptionPane.showInputDialog(null, "Shopping Cart Menu!", "", 1, null,
				choices, choices[0]);
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
			
			public MediaItem browseMediaList(String whichCatalogue){

				ArrayList<MediaItem> mediaList=null;
				MediaItem item = null;
				if (whichCatalogue.equalsIgnoreCase("OUR_DATABASE"))
					mediaList= databaseFetcher.getMediaItems();
				
				else if(whichCatalogue.equalsIgnoreCase("SUPPLIERS_DATABASE"))
					mediaList = databaseFetcher.getSupplierItems();
				
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
								item = mediaList.get(i);

							}
						}
					} 
				}
				return item;
				
			}
			
			
			//REFACTORING_DONE_HERE
//			public MediaItem browseMediaList(String whichCatalogue)
//			{
//				ArrayList<MediaItem> mediaList=null;
//				MediaItem item = null;
//				if (whichCatalogue.equalsIgnoreCase("OUR_DATABASE")
//					mediaList= databaseFetcher.getMediaItems();
//				
//				else if(whichCatalogue.equalsIgnoreCase("Supplier"))
//					mediaList = databaseFetcher.getSupplierItems();
//				
//				//mediaList = databaseFetcher.getMediaItems();
//				boolean stillSearching = true, firstFive = false;
//				int k = 0;
//				String selectedItem = "";
//				int remainder = mediaList.size()%5;
//				if(mediaList.size() == 0)
//				{
//					JOptionPane.showMessageDialog(null, "There are no media items in the database!", "Error", JOptionPane.ERROR_MESSAGE);
//				}
//				while(stillSearching)
//				{
//					firstFive = false;
//					if(mediaList.size() <= 5)
//					{
//						if(mediaList.size() == 5)
//						{
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							firstFive = true;
//						}
//						else if(remainder == 1)
//						{
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							firstFive = true;
//						}
//						else if(remainder == 2)
//						{
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							firstFive = true;
//						}
//						else if(remainder == 3)
//						{
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							firstFive = true;
//						}
//						else if(remainder == 4)
//						{
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							firstFive = true;
//						}
//					}
//					if(!firstFive)
//					{
//						if(k == 0)
//						{	
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k+4).getTitle(), "Show Next 5", "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//						}
//						else if(k == mediaList.size()-remainder)
//						{
//							if(remainder == 1)
//							{
//								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), "Show Previous 5", "Quit"};
//								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							}
//							else if(remainder == 2)
//							{
//								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), "Show Previous 5", "Quit"};
//								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							}
//							else if(remainder == 3)
//							{
//								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), "Show Previous 5", "Quit"};
//								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							}
//							else if(remainder == 4)
//							{
//								Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k+1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k+2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k+3).getTitle(), "Show Previous 5", "Quit"};
//								selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//							}
//						}
//						else
//						{
//							Object [] selection = {mediaList.get(k).getMediaType()+" - " + mediaList.get(k).getTitle(), mediaList.get(k+1).getMediaType()+" - " + mediaList.get(k + 1).getTitle(), mediaList.get(k+2).getMediaType()+" - " + mediaList.get(k + 2).getTitle(), mediaList.get(k+3).getMediaType()+" - " + mediaList.get(k + 3).getTitle(), mediaList.get(k+4).getMediaType()+" - " + mediaList.get(k + 4).getTitle(), "Show Previous 5", "Show Next 5", "Quit"};
//							selectedItem = (String) JOptionPane.showInputDialog(null, "Film List", "Please Select A Film",1, null, selection, selection[0]);
//						}
//					}
//					
//					if(selectedItem.equals("Quit"))
//						stillSearching=false;
//					else if(selectedItem.equals("Show Next 5"))
//					{
//						k += 5;
//					}
//					else if(selectedItem.equals("Show Previous 5"))
//					{
//						k -= 5;
//					}
//					 else 
//					{
//						stillSearching=false;
//						boolean filmFound=false;
//						for(int i = 0; i < mediaList.size() && !filmFound; i++)
//						{
//							if(selectedItem.equals(mediaList.get(i).getMediaType()+" - " + mediaList.get(i).getTitle()))
//							{
//								if (whichCatalogue.equalsIgnoreCase("Staff"))
//								{
//									item = databaseFetcher.getMediaItemByName(mediaList.get(i).getTitle());
//									filmFound = true;
//								}
//								else if (whichCatalogue.equalsIgnoreCase("Supplier"))
//								{
//									item = databaseFetcher.getSupplierItemByName(mediaList.get(i).getTitle());
//									filmFound = true;
//								}
//								else if (whichCatalogue.equalsIgnoreCase("Customer"))
//								{
//									purchasingOptions(mediaList.get(i));
//									filmFound = true;
//								}
//							}
//						}
//					} 
//				}
//				return item;
//			}
			
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
						isStaff=true;
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
						isStaff=true;
					}
					
					// One piece of info can be changed at once, so the update string consists of 3 comma seperated values :
					// 1)The username of the user that is being updated
					// 2)The type of information that is being updated, such as username, email, or phone number
					// 3)The new value
			}
				return updateString;
		}
			
			
			//REFACTORING_DONE_HERE  --- don't think this method is ever called
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
				if(!selection.equals("Cancel"))
					selection = selection.substring(0, selection.indexOf("-")-1);
				return selection;
			}

			
			
	
	
	//		 Menus
	public String showAdminMenu() {
		Object [] selection = {"Add User", "Delete User", "Update User", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Admin : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	
	//new John
	public String showCustomerMenu() {
		Object [] selection = {"Browse Media Catalogue", "Search for Media Item", "View Shopping Cart", "Activate Promotion", "View Media Repository", "Add Funds to Wallet", "View Profile", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String showStaffMenu() {
		Object [] selection = {"View Catalogue", "Search Media Item", "Logout"};
		return (String) JOptionPane.showInputDialog(null, "What action would you like to perform?","Staff Member : " + currentUser.getName(), 1 , null, selection, selection[0]);
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
	
	public void purchasingOptions(MediaItem media)
	{
		String infoString = currentUser.getUsername() + ",";
		infoString += media.getTitle() + ",";
		Object [] selection = {"Buy Media Item", "Add To Cart", "Cancel"};
		String opt= (String) JOptionPane.showInputDialog(null, media.toString(),"Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
		
		if(opt.equalsIgnoreCase("Buy Media Item"))
		{
			if(media.getMediaType().equalsIgnoreCase("GAME"))
			{
			/*	String choice4= */getChoicePlatform(media);
		/*		if(!choice4.equals("Cancel"))
				{	
					String returnChoice= infoString + choice4 + ","; 
					platform=true;
					invoker.setCommand(new CF_ChoosePlatformCommand(new PlatformChoice()));
					invoker.optionSelectedWithStringParam(returnChoice);
				}*/	
			}
			String purchaseType = purchaseType(media);
			if(!purchaseType.equals("Cancel"))
			{
				infoString += purchaseType.substring(0, purchaseType.indexOf("-")-1) + ",";
			
				//TODO! take out this choice : don't use Credit card anymore
				String paymentChoice = paymentMethod();
				if(!paymentChoice.equals("Cancel"))
				{
					infoString += paymentChoice;
			
					concreteCommandName = "CF_BuyMediaItemCommand";
					concreteReceiverName = "Payment";
					executeInvoker(infoString, concreteCommandName, concreteReceiverName);
				}
			}
		}
		else if(opt.equalsIgnoreCase("Add To Cart"))
		{		CustomerClass customer = ((CustomerClass)currentUser);
					String qty;
					//checkl if the item exists.
					
					if(((CustomerClass) currentUser).getCart().checkIfItemExists(media))
					{
						int response = JOptionPane.showConfirmDialog(null, "The Item already exists in your basket. Do you want to change the quantity?", "Confirm",
						        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						System.out.println(media.getPrice());
						    if (response == JOptionPane.NO_OPTION) {
						    } else if (response == JOptionPane.YES_OPTION) {
						    	boolean valid = false;
						    	do{
						    	qty = (String) JOptionPane.showInputDialog(null, "What quantity would you like?");
						    	//gets qty, chnage to int
						    	if(Integer.parseInt(qty) < 0 ){
						    		JOptionPane.showMessageDialog(null,
										    "You cannot enter a quantity less than 0!",
										    "Quantity Input Error",
										    JOptionPane.ERROR_MESSAGE);
						    	}
						    	
						    	else if(Integer.parseInt(qty) == 0){
						    		response = JOptionPane.showConfirmDialog(null, "This will remove the Item from your shopping cart. Do you wish to continue?", "Confirm",
									        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						    		if (response == JOptionPane.NO_OPTION) {
									      valid = false;
									    }
						    		else if (response == JOptionPane.YES_OPTION){
						    			customer.getCart().removeItem(databaseFetcher.getMediaItemByName(media.getTitle()));
						    			valid = true;
						    		}
						    		else
						    			valid = false;
						    	}
						    	else{
						    		valid = true;
						    	}
						    	}while(!valid);
						    	customer.getCart().updateQuantity(customer.getCart().getMediaItemByName(media.getTitle()), qty);
						    	databaseFetcher.updateShoppingCartFile(Integer.toString(currentUser.getUserID()), media.getMediaID() ,qty);
				    			
						    }
						    else if (response == JOptionPane.CLOSED_OPTION) {
						    }
					}
					else
					{
						MediaItem gameToCart = TypeOfFactoryGenerator.getFactory("MEDIA").getMediaItem("GAME");
						boolean platform=false;
						// TODO! invoker code here for add to cart functionality 
						if(media.getMediaType().equalsIgnoreCase("GAME"))
						{
							getChoicePlatform(media);
							platform= true;
							gameToCart = gameToCart.createMediaItem(media.toFileString());
						}

						if(platform==true)
						{
							GameClass item= (GameClass)media;
							PlatformChoice pChoice= new PlatformChoice();
							pChoice.nullPLatform(item);
						}
						
						qty = (String) JOptionPane.showInputDialog(null, "How many would you like to add?");
						
						//need to check for qantity is valid
						
						if(!(media.getMediaType().equalsIgnoreCase("GAME")))
						{	
							customer.getCart().addItem(media, qty);
							databaseFetcher.updateShoppingCartFile(Integer.toString(currentUser.getUserID()), databaseFetcher.getMediaItemByName(media.getTitle()).getMediaID() ,qty);
						}
						else if(media.getMediaType().equalsIgnoreCase("GAME"))
						{
							
							customer.getCart().addItem(gameToCart, qty);
						databaseFetcher.updateShoppingCartFile(Integer.toString(currentUser.getUserID()), databaseFetcher.getMediaItemByName(gameToCart.getTitle()).getMediaID() ,qty);
						}
						
					}
		}
		else if(opt.equalsIgnoreCase("Cancel") || opt == null || opt.length()<0){
		}
			
	}
	
//	public String customerMediaItemDetailsAndReturnedChoice(MediaItem media)
//	{
//		Object [] selection = {"Rent Film", "Buy Film", "Cancel"};
//		return (String) JOptionPane.showInputDialog(null, media.toString(),"Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
//	}
	
	public String purchaseType(MediaItem media)
	{
		Object[] selection;
		String[] mediaItemSelection = {"Ship to Address - Hardcopy", "Store in Online Repository - SoftCopy", "Cancel"};
		String[] gameSelection = {"Ship to Address - Hardcopy", "Cancel"};
		if(media.getMediaType().equalsIgnoreCase("Game"))
			selection = gameSelection;
		else
			selection = mediaItemSelection;
		return (String) JOptionPane.showInputDialog(null, "What would you like to do?", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String StaffMediaItemDetails(MediaItem media)
	{
		Object [] selection = {"Edit", "Remove", "Undo","Redo", "Return to Menu"};
		return (String) JOptionPane.showInputDialog(null, media.toString(),"Staff : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String SupplierItemDetails(MediaItem media)
	{
		Object [] selection = {"Add to Catalogue", "Return to Menu"};
		return (String) JOptionPane.showInputDialog(null, media.toString(),"Staff : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String SearchWhichCatalogue()
	{
		Object [] selection = {"Search Our Catalogue", "Search Supplier Catalogue", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Method of Payment?", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String ViewWhichCatalogue()
	{
		Object [] selection = {"View Our Catalogue", "View Supplier Catalogue", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Method of Payment?", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String paymentMethod()
	{
		Object [] selection = {"Credit Card", "Wallet", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Method of Payment?", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public String confirmPurchase()
	{
		Object [] selection = {"Confirm Purchase", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Please confirm purchase", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
		
	}
	
	public void displayReceipt(MediaItem media,I_Receipt receipt) throws IOException
	{
		JOptionPane.showMessageDialog(null,receipt.PrintReceipt(media.getTitle()));
	}
	
	public void displayReceipt(String username,I_Receipt receipt) throws IOException
	{
		JOptionPane.showMessageDialog(null,receipt.PrintReceipt(username));
	}
	
	public String amountToAddToWallet()
	{
		Object [] selection = {"€100", "€50", "€20", "€10", "Cancel"};
		return (String) JOptionPane.showInputDialog(null, "Choose amount to add to wallet", "Customer : " + currentUser.getName(), 1 , null, selection, selection[0]);
	}
	
	public void displayErrorMessage(String errorMessage)
	{
		JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	

	public void getChoicePlatform(MediaItem media)
	{
		//String gets customers name and title of the game
		String infoString = currentUser.getUsername() + ",";
		infoString += media.getTitle() + ",";
		Object[] formats = media.getFormat().split(":");
		//get all formats for the game (Ps4 xbox etc)
		Object[] formatz= new Object[formats.length+1];
		for(int i=0; i<formats.length; i++)
		{
			formatz[i]=formats[i];
		}
		formatz[formatz.length-1]= "Cancel";
		
		String choice4= (String) JOptionPane.showInputDialog(null, "Choose Platform", "Customer : " + currentUser.getName(), 1 , null, formatz, formatz[0]);
		if(!choice4.equals("Cancel"))
		{
			String returnChoice= infoString + choice4 + ","; 
			concreteCommandName = "CF_ChoosePlatformCommand";
			concreteReceiverName = "PlatformChoice";
			executeInvoker(returnChoice, concreteCommandName, concreteReceiverName);
		}
	}
}