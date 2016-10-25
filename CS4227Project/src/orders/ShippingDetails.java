package orders;
import java.util.Date;

public class ShippingDetails {
	private String shippingID;
	private String orderID;
	private String shippingType;
	private float shippingCost;
	private Date shippingDate;
	private boolean sigReq;
	public String getShippingID() {
		return shippingID;
	}
	public void setShippingID(String shippingID) {
		this.shippingID = shippingID;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	public float getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public boolean isSigReq() {
		return sigReq;
	}
	public void setSigReq(boolean sigReq) {
		this.sigReq = sigReq;
	}
	
	
}
