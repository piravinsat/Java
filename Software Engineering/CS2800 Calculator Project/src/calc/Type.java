
package calc;

/**
 *A list of the types of value an Entry can represent.
 *@author Piravin Satkunarajah
 */
public enum Type {
    /**
     * This represents the float value.
     */
    NUMBER,

    /**
     * This represents the symbol value.
     */
    SYMBOL,

    /**
     * This represents the string value.
     */
    STRING,

    /**
     * This represents the value that isn't the above three.
     */
    INVALID
}
