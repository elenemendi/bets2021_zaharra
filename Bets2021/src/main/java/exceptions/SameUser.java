package exceptions;

public class SameUser extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SameUser() {
		super();

	}

	/**
	 * This exception is triggered if a user tries to replicate yourself
	 * @param s String of the exception
	 */
	public SameUser(String s) {
		super(s);

	}

}
