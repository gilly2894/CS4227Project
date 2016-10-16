package media;

import java.io.IOException;

public class GameClass extends MediaItem {

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
		
		return "MediaType : " + getMediaType() + "\nGame ID : " + getMediaID() + "\nTitle : " + getTitle() + "\nDeveloper : " + getCreator() + "\nGenre : " + getGenre()
		+ "\nRelease Type : " + getType() + "\nPrice : " + getPrice() + "\nDescription : " + getDescription() + "\nPlatform('s) : " + getFormat()
		 + "\nRating : " + getRating() + "/5.0";
		
	}

}
