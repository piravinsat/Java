
package calc;

/**
 * Values stored in a Stack.
 * @author Piravin Satkunarajah
 */
public class Entry {

    /**
     *The instance variable which where float values are placed.
     */
    private float number;
    /**
     *The instance variable which where symbol values are placed.
     */
    private Symbol other;
    /**
     *The instance variable which where String values are placed.
     */
    private String str;
    /**
     *The instance variable which where Type values are placed.
     */
    private Type type;

    /**
     *Entry constructor which accepts floats and places it in
     *the number instance variable.
     * @param value - float value.
     */
    public Entry(final float value) {
        number = value;
        type = Type.NUMBER;
    }

    /**
     *Entry constructor which accepts symbols and places it in
     *the other instance variable.
     * @param which - Symbol value.
     */
    public Entry(final Symbol which) {
        other = which;
        type = Type.SYMBOL;
    }

    /**
     *Entry constructor which accepts strings and places it in
     *the str instance variable.
     * @param value - String value.
     */
    public Entry(final String value) {
        str = value;
        type = Type.STRING;
        }

    /**
     *The getType function which returns type.
     *@return The type the value is representing.
     */
    public final Type getType() {
        return type;
        }

    /**
     *The getString function returns a string value.
     *@throws BadTypeException When value is not correct type
     *@return The string value.
     */
    public final String getString() throws BadTypeException {
        if (getType() != Type.STRING) {
        throw new BadTypeException();
        } else {
            return str;
        }
    }

    /**
     *The getSymbol function returns a symbol value.
     *@throws BadTypeException - When value is not correct type
     *@return The symbol value.
     */
    public final Symbol getSymbol() throws BadTypeException {
        if (getType() != Type.SYMBOL) {
            throw new BadTypeException();
        } else {
        return other;
        }
    }

    /**
     *The getValue function returns a float value.
     *@throws BadTypeException - When value is not correct type
     *@return The float value.
     */
    public final Float getValue() throws BadTypeException {
        if (getType() != Type.NUMBER) {
            throw new BadTypeException();
        } else {
        return number;
        }
    }
}
