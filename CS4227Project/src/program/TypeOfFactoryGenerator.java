package program;

import media.MediaFactory;
import userInterface.CommandFactory;
import userInterface.ReceiverFactory;
import users.UserFactory;

public class TypeOfFactoryGenerator {
	public static I_AbstractFactory getFactory(String factoryType)
	{
		if(factoryType == null)
			return null;
		else if(factoryType.equalsIgnoreCase("USER"))
			return new UserFactory();
		else if(factoryType.equalsIgnoreCase("MEDIA"))
			return new MediaFactory();
		else if(factoryType.equalsIgnoreCase("COMMAND"))
			return new CommandFactory();
		else if(factoryType.equalsIgnoreCase("RECEIVER"))
			return new ReceiverFactory();
		
		return null;
	}
}
