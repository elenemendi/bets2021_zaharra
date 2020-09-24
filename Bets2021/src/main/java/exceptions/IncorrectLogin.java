package exceptions;

public class IncorrectLogin extends Exception {
	
	private static final long serialVersionUID = 1L;

	public IncorrectLogin() {
		super();

	}

	/**
	 * This exception is triggered when the log in is incorrect
	 * @param s String of the exception
	 */
	public IncorrectLogin(String s) {
		super(s);

	}

}
