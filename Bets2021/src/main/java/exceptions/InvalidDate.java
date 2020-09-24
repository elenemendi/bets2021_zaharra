package exceptions;

public class InvalidDate extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidDate() {
		super();

	}
	
	/**
	 * The exception is triggered if the inserted date is incorrect
	 * @param s String of the exception
	 */
	public InvalidDate(String s) {
		super(s);

	}

}
