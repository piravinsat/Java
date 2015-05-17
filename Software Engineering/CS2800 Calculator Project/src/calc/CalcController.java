package calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

/**
 * The controller for the Calculator GUI which links the CalcView and CalcModel.
 * @author Piravin Satkunarajah
 */
final class CalcController {

    /**
     * The instance variable of where CalcModel class is used.
     */
    private CalcModel model = new CalcModel();
    /**
     * The instance variable of where CalcView class is used.
     */
    private CalcView view = new CalcView();
    /**
     * The instance variable where it is used to hold
     * if the inFix radio button was selected.
     */
    private boolean isInfix;

    /**
     * The constructor for CalcController class which adds Listeners.
     */
    private CalcController() {
        view.addCalculateListener(new CalculateListener());
        view.addRadioListener(new RadioListener());
        view.setVisible(true);
    }

    /**
     * The main class which calls the constructor.
     * @param args Arguments
     */
    public static void main(final String[] args) {
        new CalcController();
    }
    /**
     * CalculateListener which uses ActionListener.
     * @author Piravin
     */
    private class CalculateListener implements ActionListener {
        /**
         * This calls the evaluate method
         * and then sets the answer on the GUI.
         *@param e Action Event
         */
        public void actionPerformed(final ActionEvent e) {
            try {
                String answer = " "
            + model.evaluate(view.getUserInput(), isInfix);
                view.setAnswer(answer);
            } catch (InvalidExpressionException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * RadioListener which uses ActionListener.
     * @author Piravin
     */
    private class RadioListener implements ActionListener {
        /**
         * This listen if the radio button set is
         * the infix one or rpn.
         *@param e Action Event
         */
        public void actionPerformed(final ActionEvent e) {
            isInfix = !((JRadioButton) e.getSource()).isSelected();
        }
    }
}
