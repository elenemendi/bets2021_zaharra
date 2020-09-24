package exceptions;

public class InvalidEmail extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidEmail() {
		super();

	}

	/**
	 * This exception is triggered if the email of the user does not follow the correct format
	 * @param s String of the exception
	 */
	public InvalidEmail(String s) {
		super(s);

	}

}
