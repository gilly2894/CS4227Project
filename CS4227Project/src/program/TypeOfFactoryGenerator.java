package program;

import media.MediaFactory;
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
		
		return null;
	}
}
