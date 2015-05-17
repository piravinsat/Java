
package calc;

import java.util.Scanner;

/**
 * @author Piravin
 *
 */
public class RevPolishCalc implements Calculator {

    /**
     * An instance of the NumStack class.
     */
    private NumStack values = new NumStack();

    /**
     * Evaluates the string expression.
     * @throws InvalidExpressionException Wrong expression
     * @return Answer to expression
     * @param what Expression
     */
    public final float evaluate(final String what)
    throws InvalidExpressionException {

        Scanner input = new Scanner(what);
        boolean includeOpr = false;

        if (what.trim().isEmpty() || what.equals("0")) {
            input.close();
            throw new InvalidExpressionException();

        }

        try {
        while (input.hasNext()) {
            if (input.hasNextFloat()) {
                values.push(input.nextFloat());
            } else {
                String nextOp = input.next();
                Symbol symOp = Symbol.INVALID;

                for (Symbol i : Symbol.values()) {
                    if (i.toString().equals(nextOp)) {
                        includeOpr = true;
                        symOp = i;
                        break;
                    }
                }
               if (symOp == Symbol.PLUS) {
                    values.push(values.pop() + values.pop());
               } else
                    if (symOp == Symbol.MINUS) {
                       float firstValue = values.pop();
                       values.push(values.pop() - firstValue);
                } else if (symOp == Symbol.TIMES) {
                    values.push(values.pop() * values.pop());
                } else if (symOp == Symbol.DIVIDE) {
                    float firstValue = values.pop();
                    values.push(values.pop() / firstValue);
                } else {
                    input.close();
                    throw new InvalidExpressionException();

                }
        }
    }
        input.close();

        if (!includeOpr) {
            throw new InvalidExpressionException();
        }

        float ans = values.pop();
        return ans;
        } catch (Exception e) {
            throw new InvalidExpressionException();
        }

    }
}
