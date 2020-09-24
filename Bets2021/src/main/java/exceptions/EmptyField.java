package exceptions;

public class EmptyField extends Exception {

	public EmptyField() {
		super();
		
	}
	
	/**
	 * This exception is triggered if one of the registration field is empty
	 * @param s String of the exception
	 */
	public EmptyField(String s) {
		super(s);
		
	}
}
