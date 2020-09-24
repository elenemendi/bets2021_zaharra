package exceptions;

public class UnspecifiedMovement extends Exception{

	public UnspecifiedMovement() {
		super();
		
	}
	
	/**
	 * This exception is triggered when the type of money Movement a RegisteredUser wants to make is not specified (insert or take)
	 * @param s String of the exception
	 */
	public UnspecifiedMovement(String s) {
		super(s);
		
	}
}

