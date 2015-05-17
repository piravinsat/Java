import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import calc.BadTypeException;
import calc.Entry;
import calc.Symbol;
import calc.Type;

/**
 * The Test Entry class which used JUnit to test the entry class.
 * @author Piravin Satkunarajah
 */
public class TestEntry {

    /**
     * A object of the entry class.
     */
    private Entry en; //Created Entry class

    /**
     *The delta for all assertEquals using float parameters.
     */
    private final float delta = 0.001f;

    /**
     * The value used in getValue() method.
     */
    private final float getValueValue = 10.2f;

    /**
     *The value used in getValueV2() method.
     */
    private final float getValueV2Value = 2.2f;

    /**
     *The value used in getTypeInvalid() method.
     */
     private final float getWrongTypeValue = 2.32f;

     /**
      * The value used in getTypeNumber() method.
      */
     private final float getTypeNumberValue = 5.55f;

    /**
     * The setUp function to create constructors for the Entry class.
     */
    @Before
    public final void setUp() {
        en = new Entry(2f); //Created constructor with float parameter
        en = new Entry(Symbol.PLUS); //Created constructor
                                     //with symbol parameter
        en = new Entry("Yada Yada Yada"); //Created constructor
                                          //with string parameter
    }
    /**
     * Test 1 is the test which creates the method
     * and tries to return the type from getType().
     */
    @Test
    public final void getType() {
        en = new Entry(Symbol.PLUS);
        assertEquals("TEST1: Get Type", Type.SYMBOL , en.getType());
        //Created getType method
        //Complies but gives wrong value
        //Test succeeded by changing return value to Type.SYMBOL.
        //The refactoring from the getTypeString has made this fail again
        //Test succeeded by putting Type.SYMBOL into type variable of
        //the Entry(Symbol which) constructor
    }
    /**
     *Test 2 tests the getString method if it can return a string.
     *@throws BadTypeException When the value is not the right type
     */
    @Test
    public final void getString() throws BadTypeException {
        en = new Entry("DRINK!");
        assertEquals("TEST2: Get String", "DRINK!", en.getString());
        //Created getString method
        //Complies but gives wrong value
        //Test succeeded by changing return value to "DRINK!".
    }
    /**
     *Test 3 tests the getSymbol method if it can return at symbol.
     *@throws BadTypeException When the value is not the right type
     */
    @Test
    public final void getSymbol() throws BadTypeException {
        en = new Entry(Symbol.PLUS);
        assertEquals("TEST3: Get Symbol", Symbol.PLUS, en.getSymbol());
        //Created getSymbol method
        //Complies but gives wrong value
        //Test succeeded by changing return value to Symbol.PLUS.
    }
    /**
    *Test 4 tests the getValue method if it can return a float value.
    *@throws BadTypeException When the value is not the right type
    */
    @Test
    public final void getValue() throws BadTypeException {
        en = new Entry(getValueValue);
        assertEquals("TEST4: Get Value", getValueValue , en.getValue(), delta);
        //Created getValue method
        //Complies but gives wrong value of 0.0 instead of 10.2
        //Test succeeded by changing return value to 10.2
    }

    /**
     * Test 4.2 tests the getValue method if it can return a float value.
     * But this version of the tests with a different value.
     * @throws BadTypeException When the value is not the right type
     */
    @Test
    public final void getValueV2() throws BadTypeException {
        //Second version of the test with a different value
        en = new Entry(getValueV2Value);
        assertEquals("TEST4.2: Get Value", getValueV2Value, en.getValue(),
        delta);
        //Complies but returns 10.2 instead
        //Test succeeded by returning the number attribute in Entry.
    }

    /**
     * Test 3.2 tests the getSymbol method if it can return a symbol class.
     * But this version of the tests with a different value.
     * @throws BadTypeException - When the value is not the right type
     */
    @Test
    public final void getSymbolV2() throws BadTypeException {
        //Second version of the test with a different value
        en = new Entry(Symbol.MINUS);
        assertEquals("TEST3.2: Get Symbol", Symbol.MINUS, en.getSymbol());
        //Complies but returns Symbol.PLUS
        //Test succeeded by returning the symbol attribute in Entry.
    }

    /**
     * Test 2.2 tests the getString method if it can return a string.
     * This version of the test uses a different value.
     * @throws BadTypeException - When the value is not the right type
     */
    @Test
    public final void getStringV2() throws BadTypeException {
        //Second version of the test with a different value
        en = new Entry("Tucker's Law");
        assertEquals("TEST2.2: Get String", "Tucker's Law", en.getString());
        //Complies but returns "DRINK!"
        //Test succeeded by returning the string attribute in Entry.
    }

    /**
     *Test 5 tests the getType method if it can return Type.STRING
     *with a string.
     */
    @Test
    public final void getTypeString() {
        //Trying to get the correct type from a String.
        en = new Entry("Steve Holt!");
        assertEquals("TEST5: Get Type String", Type.STRING, en.getType());
        //Complies but returns type.NUMBER
        //Changed return to the type attribute in Entry but gives NULL instead.
        //Test succeeded by putting Type.STRING into type variable of
        //the Entry (String str) constructor.
    }

    /**
     *Test 6 tests the getType method if it can return Type.NUMBER
     *with a float.
     */
     @Test
    public final void getTypeNumber() {
        //Trying to get the correct type from a Float.
        en = new Entry(getTypeNumberValue);
        assertEquals("TEST6: Get Type Number ", Type.NUMBER, en.getType());
        //Test succeeded by putting Type.NUMBER into type variable of
        //the Entry(float value) constructor.
    }

    /**
     * Test 7 tests the getType method if it can throw a exception
     * with a value not a float, string or symbol.
     * @throws BadTypeException - When the value is not the right type
     */
     @Test(expected = BadTypeException.class)
    public final void getWrongType() throws BadTypeException {

        en = new Entry(getWrongTypeValue);
        en.getSymbol();
        en.getString();
        en.getValue();
        fail();
        //Complies but gives an Assertion error
        //Test succeeded by creating a BadTypeException class
        //and adding a if statement to the getString, getSymbol
        //and getValue methods.
    }
}
