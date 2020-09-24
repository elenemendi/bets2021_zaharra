package exceptions;
public class EventUnfinished extends Exception {
 private static final long serialVersionUID = 1L;
 
 public EventUnfinished()
  {
    super();
  }
  /**This exception is triggered if the event hasn't finished
  *@param s String of the exception
  */
  public EventUnfinished(String s)
  {
    super(s);
  }
}