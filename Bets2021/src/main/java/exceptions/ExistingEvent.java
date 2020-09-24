package exceptions;

public class ExistingEvent extends Exception {

	public ExistingEvent() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to create a event that already exists
	 * @param s String of the exception
	 */
	public ExistingEvent(String s) {
		super(s);
		
	}
}
