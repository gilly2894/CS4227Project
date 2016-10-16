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
	}
}
