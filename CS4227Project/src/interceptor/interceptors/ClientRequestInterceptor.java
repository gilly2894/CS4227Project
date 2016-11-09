package interceptor.interceptors;

import java.io.IOException;

import interceptor.contextObjects.PaymentInfoContext;
import logging.Logger;

public class ClientRequestInterceptor implements I_Interceptor {

	Logger logger = new Logger();
	
	public void onPayment(PaymentInfoContext context) throws IOException
	{
		logger.logPayment(context);
	}
	
}
