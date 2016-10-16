package media;

import java.io.IOException;

public class AlbumClass extends MediaItem {

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
		
		return "MediaType : " + getMediaType() + "\nAlbum ID : " + getMediaID() + "\nTitle : " + getTitle() + "\nArtist : " + getCreator() + "\nGenre : " + getGenre()
		+ "\nRelease Type : " + getType() + "\nPrice : " + getPrice() + "\nDescription : " + getDescription() + "\nFormat : " + getFormat()
		 + "\nRating : " + getRating() + "/5.0";
		
	}

}
