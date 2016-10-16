package users;

import java.io.IOException;

import database.Database;

public interface I_UserActions {
	
	Database database = Database.getInstance();
	void userActions(String returnedSelection, String user) throws IOException;
}