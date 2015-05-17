import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import calc.BadTypeException;
import calc.EmptyStackException;
import calc.NumStack;

/**
 * The Test Number Stack class which used JUnit to test the NumStack class.
 * @author Piravin Satkunarajah
 */
public class TestNumStack {

    /**
     * An object of the NumStack class.
     */

    private NumStack nu; //Created NumStack class

    /**
     *The delta used for all tests.
     */
    private final float delta = 0.001f;

    /**
     * The value used in Test 2.
     */
    private final float test2Value = 4.2f;

    /**
     * The first value used in Test 3.
     */
    private final float test3Value1st = 2.3f;

    /**
     * The second value used in Test 3.
     */
    private final float test3Value2nd = 3.2f;
    /**
     * The setUp function.
     */
    @Before
    public final void setUp() {
        nu = new NumStack();
    }

    /**
     * TEST1 tests the push and pop method in the NumStack class.
     * This checks if you can return the value you have just pushed into
     * the stack.
     * @throws BadTypeException Bad Type
     * @throws EmptyStackException Empty Stack
     */
    @Test
    public final void pushThenPop() throws EmptyStackException,
    BadTypeException {
        nu.push(2f);
        assertEquals("TEST1: Push then Pop", 2f, nu.pop(), delta);
        //Created push method.
        //Complies but returns wrong result.
        //Test succeeded by changing return value from 0 to 2f.
    }

    /**
     * TEST2 tests the isEmpty method in the NumStack class.
     *This checks if there's no elements in the Stack then isEmpty would be
     *true and false if it isn't.
     */
    @Test
    public final void pushThenisEmpty() {
        nu.push(test2Value);
        assertEquals("TEST2: Push then isEmpty", false, nu.isEmpty());
        //Created isEmpty method.
        //Complies but returns wrong result
        //Test succeeded by changing return value from true to false.
    }

    /**
     * TEST3 tests the pop method in the NumStack class.
     * This checks if you can pop the last element popped into the stack.
     * @throws BadTypeException Bad Type used
     * @throws EmptyStackException Empty Stack
     */
    @Test
    public final void pushThenPushThenPop()
            throws EmptyStackException, BadTypeException {
        nu.push(test3Value1st);
        nu.push(test3Value2nd);
        assertEquals("TEST3: Push then Push then Pop",
                test3Value2nd, nu.pop(), delta);
        //Complies but returns wrong result: 2.0.
        //Test succeeded by calling the push method from the Stack class in push
        //and calling the pop method from the Stack in pop
    }

    /**
     * TEST4 does the same test as TEST2 but with no elements in the stack.
     */
    @Test
    public final void isEmpty() {
        assertEquals("TEST4: isEmpty", true, nu.isEmpty());
        //Complies but isEmpty returns false
        //Test succeeded by calling the size method
        //from the Stack class in isEmpty
    }

}
