package Orders;
import java.util.Date;

public class Order {
	private String orderID;
	private String cartID;
	private Date orderCreated;
	private String userID;
	private String customerName;
	private String status;
	private String shoppingID;
	private boolean priorityMail;
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getCartID() {
		return cartID;
	}
	public void setCartID(String cartID) {
		this.cartID = cartID;
	}
	public Date getOrderCreated() {
		return orderCreated;
	}
	public void setOrderCreated(Date orderCreated) {
		this.orderCreated = orderCreated;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShoppingID() {
		return shoppingID;
	}
	public void setShoppingID(String shoppingID) {
		this.shoppingID = shoppingID;
	}
	public boolean isPriorityMail() {
		return priorityMail;
	}
	public void setPriorityMail(boolean priorityMail) {
		this.priorityMail = priorityMail;
	}
	
	public void placeOrder(){
		
	}
}
