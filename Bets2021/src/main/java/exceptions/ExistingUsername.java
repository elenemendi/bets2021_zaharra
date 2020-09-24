package exceptions;

public class ExistingUsername extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExistingUsername() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to register with a username that already exists
	 * @param s String of the user
	 */
	public ExistingUsername(String s) {
		super(s);
		
	}
	
}
