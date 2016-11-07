package database;

import java.io.*;
import java.util.*;

//import org.junit.Test;

import media.*;
import program.*;
import users.*;

public class Database {
UserFactory userFactory = new UserFactory();
	private final String usersDatabase = "users.txt";
	private final String mediaItemsDatabase = "mediaItems.txt";
	private final String customerRepository = "customerRepository.txt";
	private final String shoppingCart = "shoppingCart.txt";
	private final String supplierDatabase = "Supplier.txt";
	
	File usersFile = new File(usersDatabase);
	File mediaItemsFile = new File(mediaItemsDatabase);
	File customerRepositoryFile = new File(customerRepository);
	File shoppingCartFile = new File (shoppingCart);
	File supplierFile = new File (supplierDatabase);
	
	// Reading and writing objects
	FileWriter fw;
	BufferedWriter bw;
	Scanner in;
		
	// A user
	UserClass user;
	
	ArrayList<String> linesFromUsersFile = new ArrayList<String>();
	ArrayList<String> linesFromMediaItemsFile = new ArrayList<String>();
	ArrayList<String> linesFromCustomerRepositoryFile = new ArrayList<String>();
	ArrayList<String> linesFromShoppingCartFile = new ArrayList<String>();
	ArrayList<String> linesFromSupplierFile = new ArrayList<String>();
	
	ArrayList<UserClass> usersList = new ArrayList<UserClass>();
	ArrayList<MediaItem> mediaItemsCatalogueList = new ArrayList<MediaItem>();
	ArrayList<MediaItem> mediaItemsSuppliersList = new ArrayList<MediaItem>();

	
	// This is the one single instance of this class it is populated in getInstance()
	private static Database databaseFirstInstance = null;
	
	//Constructor is private so that only one instance of it will be created : singleton design pattern
		private Database() throws IOException
		{
			// The methods will only be called once!
			populateUsersList();
			populateMediaCatalogue();
			populateCustomerRepository();
			populateShoppingCartRepository();
			populateSupplierCatalogue();
			//Need to add other methods in here so that the films lists will be updated and any other database too
		}
		
		

		

		// this is the singlton pattern, this function is the one that will be called by any class that needs an instance of this class
		//it checks if there is already an instance of it created, if there is it returns it, if not it calls the private constructor to
		//create oneand then returns it.
	    public static Database  getInstance()
	    {
	    	if(databaseFirstInstance == null)
	    	{
	    		try 
	    		{
	    			databaseFirstInstance = new Database();
				} 
	    		catch (IOException e)
	    		{
					e.printStackTrace();
				}
	    	}
	    	return databaseFirstInstance;
	    }
	
	    private void populateUsersList() throws IOException 
	    {
	    	in = new Scanner(usersFile);
			String lineFromFile="";
			while(in.hasNext())
			{
				lineFromFile = in.nextLine();
				linesFromUsersFile.add(lineFromFile);
				String type = lineFromFile.substring(0, lineFromFile.indexOf(","));
				user = TypeOfFactoryGenerator.getFactory("USER").getUser(type);
				user.createUser(lineFromFile);
				usersList.add(user);
			}
		}
	    
	    private void populateMediaCatalogue() throws FileNotFoundException 
	    {
	    	in = new Scanner(mediaItemsFile);
			String aLineFromFile = "";
			MediaItem mediaItem;
			while(in.hasNext())
			{
				aLineFromFile = in.nextLine();
				String type = aLineFromFile.substring(0, aLineFromFile.indexOf(","));
				mediaItem = TypeOfFactoryGenerator.getFactory("MEDIA").getMediaItem(type);
				if(mediaItem!=null)
				{
					linesFromMediaItemsFile.add(aLineFromFile);
					mediaItemsCatalogueList.add(mediaItem.createMediaItem(aLineFromFile));
				}
			}
			
		}
	    
	    private void populateSupplierCatalogue() throws FileNotFoundException 
	    {
	    	in = new Scanner(supplierFile);
			String aLineFromFile = "";
			MediaItem mediaItem;
			while(in.hasNext())
			{
				aLineFromFile = in.nextLine();
				String type = aLineFromFile.substring(0, aLineFromFile.indexOf(","));
				mediaItem = TypeOfFactoryGenerator.getFactory("MEDIA").getMediaItem(type);
				if(mediaItem!=null)
				{
					linesFromSupplierFile.add(aLineFromFile);
					mediaItemsSuppliersList.add(mediaItem.createMediaItem(aLineFromFile));
				}
			}
			
	    } 
	    
	    public void populateShoppingCartRepository() throws FileNotFoundException{
	    	in = new Scanner(shoppingCartFile);
			String aLineFromFile = "";
			while(in.hasNext())
			{
				aLineFromFile = in.nextLine();
				linesFromShoppingCartFile.add(aLineFromFile);
			}
			
	    }
	    
	    
	    public void populateCustomerRepository() throws FileNotFoundException
		{
			in = new Scanner(customerRepositoryFile);
			String lineFromFile="";
			while(in.hasNextLine())
			{
				lineFromFile = in.nextLine();
				linesFromCustomerRepositoryFile.add(lineFromFile);
			}
		}
	    
	    //get shopping cart items
	    public HashMap<MediaItem, String> getShoppingCart(String userID) throws FileNotFoundException 
	    {
	    	HashMap <MediaItem, String> cartList = new HashMap<MediaItem, String>();
	    	MediaItem m;
	    	in = new Scanner(shoppingCartFile);
			String aLineFromFile = "";
			MediaItem mediaItem;
			while(in.hasNext())
			{
				aLineFromFile = in.nextLine();
				linesFromShoppingCartFile.add(aLineFromFile);
				String[] components = aLineFromFile.split(",");
				if(components[0].equals(userID)){
					for(int i=1; i < components.length; i+= 2){
						m = getMediaItemByID(components[i]);
						cartList.put(m, components[i+1]);
					}
				}
			}
			return cartList;
		}
	    
	    
	    
	    
	    //	Users Section
	    
	    public ArrayList<UserClass> getUsers()
		{
			return usersList;
		}
	    
	    //	checks to see if there is a user with the username you passed in and returns that user, 
	    //	if it doesn't match it returns null
		public UserClass getUserByName(String nameOfUser)
		{
			boolean found=false;
			for(int i=0; i<usersList.size() && !found; i++)
			{
				if(nameOfUser.matches((usersList.get(i)).getUsername()))
				{
					user = usersList.get(i);
					found=true;
				}
			}
			if(!found)
			{
				return null;
			}
			return user;
		}
		
		public MediaItem getMediaItemByName(String nameOfItem)
		{	
			MediaItem media=null;
			boolean found=false;
			for(int i=0;i<mediaItemsCatalogueList.size() && !found; i++)
			{
				if(nameOfItem.matches(mediaItemsCatalogueList.get(i).getTitle()))
				{
					media= mediaItemsCatalogueList.get(i);
					found= true;
				}
					
			}
			if(!found)
			{
				return null;
			}
			return media;
		}
		
		public MediaItem getSupplierItemByName(String nameOfItem)
		{	
			MediaItem media=null;
			boolean found=false;
			for(int i=0;i<mediaItemsSuppliersList.size() && !found; i++)
			{
				if(nameOfItem.matches(mediaItemsSuppliersList.get(i).getTitle()))
				{
					media= mediaItemsSuppliersList.get(i);
					found= true;
				}
					
			}
			if(!found)
			{
				return null;
			}
			return media;
		}
		
		public MediaItem getMediaItemByID(String mediaID)
		{	
			MediaItem media=null;
			boolean found=false;
			for(int i=0;i<mediaItemsCatalogueList.size() && !found; i++)
			{
				if(mediaID.matches(mediaItemsCatalogueList.get(i).getMediaID()))
				{
					media= mediaItemsCatalogueList.get(i);
					found= true;
				}
					
			}
			if(!found)
			{
				return null;
			}
			return media;
		}
	    
		//adds a new user, this updates the list of user objects and also writes out to the database
		public void addUser(UserClass userToAdd) throws IOException
		{
			
			usersList.add(userToAdd);
			linesFromUsersFile.add(userToAdd.toString());
			if(userToAdd.getType().equalsIgnoreCase("CUSTOMER"))
			{
				linesFromCustomerRepositoryFile.add(Integer.toString(userToAdd.getUserID()));
				BufferedWriter bwUserAccess = new BufferedWriter(new FileWriter(customerRepositoryFile, false));						
				for(int i=0; i<linesFromCustomerRepositoryFile.size(); i++)				
				{
					bwUserAccess.write(linesFromCustomerRepositoryFile.get(i));
					bwUserAccess.newLine();
				}
				bwUserAccess.close();
			}
			//Write user to users.txt
			
//			fw = new FileWriter(usersFile,true);
//			bw = new BufferedWriter(fw);
//			bw.newLine();
//			bw.write(userToAdd.toString());
//			
//			bw.close();
//			fw.close();
			
			fw = new FileWriter(usersFile, false);						//have to change this so it is just appending 
			bw = new BufferedWriter(fw);								//like above.. problem with above code
			for(int i=0; i<linesFromUsersFile.size(); i++)				//was adding extra blank line
			{
				bw.write(linesFromUsersFile.get(i));
				bw.newLine();
			}
			
			bw.close();
			fw.close();
			
			

		}
		
		//removes a user, this updates the list of user objects and also writes out to the database
		public void removeUser(UserClass userToRemove) throws IOException
		{
			boolean found = false;
			for(int i=0; i<usersList.size() && !found; i++)
			{
				
				if(userToRemove.getUsername().equals(usersList.get(i).getUsername()))
				{
					usersList.remove(i);
					linesFromUsersFile.remove(i);
					found=true;
					
				}
			}
			if(found)
			{
				//this is new code
				if(userToRemove.getType().equalsIgnoreCase("CUSTOMER"))
				{
//					linesFromUserAccessFile.add(userToRemove.getUsername());
					boolean userFound = false;
					for(int i=0; i<linesFromCustomerRepositoryFile.size() && !userFound; i++)
					{ 
						
						if(linesFromCustomerRepositoryFile.get(i).startsWith(userToRemove.getUserID() + ","))
						{
							linesFromCustomerRepositoryFile.remove(i);
							userFound = true;
						}
					}
					BufferedWriter bwUserAccess = new BufferedWriter(new FileWriter(customerRepositoryFile, false));						
					for(int i=0; i<linesFromCustomerRepositoryFile.size(); i++)				
					{
						bwUserAccess.write(linesFromCustomerRepositoryFile.get(i));
						bwUserAccess.newLine();
					}
					bwUserAccess.close();
				}
				//end of new code
				fw = new FileWriter(usersFile, false);
				bw = new BufferedWriter(fw);
				for(int i=0; i<linesFromUsersFile.size(); i++)
				{
					bw.write(linesFromUsersFile.get(i));
					bw.newLine();
				}
				
				
				bw.close();
				fw.close();
			}
		}
		

		
		//updates a user, this writes out to the database
		public void updateUsers() throws IOException
		{
			linesFromUsersFile.clear();
			for(int i=0;i<usersList.size();i++)
			{
				linesFromUsersFile.add(usersList.get(i).toString());
			}
			
			fw = new FileWriter(usersFile, false);
			bw = new BufferedWriter(fw);
			for(int i=0; i<linesFromUsersFile.size(); i++)
			{
				bw.write(linesFromUsersFile.get(i));
				bw.newLine();
			}
			bw.close();
			fw.close();
		}
		
		// Updates item and writes to DB
		public void updateItem() throws IOException
		{
			linesFromMediaItemsFile.clear();
			for(int i=0;i<mediaItemsCatalogueList.size();i++)
			{
				linesFromMediaItemsFile.add(mediaItemsCatalogueList.get(i).toFileString());
			}
			
			fw = new FileWriter(mediaItemsFile, false);
			bw = new BufferedWriter(fw);
			for(int i=0; i<linesFromMediaItemsFile.size(); i++)
			{
				bw.write(linesFromMediaItemsFile.get(i));
				bw.newLine();
			}
			bw.close();
			fw.close();
		}
	    
		public void removeItem(MediaItem itemToRemove) throws IOException
		{
			boolean found = false;
			for(int i=0; i<mediaItemsCatalogueList.size() && !found; i++)
			{
				
				if(itemToRemove.getTitle().equals(mediaItemsCatalogueList.get(i).getTitle()))
				{
					mediaItemsCatalogueList.remove(i);
					linesFromMediaItemsFile.remove(i);
					found=true;
					
				}
			}

				fw = new FileWriter(mediaItemsFile, false);
				bw = new BufferedWriter(fw);
				for(int i=0; i<linesFromMediaItemsFile.size(); i++)
				{
					bw.write(linesFromMediaItemsFile.get(i));
					bw.newLine();
				}
				
				bw.close();
				fw.close();
		}
		
		public void addItemFromSuppier(MediaItem media) throws IOException
		{
			boolean itemDuplicate = false;
			in = new Scanner(mediaItemsFile);
			String lineFromFile;
			while(in.hasNext() && !itemDuplicate)
			{
				lineFromFile = in.nextLine();
				String[] arr = lineFromFile.split(",");
				if(media.getTitle().equals(arr[1]))
				{
					itemDuplicate = true;
				}
			}
			if(!itemDuplicate)
			{
				linesFromMediaItemsFile.add(media.toFileString());
				//I_MediaItem filmInstance = TypeOfFactoryGenerator.getFactory("MEDIA").getMediaItem("FILM");
				//mediaItemsCatalogueList.add(filmInstance.createMediaItem(film.toString()));
				fw = new FileWriter(mediaItemsFile, false);
				bw = new BufferedWriter(fw);
				for(int i=0; i<linesFromMediaItemsFile.size(); i++)
				{
					bw.write(linesFromMediaItemsFile.get(i));
					bw.newLine();
				}
				bw.close();
				fw.close();
			}
			
		}
		
		
		//	Media Section
		
	    public ArrayList<MediaItem> getMediaItems()
		{
			return mediaItemsCatalogueList;
		}
	    
	    public ArrayList<MediaItem> getSupplierItems()
	    {
	    	return mediaItemsSuppliersList;
	    }
	    
	   public MediaItem searchSuppliersDatabase(String nameOfItem)
	   {
			for(int i=0; i < mediaItemsSuppliersList.size(); i++)
			{
				if(nameOfItem.equals(mediaItemsSuppliersList.get(i).getTitle()))
				{
					return mediaItemsSuppliersList.get(i);
				}
			}
			return null;
	   }
	   
	   public ArrayList<MediaItem> getCustomersMediaRepository(int userID)
	   {
		   ArrayList<MediaItem> customersMediaItems = new ArrayList<MediaItem>();
		   boolean foundUser=false;
		   for(int i=0; i<linesFromCustomerRepositoryFile.size() && !foundUser; i++)
		   {
			   String line[] = linesFromCustomerRepositoryFile.get(i).split(",");
			   int userIdNumber = Integer.parseInt(line[0]);
			   if(userID == userIdNumber)
			   {
				   for(int x=1; x<line.length; x++)
				   {
					   customersMediaItems.add(getMediaItemByID(line[x]));
				   }
				   foundUser = true;
				   return customersMediaItems;
			   }
		   }
		   return null;
	   }
	   
	   public boolean updateOnlineMediaRepository(String userID, String mediaID) throws IOException
	   {
		   boolean found = false, duplicate = false;
		   for(int i=0; i<linesFromCustomerRepositoryFile.size() && !found; i++)
		   {
			   String currentLine = linesFromCustomerRepositoryFile.get(i);
			   if(currentLine.startsWith(userID))
			   {
				   found = true;
				   if(currentLine.contains(mediaID))
				   {
					   duplicate = true;
				   }
				   else
				   {
					   currentLine += "," + mediaID;
					   linesFromCustomerRepositoryFile.set(i, currentLine);
					   duplicate=false;
					   
					   // update text file after we add mediaID to the end of the line
					   fw = new FileWriter(customerRepositoryFile, false);
					   bw = new BufferedWriter(fw);
					   for(int x=0; x<linesFromCustomerRepositoryFile.size(); x++)
					   {
						   bw.write(linesFromCustomerRepositoryFile.get(x));
						   bw.newLine();
					   }
					   bw.close();
					   fw.close();
				   }
			   }
		   }
		   return duplicate;
	   }


	   public void updateShoppingCart(String id, String qty, int UserID) throws Exception 
	   {
		   String aLineFromFile;
		   String newLine;

		   if(!linesFromShoppingCartFile.isEmpty()){
			   linesFromShoppingCartFile.clear();
		   }
		   in = new Scanner(shoppingCartFile);
		   while (in.hasNext()) {
			   aLineFromFile = in.nextLine();
			   String[]components = aLineFromFile.split(",");
			   if (components[0].equals(Integer.toString(UserID))) {
				   aLineFromFile = components[0];
				   for(int j=1; j < components.length; j+=2){
					   if(components[j].matches(id)){
						   components[j+1] = qty;
					   }
					   aLineFromFile += "," + components[j] + "," + components[j+1];
				   }
			   }
			   linesFromShoppingCartFile.add(aLineFromFile);
		   }

		   System.out.println(linesFromShoppingCartFile);
		   BufferedWriter bwCart = new BufferedWriter(new FileWriter(shoppingCartFile, false));						
		   for(int i=0; i<linesFromShoppingCartFile.size(); i++)				
		   {
			   bwCart.write(linesFromShoppingCartFile.get(i));
			   bwCart.newLine();
		   }
		   bwCart.close();
	   }


}
