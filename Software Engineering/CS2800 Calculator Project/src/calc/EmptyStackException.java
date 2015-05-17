
package calc;

/**
 * This exception is for when a stack has no values in the stack.
 * @author Piravin
 */
public class EmptyStackException extends Exception {

    /**
     *The serial version ID of this exception.
     */
     private static final long serialVersionUID = -6009427494248911426L;

    /**
     *Throws this exception whenever a stack has no values in the stack.
     */
     public EmptyStackException() {

        super.getMessage();
     }
}
