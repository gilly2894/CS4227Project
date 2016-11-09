package program;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import database.Database;
import interceptor.dispatchers.ClientRequestDispatcher;
import interceptor.interceptors.ClientRequestInterceptor;
import media.MediaItem;
import userInterface.UserInterfaceMenu;
import users.*;

public class ProgramDriver 
{
	static Database database;
	public static void main(String[] args) throws IOException
	{
		ClientRequestDispatcher dispatcher = ClientRequestDispatcher.getInstance();
		database = Database.getInstance();
		ClientRequestInterceptor interceptor = new ClientRequestInterceptor();
		dispatcher.registerClientInterceptor(interceptor);
		
		UserInterfaceMenu uiObj = new UserInterfaceMenu();
		uiObj.showMainMenu();		
	}
}
