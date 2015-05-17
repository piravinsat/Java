import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import calc.BadTypeException;
import calc.EmptyStackException;
import calc.OpStack;
import calc.Symbol;

/**
 * The Test Operation Stack class which used JUnit to test the OpStack class.
 * @author Piravin Satkunarajah
 */
public class TestOpStack {

    /**
     * An object of the OpStack class.
     */
    private OpStack op; //Created OpStack class

    /**
     * The setUp function for the OpStack class.
     */
    @Before
    public final void setUp() {
        op = new OpStack();
    }

    /**
     * TEST1 tests the push and pop method in the OpStack class.
     * This checks if you can return the value you have just pushed into
     * the stack.
     * @throws BadTypeException Bad Type
     * @throws EmptyStackException Empty Stack
     */
    @Test
    public final void pushThenPop() throws
    EmptyStackException, BadTypeException {
        op.push(Symbol.DIVIDE);
        assertEquals("TEST1: Push then Pop", Symbol.DIVIDE, op.pop());
        //Created push method.
        //Created pop method.
        //Complies but returns wrong result.
        //Test succeeded by changing return value
        //from Symbol.INVALID to Symbol.DIVIDE.
    }

    /**
     * TEST2 tests the isEmpty method in the OpStack class.
     * This checks if there's no elements in the Stack
     * then isEmpty would be true and false if it isn't.
     */
    @Test
    public final void pushThenisEmpty() {
        op.push(Symbol.LEFT_BRACKET);
        assertEquals("TEST2: Push then isEmpty", false, op.isEmpty());
        //Created isEmpty method.
        //Complies but returns wrong result
        //Test succeeded by changing return value from true to false.
    }

    /**
     * TEST3 tests the pop method in the OpStack class.
     * This checks if you can pop the last element popped into the stack.
     * @throws BadTypeException Bad Type
     * @throws EmptyStackException Empty Stack
     */
    @Test
    public final void pushThenPushThenPop()
            throws EmptyStackException, BadTypeException {
        op.push(Symbol.DIVIDE);
        op.push(Symbol.MINUS);
        assertEquals("TEST3: Push then Push then Pop", Symbol.MINUS, op.pop());
        //Complies but returns wrong result: DIVIDE.
        //Test succeeded by calling the push method from the Stack class in push
        //and calling the pop method from the Stack in pop
    }

    /**
     * TEST4 does the same test as TEST2 but with no elements in the stack.
     */
    @Test
    public final void isEmpty() {
        assertEquals("TEST4: isEmpty", true, op.isEmpty());
        //Complies but isEmpty returns false
        //Test succeeded by calling the size method
        //from the Stack class in isEmpty.
    }
}
