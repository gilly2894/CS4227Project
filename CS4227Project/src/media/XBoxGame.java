package media;

public class XBoxGame implements PriceBundler {

	

	@Override
	public double bundlePrice(double price) {
		// TODO Auto-generated method stub
		System.out.println("This is an XBox game");
		//this.setPrice(this.getPrice()+(5*this.dlc.bundle()));
		double bundledPrice= price*1.05;
		System.out.println("Bundled Xbox Price: " + bundledPrice);	
		return bundledPrice;
		
	}
	
	
	public double reset()
	{
		return 1.05;
	}

}
