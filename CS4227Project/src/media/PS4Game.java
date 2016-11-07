package media;

public class PS4Game implements PriceBundler {



	@Override
	public double bundlePrice(double price) {
		// TODO Auto-generated method stub
		System.out.println("This is an ps4 game");
		//this.setPrice(this.getPrice()+(5*this.dlc.bundle()));
		double bundledPrice= price*1.10;
		System.out.print("Bundled PS4 Price: " + bundledPrice);	
		return bundledPrice;
	}
	
	public double reset()
	{
		return 1.10;
	}
	
	
	
}
