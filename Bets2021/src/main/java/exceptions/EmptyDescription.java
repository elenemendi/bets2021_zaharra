package exceptions;

public class EmptyDescription extends Exception{

	public EmptyDescription() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to create a event or question with empty description
	 * @param s String of the exception
	 */
	public EmptyDescription(String s) {
		super(s);
		
	}
}
