package exceptions;

public class Underage extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Underage() {
		super();

	}
	
	
	/**
	 * This exception is triggered if the registeredUser is underage
	 * @param s String of the exception
	 */
	public Underage(String s) {
		super(s);

	}

}
