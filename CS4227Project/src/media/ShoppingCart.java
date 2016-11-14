package media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import media.MediaItem;

public class ShoppingCart implements I_Observer{

	private String cartID;
	HashMap <MediaItem, String> cartList;
	private double totalCost, discountTotal;
	private static boolean priceChangeInCart = false;

	public ShoppingCart(){
		setCartID("");
		cartList = new HashMap<MediaItem, String>();
		totalCost = 0;
	}
	
	public ShoppingCart(String userID, HashMap <MediaItem, String> cartList)
	{
		this.setCartID(userID);
		this.cartList = cartList;
		calculateTotalCost();
		for (Map.Entry<MediaItem, String> entry : cartList.entrySet()) {
			entry.getKey().registerObserver(this);
		}
	}
	
	public void addItem(MediaItem m, String quantity) {
		System.out.println(m);
		cartList.put(m, quantity);
		m.registerObserver(this);
		calculateTotalCost();
	}
	
	public MediaItem getMediaItemByName(String name){
		for (Map.Entry<MediaItem, String> entry : cartList.entrySet()) {
			if(entry.getKey().getTitle().equals(name)){
				return entry.getKey();
			}
		}
		return null;
	}
	
	public void clearCart() {
		cartList.clear();
		
	}

	public void removeItem(MediaItem m) {
		cartList.remove(m);
		m.removeObserver(this);
		calculateTotalCost();
	}

	public void updateQuantity(MediaItem m, String quantity) {
		for (Map.Entry<MediaItem, String> entry : cartList.entrySet()) {
			if(entry.getKey().equals(m)){
				if(Integer.parseInt(quantity) == 0)
					cartList.remove(m);
				else
					cartList.put(m, quantity);
			}
		}
		calculateTotalCost();
	}
	
	public void updatePrice(MediaItem m)
	{
		String qty = cartList.get(m);
		double discount = (m.getPrice() / 100) * 10;
		m.setPrice(m.getPrice() - discount);
		m.notifyObservers();
		setDiscountTotal(discount * Integer.parseInt(qty));
		calculateTotalCostWithDiscount();
	}

	public void viewCartDetails() {

	}

	public void checkout() {

	}

	public String getCartID() {
		return cartID;
	}

	public void setCartID(String cartID) {
		this.cartID = cartID;
	}
	
	public HashMap <MediaItem, String> getCartList() {
		return cartList;
	}

	public void update(MediaItem mediaItem) {
		 for(Map.Entry<MediaItem, String> entry : cartList.entrySet())
			{
			 
				if(entry.getKey().getMediaID().equals(mediaItem.getMediaID()))
				{
					calculateTotalCost();
					//Bellow will have to be sent to UI to display
				JOptionPane.showMessageDialog(null, "The price of " + mediaItem.getTitle() + " has changed to " + mediaItem.getPrice()
														+ "\nTotal Cost : " + getTotalCost());
				}
					
			}
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public double getDiscountTotal(){
		return discountTotal;
	}
	public void setDiscountTotal(double discountTotal){
		this.discountTotal = discountTotal;
	}
	
	public boolean checkIfItemExists(MediaItem m) {
		for(Map.Entry<MediaItem, String> entry : cartList.entrySet()){
			if(entry.getKey().getMediaID().equals(m.getMediaID()))
				return true;
		}
		return false;
	}
	
	public void calculateTotalCost()
	{
		//loop through cart and add cost of each (item*quantity)
		totalCost = 0.0;
		for(Map.Entry<MediaItem, String> entry : cartList.entrySet())
		{
		 
			totalCost += entry.getKey().getPrice() * Integer.parseInt(entry.getValue());
		}
		setTotalCost(totalCost);
	}
	
	public void calculateTotalCostWithDiscount()
	{
		//loop through cart and add cost of each (item*quantity)
		totalCost = 0.0;
		for(Map.Entry<MediaItem, String> entry : cartList.entrySet())
		{ 
			totalCost += entry.getKey().getPrice() * Integer.parseInt(entry.getValue());
		}
		setTotalCost(totalCost);
	}


	public String getQuantity(MediaItem shoppingItem) {
		for(Map.Entry<MediaItem, String> entry : cartList.entrySet())
		{
			if(entry.getKey().getMediaID().equals(shoppingItem.getMediaID()))
					return entry.getValue();
		}
		JOptionPane.showMessageDialog(null, "Cannot find quantity!", "Quantity Error",JOptionPane.ERROR_MESSAGE);
		return null;
	}

	public String getSingleQuantityPrice() {
		double newPrice = 0.0;
		
		for(Map.Entry<MediaItem, String> entry : cartList.entrySet())
		{
		 if(!entry.getKey().getMediaType().equalsIgnoreCase("Game"))
			newPrice += entry.getKey().getPrice();
		 else
			 newPrice += entry.getKey().getPrice() * Integer.parseInt(entry.getValue());
			 
		}
		return String.valueOf(newPrice);
	}
}