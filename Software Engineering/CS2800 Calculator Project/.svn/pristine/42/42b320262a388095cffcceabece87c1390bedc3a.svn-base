
package calc;

import java.util.Scanner;

/**
 * Shunting Yard algorithm by Edsger Dijkstra taken from
 * http://en.wikipedia.org/wiki/Shunting-yard_algorithm .
 * @author Piravin Satkunarajah
 *
 */
public class StandardCalc implements Calculator {

    /**
     * An instance of the StrStack class.
     */
    private StrStack str = new StrStack();
    /**
     * An instance of the OpStack class.
     */
    private OpStack values = new OpStack();
    /**
     * An instance of the RevPolishCalc class.
     */
    private RevPolishCalc rpCalc = new RevPolishCalc();

    /**
     *Evaluates the String expression.
     *@param what String expression
     *@return Answer
     *@throws InvalidExpressionException Invalid Expression used
     */
    public final float evaluate(final String what)
            throws InvalidExpressionException {
      System.out.println("Start");
        Scanner input = new Scanner(what);
        StringBuilder revString = new StringBuilder();

        if (what.trim().isEmpty() || what.equals("0")) {
            input.close();
            System.out.println(1);
            throw new InvalidExpressionException();
        }

        try {
            while (input.hasNext()) {
                if (input.hasNext()) {
                    str.push(input.next());
                }
            }

            while (!str.isEmpty()) {
                String nextToken;
                nextToken = str.pop();
                Scanner tok = new Scanner(nextToken);

                if (tok.hasNextFloat()) {
                    revString.append(nextToken + " ");
                } else {
                    Symbol symOp = Symbol.INVALID;
                    for (Symbol i : Symbol.values()) {
                      if (i.toString().equals(nextToken)) {
                        symOp = i;
                        break;
                      }
                    }

                    if (symOp != Symbol.LEFT_BRACKET
                            || symOp != Symbol.RIGHT_BRACKET
                            || symOp != Symbol.INVALID) {
                        values.push(symOp);
                        } else if (symOp == Symbol.LEFT_BRACKET) {
                        values.push(symOp);
                    } else if (symOp == Symbol.RIGHT_BRACKET) {
                        try {
                        Symbol nextOp = values.pop();
                        while (nextOp != Symbol.LEFT_BRACKET) {
                          revString.append(nextOp.toString() + " ");
                          nextOp = values.pop();
                        }
                        } catch (Exception e) {
                        tok.close();
                        System.out.println("2");
                        throw new InvalidExpressionException();
                    }
                    }
                }
                tok.close();
            }
            while (!values.isEmpty()) {
                Symbol nextOp = Symbol.INVALID;
                nextOp = values.pop();

                if (nextOp == Symbol.LEFT_BRACKET
                        || nextOp == Symbol.LEFT_BRACKET) {
                    input.close();
                    System.out.println("3");
                    throw new InvalidExpressionException();
                }
                revString.append(nextOp.toString() + " ");
                System.out.println(revString.toString() + " Hello");
            }
        } catch (Exception e) {
            System.out.println(revString.toString());
            throw new InvalidExpressionException();
        }
        input.close();
        System.out.println(revString.toString());
        return rpCalc.evaluate(revString.toString());
    }

}
