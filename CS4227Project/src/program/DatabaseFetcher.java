package program;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import database.Database;
import media.FilmClass;
import users.UserClass;

public class DatabaseFetcher {
	
	Database database = Database.getInstance();

	public UserClass getUserByName(String userName) 
	{
		return database.getUserByName(userName);
	}
	
	public void addUser(UserClass currentUser) throws IOException
	{
		database.addUser(currentUser);
	}
	
	public int getHighestUserID()
	{
		return database.getUsers().get(database.getUsers().size()-1).getUserID();
	}
//	public ArrayList<FilmClass> getListOfFilms(String filmListType)
//	{
//		ArrayList<FilmClass> filmList=null;
//		if(filmListType.equalsIgnoreCase("MEGASTREAM"))
//			filmList = megaStreamDatabase.getFilmsList();
//		else if(filmListType.equalsIgnoreCase("SUPPLIER"))
//			filmList = megaStreamDatabase.getSuppliersFilmList();
//		return filmList;
//	}
//	
//	public FilmClass getFilmByName(String nameOfFilm)
//	{		
//		return megaStreamDatabase.getFilmByName(nameOfFilm);
//	}
//
//	public FilmClass searchSuppliersDatabase(String nameOfFilm) throws FileNotFoundException 
//	{
//		return megaStreamDatabase.searchSuppliersDatabase(nameOfFilm);
//	}
//
//	public String[] getUserFilmSelection(String userName) throws IOException 
//	{
//		return megaStreamDatabase.getUserFilmSelection(userName);
//	}
//
//	public String[][] showListOfFilms() throws IOException 
//	{
//		return megaStreamDatabase.showListOfFilms();
//	}
//

}
