package media;

import java.io.IOException;

public class FilmClass extends MediaItem {

//	private String mediaType="", filmID="", filmName="", genre="",type="", description="", format="";
//	private double price = 0.0, rating = 0.0;
	
	public FilmClass()
	{
		
	}
	
//	public FilmClass(String filmString)
//	{
//		String[] filmArr = filmString.split(",");
//		this.mediaType = filmArr[0];
//		this.filmID = filmArr[1];
//		this.filmName = filmArr[2];
//		this.genre = filmArr[3];
//		this.type = filmArr[4];
//		this.price = Double.parseDouble(filmArr[5]);
//		this.description = filmArr[6];
//		this.rating = Double.parseDouble(filmArr[7]);
//		this.format = filmArr[8];
//	}
	
	
	public FilmClass createMediaItem(String aLineFromFile)
	{
		return (FilmClass) super.createMediaItem(aLineFromFile);
	}
	
	@Override
	public void paymentMethod(String userID, String fileName, String filePrice, String paymentMethod,
			String confirmation) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void payWithWallet(String userID, String fileName, String filePrice) throws IOException {
		// TODO Auto-generated method stub

	}



	@Override
	public String toString() {
		
		return "MediaType : " + getMediaType() + "\nFilm ID : " + getMediaID() + "\nFilm Name : " + getTitle() + "\nDirector : " + getCreator() + "\nFilm Genre : " + getGenre()
		+ "\nFilm Type : " + getType() + "\nFilm Price : " + getPrice() + "\nDescription : " + getDescription() + "\nFormat : " + getFormat()
		 + "\nRating : " + getRating() + "/5.0";
		
	}
	
	
	
//	public String getFilmID() {
//	return filmID;
//}
//
//public void setFilmID(String filmID) {
//	this.filmID = filmID;
//}
//
//public String getFilmName() {
//	return filmName;
//}
//
//public void setFilmName(String filmName) {
//	this.filmName = filmName;
//}
//
//public String getGenre() {
//	return genre;
//}
//
//public void setGenre(String genre) {
//	this.genre = genre;
//}
//
//public String getType() {
//	return type;
//}
//
//public void setType(String type) {
//	this.type = type;
//}
//
//public double getPrice() {
//	return price;
//}
//
//public void setPrice(double price) {
//	this.price = price;
//}
//
//public String getMediaType() {
//	return mediaType;
//}
//
//public void setMediaType(String mediaType) {
//	this.mediaType = mediaType;
//}
//
//public String getDescription() {
//	return description;
//}
//
//public void setDescription(String description) {
//	this.description = description;
//}
//
//public String getFormat() {
//	return format;
//}
//
//public void setFormat(String format) {
//	this.format = format;
//}
//
//public double getRating() {
//	return rating;
//}
//
//public void setRating(double rating) {
//	this.rating = rating;
//}
	
	
	
	
	
	
	
}
