import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import calc.Calculator;
import calc.InvalidExpressionException;
import calc.StandardCalc;

/**
 * @author Piravin Satkunarajah
 *
 */
public class TestStandardCalc {

    /**
     * Instance of Calculator.
     */
    private Calculator sta;

    /**
     * Value used for TEST1.
     */
    private final float testOneValue = 5.0f;

    /**
     * The delta value for all tests.
     */
    private final float delta = 0.001f;

    /**
     * Sets up the StandardCalc class.
     */
    @Before
    public final void setUp() {
        sta = new StandardCalc();
    }

    /**
     * TEST1 tests with an infix expression.
     * @throws InvalidExpressionException Invalid Expression
     */
    @Test
    public final void inFixTest() throws InvalidExpressionException {
        assertEquals("Test 1: Plus", testOneValue,
                sta.evaluate("3 + 2"), delta);
    }

}
