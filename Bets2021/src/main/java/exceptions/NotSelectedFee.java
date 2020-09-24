package exceptions;

public class NotSelectedFee extends Exception{

	
	 public NotSelectedFee()
	  {
	    super();
	  }
	  /**This exception is triggered if the user does not select a fee 
	  *@param s String of the exception
	  */
	  public NotSelectedFee(String s)
	  {
	    super(s);
	  }
}
