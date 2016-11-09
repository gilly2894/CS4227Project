package interceptor.contextObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import media.MediaItem;

public class PaymentInfoContext implements I_ContextObj {
	
	private String userID, description, descriptionForLog;
	private boolean isSuccessful;

	public PaymentInfoContext(String userID, String paymentDescription, boolean isSuccessful) {
		this.userID = userID;
		this.description = paymentDescription;
		this.isSuccessful = isSuccessful;
		makeDescriptionForLog();
	}
	

	@Override
	public String getDescription() {
		
		String logString = getTimeStamp();
		
		logString += descriptionForLog;
		
		return logString;
	}
	
	private String getTimeStamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private void makeDescriptionForLog()
	{
		if(isSuccessful)
			descriptionForLog = " - SUCCESS - User: " + userID + " - " + description;
		else
			descriptionForLog = " - FAILURE - User: " + userID + " - " + description;
	}
	
	
	
	
	
//	@Override
//	public String getContextObjType() {
//		
//		return "PaymentInfoContext";
//	}

}
