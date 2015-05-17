package calc;

/**
 * CalcModel class decides which Calculator to use.
 * @author Piravin
 */
public class CalcModel {

    /**
     * Instance of the RPN calculator.
     */
    private Calculator revPolish = new RevPolishCalc();
    /**
     * Instance of the Standard calculator.
     */
    private Calculator standard = new StandardCalc();

    /**
     *
     * @param expr The expression inputed from the user.
     * @param infix Boolean of calculator want to be used
     * is set to infix or not.
     * @return the answer.
     * @throws InvalidExpressionException
     * when a invalid expression is inputed.
     */
    public final float evaluate(final String expr, final boolean infix)
            throws InvalidExpressionException {

        if (infix) {
            return standard.evaluate(expr);
        } else {
            return revPolish.evaluate(expr);
        }
    }
}
