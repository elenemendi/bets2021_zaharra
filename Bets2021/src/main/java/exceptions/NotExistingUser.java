package exceptions;

public class NotExistingUser extends Exception{

	public NotExistingUser() {
		super();
		
	}
	
	
	/**
	 * This exception is triggered when trying to get a user that does not exist	 
	 * @param s String of the exception
	 */
	public NotExistingUser(String s) {
		super(s);
		
	}
}
