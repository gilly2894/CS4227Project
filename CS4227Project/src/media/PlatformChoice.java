package media;

import java.io.IOException;

import database.Database;

public class PlatformChoice {
	
	Database database = Database.getInstance();
	
	public void decidePlatform(String username_MediaName_Platform) throws IOException
	{
		String[] returnedStrArr = username_MediaName_Platform.split(",");
		String username = returnedStrArr[0];
		String MediaName = returnedStrArr[1];
		String Platform = returnedStrArr[2];
		//pass info string and get USERNAME ---MEDIANAME ---PLATFORM type
		media.GameClass item= (media.GameClass)database.getMediaItemByName(MediaName);
		if(Platform.equalsIgnoreCase("XBOX"))
		{
			item.setBundler(new XBoxGame());
			item.bundle();
		}
		
		if(Platform.equalsIgnoreCase("PS4"))
		{
			item.setBundler(new PS4Game());
			item.bundle();
		}
		
		if(Platform.equalsIgnoreCase("NULLPLATFORM"))
		{
			double bPrice= item.getPrice();
			double bRate= item.reset();
			double originalPrice= bPrice/bRate;
			item.setPrice(originalPrice);
			item.setBundler(null);
			
		}
	}
	
	public void nullPLatform(GameClass item)
	{
		double bPrice= item.getPrice();
		double bRate= item.reset();
		double originalPrice= bPrice/bRate;
		item.setPrice(originalPrice);
		item.setBundler(null);
	}

}
