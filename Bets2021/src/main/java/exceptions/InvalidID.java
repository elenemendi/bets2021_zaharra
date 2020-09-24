package exceptions;

public class InvalidID extends Exception {

	
	private static final long serialVersionUID = 1L;

	public InvalidID() {
		super();

	}

	/**
	 * This exception is triggered if the ID card introduced does not follow the correct format
	 * @param s String of the exception
	 */
	public InvalidID(String s) {
		super(s);

	}

}
