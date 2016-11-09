package interceptor.dispatchers;

import java.io.IOException;
import java.util.ArrayList;

import org.omg.PortableInterceptor.ClientRequestInfo;

import database.Database;
import interceptor.contextObjects.PaymentInfoContext;
import interceptor.interceptors.ClientRequestInterceptor;
import interceptor.interceptors.I_Interceptor;

public class ClientRequestDispatcher {

	private static ClientRequestDispatcher clientRequstDispatcherFirstInstance = null;
	
	private ArrayList<I_Interceptor> interceptorList;



     private ClientRequestDispatcher() 
     {
    	 interceptorList = new ArrayList<I_Interceptor>();
     }
     
     public static ClientRequestDispatcher getInstance() throws IOException
     {
    	 if(clientRequstDispatcherFirstInstance == null)
    	 {
    		 clientRequstDispatcherFirstInstance = new ClientRequestDispatcher();
    	 }

    	 return clientRequstDispatcherFirstInstance;

     }

     
     public void registerClientInterceptor(I_Interceptor i)
     {   
         this.interceptorList.add(i);
     }
     public void removeClientInterceptor(I_Interceptor i)
     {   
         this.interceptorList.remove(interceptorList.indexOf(i));
     }
     
     public void dispatchClientRequestInterceptorPaymentLogging(PaymentInfoContext context) throws IOException
     {
    	 ArrayList<I_Interceptor> interceptors;
    	 synchronized(this)
    	 {
    		 
    		 interceptors = interceptorList;
    	 }
    	 
    	 for(int index = 0; index<interceptors.size(); index++)
    	 {
    		 ClientRequestInterceptor i = (ClientRequestInterceptor) interceptors.get(index);
    		 i.onPayment(context);
    	 }
     }
	
}

