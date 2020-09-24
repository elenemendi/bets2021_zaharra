package exceptions;

public class NotMatchingPasswords extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NotMatchingPasswords() {
		super();
		
	}
	
	/**
	 * This exception is triggered if the two passwords inserted by the user are not the same
	 * @param s String of the exception
	 */
	public NotMatchingPasswords(String s) {
		super(s);
		
	}
	
}
