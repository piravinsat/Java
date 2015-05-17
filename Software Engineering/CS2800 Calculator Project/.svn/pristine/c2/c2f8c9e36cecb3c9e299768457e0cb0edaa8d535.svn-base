
package calc;

/**
 * A facade of the Stack class which only has numerical values.
 * @author Piravin Satkunarajah
 */
public class NumStack {

    /**
     * An instance variable of Stack.
     */
    private Stack numStack = new Stack();

    /**
     * Adds a new float value into NumStack.
     * @param i - float value
     */
    public final void push(final float i) {

        numStack.push(new Entry(i));
    }

    /**
     *The pop function returns newest float in the stack
     *and removes it afterwards.
     * @return newest float and removes it
     * @throws EmptyStackException No entries in the stack
     * @throws BadTypeException Wrong type
     */
    public final float pop() throws EmptyStackException, BadTypeException {

        return numStack.pop().getValue();
    }


    /**
     * The isEmpty functions returns if the Stack is empty or not.
     * @return True if size zero else false
     */
    public final boolean isEmpty() {

        return numStack.size() == 0;
    }

}
