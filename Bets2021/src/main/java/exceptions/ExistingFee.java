package exceptions;

public class ExistingFee extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistingFee() {
		super();
		
	}
	
	/**
	 * This exception is triggered when trying to create a fee that already exists
	 * @param s String of the exception
	 */
	public ExistingFee(String s) {
		super(s);
		
	}
}
