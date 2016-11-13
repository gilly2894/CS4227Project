package media;

import java.io.IOException;
import java.util.ArrayList;

public class GameClass extends MediaItem {
	protected PriceBundler bundler;
	
	public GameClass()
	{
		super();
	}
	public GameClass(PriceBundler bundler)
	{
		super();
		this.bundler=bundler;
	}
	
	
	
	
	
	public PriceBundler getBundler() {
		return bundler;
	}
	public void setBundler(PriceBundler bundler) {
		this.bundler = bundler;
	}
	public void bundle() {  
		 {
				// TODO Auto-generated method stub
			 	this.setPrice(bundler.bundlePrice(this.getPrice()));
		}
	}
	
	public double reset()
	{
		return bundler.reset();
	}
	
	@Override
	public String toString() {
		
		return "MediaType : " + getMediaType() + "\nGame ID : " + getMediaID() + "\nTitle : " + getTitle() + "\nDeveloper : " + getCreator() + "\nGenre : " + getGenre()
		+ "\nRelease Type : " + getType() + "\nPrice : " + getPrice() + "\nDescription : " + getDescription() + "\nPlatform('s) : " + getFormat()
		 + "\nRating : " + getRating() + "/5.0";
		
	}

}



