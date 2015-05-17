package calc;

/**
 * @author Piravin
 *
 */
public class OpStack {

    /**
     * An instance variable of stack.
     */
    private Stack opStack = new Stack();


    /**
     * Adds a new float value into OpStack.
     * @param i - float value
     */
    public final void push(final Symbol i) {

        opStack.push(new Entry(i));
    }

    /**
     *The pop function returns newest Symbol in the stack
     *and removes it afterwards.
     * @return newest float and removes it
     * @throws EmptyStackException No entries in the stack
     * @throws BadTypeException Wrong type used
     */
    public final Symbol pop() throws BadTypeException, EmptyStackException {

        return opStack.pop().getSymbol();
    }

    /**
     * The isEmpty functions returns if the Stack is empty or not.
     * @return True if size zero else false
     */
    public final boolean isEmpty() {

        return opStack.size() == 0;
    }

}
