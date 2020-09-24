package exceptions;
public class WinnerAlreadyExist extends Exception {
 private static final long serialVersionUID = 1L;
 
 public WinnerAlreadyExist()
  {
    super();
  }
  /**This exception is triggered if the question already has a winner Fee 
  *@param s String of the exception
  */
  public WinnerAlreadyExist(String s)
  {
    super(s);
  }
}