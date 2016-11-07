package media;

import java.io.IOException;

public abstract class MediaItem
{
	private String mediaType="", mediaID="", title="", creator="", genre="", releaseType="", description="", format="";
	private double price = 0.0, rating = 0.0;
	private PriceBundler bundler;
	
	public abstract void paymentMethod(String userID, String fileName, String filePrice, String paymentMethod, String confirmation) throws IOException;
	public abstract void payWithWallet(String userID, String fileName, String filePrice) throws IOException;
	
	public MediaItem createMediaItem(String aLineFromFile)
	{
		String[] filmArr = aLineFromFile.split(",");
		this.mediaType = filmArr[0];
		this.mediaID = filmArr[1];
		this.title = filmArr[2];
		this.creator = filmArr[3];
		this.genre = filmArr[4];
		this.releaseType = filmArr[5];
		this.price = Double.parseDouble(filmArr[6]);
		this.description = filmArr[7];
		this.rating = Double.parseDouble(filmArr[8]);
		this.format = filmArr[9];
		return this;
		
	}
	
	
	//    getters and setters
	
	
	
	public PriceBundler getBundler() {
		return bundler;
	}
	public void setBundler(PriceBundler bundler) {
		this.bundler = bundler;
	}
	
	public void bundle()
	{
		
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaID() {
		return mediaID;
	}
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getType() {
		return releaseType;
	}
	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public String toString() 
	{
		String returnString="";
		returnString += getMediaType() + "," + getMediaID() + "," + getTitle() + "," + getCreator() + "," + getGenre()
		+ "," + getType() + "," + getPrice() + "," + getDescription() + "," + getRating() + "," + getFormat();
		return returnString;
	}
	
	public String toFileString() {
		
		return getMediaType() + "," + getMediaID() + "," + getTitle() + "," + getCreator() + "," + getGenre()
		+ "," + getType() + "," + getPrice() + "," + getDescription() + "," + getRating()
		 + ","  + getFormat() ;
		
	}
	

	
	
	
}
