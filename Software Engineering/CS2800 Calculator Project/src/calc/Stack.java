
package calc;

import java.util.ArrayList;

/**
 * This class represents a Stack of Entry values.
 * @author Piravin Satkunarajah
 */

public class Stack {

    /**
     * integer - Holds the the size of the stack.
     *
     */
    private int size;

    /**
     *Holds the values which were added to the stack by calling the push method.
     */
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    /**
     * Adds a new Entry to the Stack.
     * @param e - Entry
     */
    public final void push(final Entry e) {
        size++;
        entries.add(e);
    }

    /**
     * The top function returns the newest Entry in the stack.
     * @return newest Entry but keeps it also.
     * @throws EmptyStackException No entries in the stack
     */
    public final Entry top() throws EmptyStackException {
        if (size != 0) {
            return entries.get(size - 1);
            } else {
                throw new EmptyStackException();
                }
        }

    /**
     * The size function returns the size of the stack.
     * @return How many Entry's in the Stack
     */
    public final int size() {
        return size;
    }

    /**
     *The pop function returns newest entry in the stack
     *and removes it afterwards.
     * @return newest Entry and removes it
     * @throws EmptyStackException No entries in the stack
     */
    public final Entry pop() throws EmptyStackException {
        if (size != 0) {
            size--;
            return entries.remove(size);
            } else {
            throw new EmptyStackException();
            }
        }
}
