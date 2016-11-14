package payment;

import java.io.FileNotFoundException;

public interface I_Receipt {
	
	public String PrintReceipt(String media) throws FileNotFoundException;
}
