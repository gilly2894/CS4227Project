package media;

import java.util.ArrayList;

public class ShoppingCart {
	private String cartID;
	private ArrayList<MediaItem> cartMap;
	private int totalCost;

	public ShoppingCart(String cartID){
		this.cartID = cartID;
		cartMap = new ArrayList<MediaItem>();
	}
	
	public void addItem(MediaItem m) {
		cartMap.add(m);
	}

	public void removeItem(MediaItem m) {
		int i = cartMap.indexOf(m);
		cartMap.remove(i);
	}

	public void updateQuantity() {

	}

	public void viewCartDetails() {

	}

	public void checkout() {

	}

	public void getTotalCost() {

	}

}