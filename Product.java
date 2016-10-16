public class Product {
	private String productID;
	private String productName;
	private String productDescription;
	private float productPrice;
	
	public String getProductID(){ return productID;}
	public String getProductName(){ return productName;} 
	public String getProductDescription() { return productDescription;}
	public float getProductPrice() { return productPrice;}
	
	public void setProductID(String productID){ this.productID = productID;}
	public void setProductName(String productName){ this.productName = productName;} 
	public void setProductDescription(String productDescription) { this.productDescription = productDescription;}
	public void setProductPrice(float productPrice) { this.productPrice = productPrice;}
}
