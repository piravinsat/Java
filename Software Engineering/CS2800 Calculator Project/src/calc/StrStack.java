
package calc;

/**
 * A facade of the Stack class which is used by Standard Calc.
 * @author Piravin Satkunarajah
 */
public class StrStack {

    /**
     * An instance variable of Stack.
     */
    private Stack strStack = new Stack();


    /**
     * Adds a new string value into StrStack.
     * @param str A string value
     */
    public final void push(final String str) {

        strStack.push(new Entry(str));
    }

    /**
     *The pop function returns newest string value in the stack
     *and removes it afterwards.
     * @return newest string and removes it
     * @throws EmptyStackException No entries in the stack
     * @throws BadTypeException Bad type used
     */
    public final String pop() throws EmptyStackException, BadTypeException {

        return strStack.pop().getString();
    }


    /**
     * The isEmpty functions returns if the Stack is empty or not.
     * @return True if size zero else false
     */
    public final boolean isEmpty() {

        return strStack.size() == 0;
    }

}
