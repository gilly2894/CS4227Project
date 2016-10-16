package program;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import database.Database;
import media.MediaItem;
import userInterface.UserInterfaceMenu;
import users.*;

public class ProgramDriver 
{
	static Database database;
	public static void main(String[] args) throws IOException
	{
		database = Database.getInstance();
		UserInterfaceMenu uiObj = new UserInterfaceMenu();
		uiObj.showMainMenu();
		
		
//		//Testing
//		ArrayList<UserClass> users = database.getUsers();
//		ArrayList<MediaItem> mediaItems = database.getMediaItems();
//		
//		System.out.println("Users:\n");
//		for(int i=0; i<users.size(); i++)
//		{
//			System.out.println(users.get(i).toString());
//		}
//		
//		System.out.println("\n\nMedia Database:\n");
//		
//		for(int i=0; i<mediaItems.size(); i++)
//		{
//			System.out.println(mediaItems.get(i).toString());
//			System.out.println("\n");
//		}
//		ProgramDriver pd = new ProgramDriver();
//		String a="Add User", d="Delete User", u="Update User";
//		I_UserActions userAction = new C_AdminActions();
//		
//		
////		String addUserInput = pd.addNewUser();
////		userAction.userActions(a, addUserInput);
//		
//		String removeUserInput = pd.UserToRemove();
//		userAction.userActions(d, removeUserInput);
//		
////		String updateUserInput = pd.UserToUpdate();
////		userAction.userActions(u, updateUserInput);
//		
//		
//		System.out.println("Users after action:\n");
//		for(int i=0; i<users.size(); i++)
//		{
//			System.out.println(users.get(i).toString());
//		}
		
	}
}
