package exceptions;

public class ExistingBet extends Exception{

	public ExistingBet() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to create a bet that already exists
	 * @param s String of the exception
	 */
	public ExistingBet(String s) {
		super(s);
		
	}
}
