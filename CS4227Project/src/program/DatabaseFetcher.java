package program;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import database.Database;
import media.FilmClass;
import media.MediaItem;
import media.ShoppingCart;
import users.UserClass;

public class DatabaseFetcher {
	
	Database database = Database.getInstance();

	public UserClass getUserByName(String userName) 
	{
		return database.getUserByName(userName);
	}
	
	public MediaItem getMediaItemByName(String mediaName)
	{
		return database.getMediaItemByName(mediaName);
	}
	
	public MediaItem getSupplierItemByName(String mediaName)
	{
		return database.getSupplierItemByName(mediaName);
	}
	
	public void addUser(UserClass currentUser) throws IOException
	{
		database.addUser(currentUser);
	}
	
	
	 public void removeMediaFromCartFile(MediaItem m, String userID) throws IOException {
		 database.removeMediaFromCartFile(m, userID);
	 }
	public int getHighestUserID()
	{
		return database.getUsers().get(database.getUsers().size()-1).getUserID();
	}
	
	public ArrayList<MediaItem> getMediaItems()
	{
		return database.getMediaItems();
	}
	
	public ArrayList<MediaItem> getSupplierItems()
	{
		return database.getSupplierItems();
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
	public MediaItem searchSuppliersDatabase(String nameOfFilm) throws FileNotFoundException 
	{
		return database.searchSuppliersDatabase(nameOfFilm);
	}
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

	public ArrayList<MediaItem> getCustomersMediaRepository(int userID)
	{
		return database.getCustomersMediaRepository(userID);
	}
	
	public void updateShoppingCart(String id, String qty, int userID){
		try {
			database.updateShoppingCart(id,qty, userID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateShoppingCartFile(String cartID, String itemID, String qty){
		try {
			database.updateShoppingCartFile(cartID ,itemID, qty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void clearUsersCart(String userID) throws IOException {
		database.clearUsersCart(userID);
		
	}

	public void updateOnlineRepository(String userID, HashMap<MediaItem, String> cartList) throws IOException {
		database.addToRepository(userID,cartList);
	}

	public ShoppingCart initializeUsersShoppingCart(String userID) throws FileNotFoundException {
		return database.initializeUsersShoppingCart(userID);
	}

	public ShoppingCart getShoppingCart(String string) throws FileNotFoundException {
		return database.getShoppingCart(string);
	}

}
