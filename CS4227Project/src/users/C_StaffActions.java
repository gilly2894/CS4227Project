package users;

import java.io.IOException;
import java.util.ArrayList;
import media.FilmClass;
import media.MediaItem;

public class C_StaffActions implements I_UserActions {

	UserFactory userFactory = new UserFactory();
	public void userActions(String dropdownSelection, String returnedString) throws IOException {
		

	}
	
	public void removeItem(String itemToRemove) throws IOException
	{
		String[] arr = itemToRemove.split(",");
		MediaItem itemToRemove1 = database.getMediaItemByName(arr[1]);
		database.removeItem(itemToRemove1);
	}
	
	public void updateItem(String itemToUpdate_attribute_newValue) throws IOException
	{
		String[] arr = itemToUpdate_attribute_newValue.split(",");
		if(arr!=null)
		{
			MediaItem itemToUpdate = database.getMediaItemByName(arr[1]);
			String partBeingUpdated = arr[2];
			if(partBeingUpdated.equals("Price"))
			{
				itemToUpdate.setPrice(Double.parseDouble(arr[3]));
			}
			else if(partBeingUpdated.equals("Rating"))
			{
				itemToUpdate.setRating(Double.parseDouble(arr[3]));
			}
			else if(partBeingUpdated.equals("Release"))
			{
				itemToUpdate.setReleaseType(arr[3]);
			}
			
			database.updateItem();
		}
	}
	
	public void addItem(String itemToAdd) throws IOException
	{
		String[] arr = itemToAdd.split(",");
		MediaItem itemAdding = database.getSupplierItemByName(arr[1]);
		database.addItemFromSuppier(itemAdding);
	}
}
