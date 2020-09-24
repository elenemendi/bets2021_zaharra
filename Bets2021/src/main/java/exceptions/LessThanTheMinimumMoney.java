package exceptions;

public class LessThanTheMinimumMoney extends Exception {
	
	public LessThanTheMinimumMoney() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to bet less than the minimun money of the question
	 * @param s String of the exception
	 */
	public LessThanTheMinimumMoney(String s) {
		super(s);
		
	}

}
