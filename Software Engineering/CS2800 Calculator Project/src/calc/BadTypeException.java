
package calc;

/**
 * This exception is for values that cannnot be represent by Entry.
 * @author Piravin Satkunarajah
 */
public class BadTypeException extends Exception {

     /**
      *The serial version ID of this exception.
      */
    private static final long serialVersionUID = -1360493222942085389L;

     /**
     * This exception activates when it is given a value that
     * Entry can't represent.
     */
    public BadTypeException() {
       super.getMessage();
    }
}
