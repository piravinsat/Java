import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import calc.BadTypeException;
import calc.EmptyStackException;
import calc.StrStack;

/**
 * The Test String Stack class which used JUnit to test the StrStack class.
 * @author Piravin Satkunarajah
 */
public class TestStrStack {

    /**
     * An object of the StrStack class.
     */

    private StrStack st; //Created StrStack class

    /**
     * The setUp function for the StrStack class.
     */
    @Before
    public final void setUp() {
        st = new StrStack();
    }

    /**
     * TEST1 tests the push and pop method in the StrStack class.
     * This checks if you can return the value you have just pushed into
     * the stack.
     * @throws BadTypeException Bad Type
     * @throws EmptyStackException Empty Stack
     */
    @Test
    public final void pushThenPop()
            throws EmptyStackException, BadTypeException {
        st.push("Hello!");
        assertEquals("TEST1: Push then Pop", "Hello!", st.pop());
        //Created pop method.
        //Created push method.
        //Complies but returns wrong result.
        //Test succeeded by changing return value.
        //from "Blah Blah Blah" to "Hello!".
    }

    /**
     * TEST2 tests the isEmpty method in the StrStack class.
     *This checks if there's no elements in the Stack then isEmpty would be true
     * and false if it isn't.
     */
    @Test
    public final void pushThenisEmpty() {
        st.push("Ashens");
        assertEquals("TEST2: Push then isEmpty", false, st.isEmpty());
        //Created isEmpty method.
        //Complies but returns wrong result
        //Test succeeded by changing return value from true to false.
    }

    /**
     * TEST3 tests the pop method in the StrStack class.
     * This checks if you can pop the last element popped into the stack.
     * @throws BadTypeException Bad Type
     * @throws EmptyStackException Empty Stack
     */
    @Test
    public final void pushThenPushThenPop()
            throws EmptyStackException, BadTypeException {
        st.push("Chef Excellence");
        st.push("An Excellent Element");
        assertEquals("TEST3: Push then Push then Pop",
                "An Excellent Element", st.pop());
        //Complies but returns wrong result: "Hello".
        //Test succeeded by calling the push method from the Stack class in push
        //and calling the pop method from the Stack in pop
    }

    /**
     * TEST4 does the same test as TEST2 but with no elements in the stack.
     */
    @Test
    public final void isEmpty() {
        assertEquals("TEST4: isEmpty", true, st.isEmpty());
        //Complies but isEmpty returns false
        //Test succeeded by
        //calling the size method from the Stack class in isEmpty.
    }
}
