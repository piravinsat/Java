import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import calc.BadTypeException;
import calc.EmptyStackException;
import calc.Entry;
import calc.Stack;
import calc.Symbol;

/**
 * The Test Stack class which used JUnit to test the stack class.
 * @author Piravin Satkunarajah
 */
public class TestStack {

    /**
     * The stack instance variable that each test method uses.
     */
    private Stack st; //Created stack class.

    /**
     * This holds the delta for all assertEquals using float parameters.
     */
    private final float delta = 0.001f;

    /**
     * The value used for the pushThenPopThenSize method.
     */
    private final float pushThenPopThenSizeValue = 19f;

    /**
     *The value used for the pushThenTop method.
     */
    private final float pushThenTopValue = 5f;

    /**
     *The value used for all other methods not including the above floats.
     */
    private final float threeFValue = 3f;

    /**
     * This method sets up the test with a creation of the Stack class.
     */
    @Before
    public final void setUp() {
        st = new Stack(); //Time saver.
    }
    /**
     * Test 1 is the test which tries to push a value into the stack
     * then gets the value on top of the stack which is the same one.
     * @throws EmptyStackException Where there are no entries in the stack
     * @throws BadTypeException When the pushed value is of wrong type
     */
    @Test
    public final void pushThenTop() throws EmptyStackException,
    BadTypeException {
        st.push(new Entry(pushThenTopValue)); //Written to create push method
        assertEquals("TEST1: push then Top", pushThenTopValue,
                st.top().getValue(), delta);
        //Test used to create top method.
        //Test succeeded by changing the return value from 0 to 5.
        //As I changed List type from int to Entry,
        //I've changed the pushed value to new Entry (5f)
        //Test succeeded by adding new exception, calling getValue and adding
        //a delta
    }
    /**
     * Test 2 is the test which tries to push a value into the stack
     * and then get the correct size of stack.
     */
    @Test
    public final void pushThenSize() {
        st.push(new Entry(threeFValue));
        assertEquals("TEST2: push then Size", 1, st.size());
        //Test used to create size method.
        //Test succeeded by changing the return value from 0 to 1.
        //As I changed the List type from int to Entry,
        //I've changed the pushed value to new Entry (threeFValue)
    }

    /**
     * Test 3 tests the pop method by putting a value on the stack then
     * calling the pop method to return the value and remove it.
     * @throws EmptyStackException When there are no entries in the stack
     * @throws BadTypeException When the pushed value is of wrong type
     */
    @Test
    public final void pushThenPop() throws EmptyStackException,
    BadTypeException {
        st.push(new Entry(threeFValue));
        assertEquals("TEST3: Push then pop", threeFValue, st.pop().getValue(),
                delta);
        //Test used to create pop method.
        //Test succeeded by changing the return value from 0 to 3.
        //As I changed the list type from int to Entry,
        //I've changed the pushed value to new Entry (3f)
    }
    /**
     *Test 1.2 tests the Top method again but with a different value this time
     *to get the value from the top of the stack.
     * @throws EmptyStackException When there are no entries in the stack
     * @throws BadTypeException When the pushed value is of wrong type
     */
    @Test
    public final void pushThenTopV2() throws EmptyStackException,
    BadTypeException {
        //Second version of the test with different value pushed.
        //The attributes of the Stack class is added for this and further tests.

        st.push(new Entry("Bacon"));
        assertEquals("TEST1.2: push then top", "Bacon", st.top().getString());
        //Complies but test fails
        //because wrong value is received which is 5 not 7.
        //Test succeeded by implementing push and top methods code.
        //As I changed the list type from int to Entry,
        //I've changed the pushed value to new Entry ("Bacon")
        //Test succeeded by adding BadTypeException and calling getString
    }

    /**
     *Test 4 tests push and size methods by adding two values into the stack
     *and getting the correct size of the stack.
     */
    @Test
    public final void pushThenPushThenSize() {
        st.push(new Entry(Symbol.MINUS));
        st.push(new Entry(threeFValue));
        assertEquals("TEST 4: then size", 2, st.size());
        //Complies but test fails
        //because wrong value is received which is 1 not 2.
        //Test succeeded by returning the size variable in the method.
        //As I changed list type from int to Entry,
        //I've changed the pushed values
    }
    /**
     * Test 5 tests if the size is correct when there's nothing the size
     * from an value being added and then taken out the stack.
     * @throws EmptyStackException Where there are no entries in the stack
     */
    @Test
    public final void pushThenPopThenSize() throws EmptyStackException {
        st.push(new Entry(pushThenPopThenSizeValue));
        st.pop();
        assertEquals("TEST 5: push then pop then size", 0, st.size());
        //Complies but test fails because wrong value received which is 1 not 0.
        //Test succeeded by implementing pop method code.
        //As I changed list type from int to Entry, I changed the pushed value
        //to new Entry(19f)
    }

    /**
     * Test 6 tests the pop method if it gives an EmptyStack exception when
     * trying to pop the stack with nothing in the stack.
     * @throws EmptyStackException - When there are no entries in the stack
     */
    @Test(expected = EmptyStackException.class)
    public final void pop() throws EmptyStackException {
        //TEST 6
        st.pop();
        fail();
        //Complies but test fails
        //because it gave an ArrayIndexOutOfBoundsException.
        //Test succeeded
        //by write an EmptyStackException class and a if/else statement.
    }

    /**
     * Test 7 tests the top method if it gives an EmptyStack exception
     * when trying to return the top of the stack with nothing in
     * the stack.
     * @throws EmptyStackException - When there are no entries in the stack
     */
    @Test(expected = EmptyStackException.class)
    public final void top() throws EmptyStackException {
        //TEST 7
        st.top();
        fail();
        //Complies but test fails
        //because it gave an ArrayIndexOutOfBoundsException.
        //Test succeeded
        //by write an EmptyStackException class and a if/else statement.
    }
}
