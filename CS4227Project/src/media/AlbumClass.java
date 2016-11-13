package media;

import java.io.IOException;

public class AlbumClass extends MediaItem {


	
	@Override
	public String toString() {
		
		return "MediaType : " + getMediaType() + "\nAlbum ID : " + getMediaID() + "\nTitle : " + getTitle() + "\nArtist : " + getCreator() + "\nGenre : " + getGenre()
		+ "\nRelease Type : " + getType() + "\nPrice : " + getPrice() + "\nDescription : " + getDescription() + "\nFormat : " + getFormat()
		 + "\nRating : " + getRating() + "/5.0";
		
	}


}
