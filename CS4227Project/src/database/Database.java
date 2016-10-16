package database;

import java.io.*;
import java.util.*;
import media.*;
import program.*;
import users.*;

public class Database {

	UserFactory userFactory = new UserFactory();
	private final String usersDatabase = "users.txt";
	private final String mediaItemsDatabase = "mediaItems.txt";
	private final String customerRepository = "customerRepository.txt";
	
	File usersFile = new File(usersDatabase);
	File mediaItemsFile = new File(mediaItemsDatabase);
	File customerRepositoryFile = new File(customerRepository);
	
	
	// Reading and writing objects
	FileWriter fw;
	BufferedWriter bw;
	Scanner in;
		
	// A user
	UserClass user;
	
	ArrayList<String> linesFromUsersFile = new ArrayList<String>();
	ArrayList<UserClass> usersList = new ArrayList<UserClass>();
	
	ArrayList<String> linesFromMediaItemsFile = new ArrayList<String>();
	ArrayList<MediaItem> mediaItemsCatalogueList = new ArrayList<MediaItem>();
	
	ArrayList<String> linesFromCustomerRepositoryFile = new ArrayList<String>();
	
	// This is the one single instance of this class it is populated in getInstance()
	private static Database databaseFirstInstance = null;
	
	//Constructor is private so that only one instance of it will be created : singleton design pattern
		private Database() throws IOException
		{
			// The methods will only be called once!
			populateUsersList();
			populateMediaCatalogue();
			populateCustomerRepository();
			
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
	    
		//adds a new user, this updates the list of user objects and also writes out to the database
		public void addUser(UserClass userToAdd) throws IOException
		{
			
			usersList.add(userToAdd);
			linesFromUsersFile.add(userToAdd.toString());
			if(userToAdd.getType().equalsIgnoreCase("CUSTOMER"))
			{
				linesFromCustomerRepositoryFile.add(userToAdd.getUserID() + ",");
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
		
	    
		
		//	Media Section
		
	    public ArrayList<MediaItem> getMediaItems()
		{
			return mediaItemsCatalogueList;
		}
}
