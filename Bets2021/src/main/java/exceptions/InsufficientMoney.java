package exceptions;

public class InsufficientMoney extends Exception{

	public InsufficientMoney() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to bet or take more money than the user has
	 * @param s String of the exception
	 */
	public InsufficientMoney(String s) {
		super(s);
		
	}
}
