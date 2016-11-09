package logging;

import java.io.*;

import interceptor.contextObjects.I_ContextObj;

public class Logger {
	
	public void logPayment(I_ContextObj context) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter("payment.log", true));
		bw.write(context.getDescription());
		bw.newLine();
		bw.close();
	}

}
