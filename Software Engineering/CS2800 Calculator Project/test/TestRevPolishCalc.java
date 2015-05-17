import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import calc.Calculator;
import calc.InvalidExpressionException;
import calc.RevPolishCalc;

/**
 * The test class for RevPolishCalc.
 * @author Piravin Satkunarajah
 */
public class TestRevPolishCalc {

    /**
     * An object of the Calculator class.
     */

    private Calculator rev; //Created RevPolishCalc class.

    /**
     * The delta used for all tests.
     */
     private final float delta = 0.001f;

     /**
      * The value used for TEST 6.
      */
      private final float testSixValue = 5.0f;

      /**
       * The value used for TEST 8.
       */
       private final float testEightValue = 6.0f;

       /**
        * The value used for TEST 9.
        */
       private final float testNineValue = 3.0f;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public final void setUp() {
        rev = new RevPolishCalc();
    }

    /**
     *TEST1 tests with an empty expression.
     *@throws InvalidExpressionException Invalid Expression
     */
    @Test(expected = InvalidExpressionException.class)
    public final void noInput() throws InvalidExpressionException {
        rev.evaluate("");
        fail();
        //Failed because it needs the InvalidExpressionException thrown.
        //Complies but it gives an AssertionError instead.
        //Test succeeded by adding a if statement
        //which returns a exception when string is "".
    }

    /**
     *TEST2 tests with an expression with a whitespace.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test(expected = InvalidExpressionException.class)
    public final void spaceInput() throws InvalidExpressionException {
        rev.evaluate(" ");
        fail();
        //Complies but it gives an AssertionError instead.
        //Test succeeded by changing the condition to the
        //if statement to when string is empty when trimmed.
    }
    /**
     *TEST3 tests with an expression that is just zero.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test(expected = InvalidExpressionException.class)
    public final void zeroInput() throws InvalidExpressionException {
        assertEquals("TEST3: Inputting a 0", 0, rev.evaluate("0"), delta);
        //Complies but it gives an AssertionError instead.
        //Test succeeded by adding a condition to the if
        //statement if the string equalling "0".
    }
    /**
     * TEST4 tests with an expression with no operator.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test(expected = InvalidExpressionException.class)
    public final void noOpr() throws InvalidExpressionException {
        rev.evaluate("3 2");
        fail();
        //Complies but it gives an AssertionError instead.
        //Test succeeded by adding a boolean
        //which becomes true when a symbol is used
}
    /**
     * TEST5 tests with an infix expression.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test(expected = InvalidExpressionException.class)
    public final void inFixExp() throws InvalidExpressionException {
        rev.evaluate("3 + 2");
        fail();
        //Complies but it gives an AssertionError instead.
        //Test succeeded by adding a catch
        //for a InvalidExpressionException when an inFix
        //expression is inputed.
    }
    /**
     * TEST6 tests with an adding expression.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test
    public final void plusExp() throws InvalidExpressionException {
        assertEquals("TEST6: 3 2 +", testSixValue,
                rev.evaluate("3 2 +"), delta);
        //Complies but gives an InvalidExpressionException.
        //Test succeeded by adding a condition which adds two floats when
        //the operation of the stack is Symbol.PLUS.
    }
    /**
     * TEST7 tests with an minus expression.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test
    public final void minusExp() throws InvalidExpressionException {
        assertEquals("TEST7: 3 2 -", 1.0f, rev.evaluate("3 2 -"), delta);
        //Complies but gives an InvalidExpressionException.
        //Test succeeded by adding a condition which minuses two floats
        //when the operation of the stack is Symbol.MINUS.
    }
    /**
     * TEST8 with an times expression.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test
    public final void timesExp() throws InvalidExpressionException {
        assertEquals("TEST8: 3 2 *", testEightValue,
                rev.evaluate("3 2 *"), delta);
        //Complies but gives an InvalidExpressionException.
        //Test succeeded by adding a condition which times two floats
        //when the operation of the stack is Symbol.TIMES.
    }

    /**
     * TEST9 with an division expression.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test
    public final void divideExp() throws InvalidExpressionException {
        assertEquals("TEST9: 6 2 /", testNineValue,
                rev.evaluate("6 2 /"), delta);
        //Complies but gives an InvalidExpressionException.
        //Test succeeded by adding a condition which divides two floats
        //when the operation of the stack
    }
}
