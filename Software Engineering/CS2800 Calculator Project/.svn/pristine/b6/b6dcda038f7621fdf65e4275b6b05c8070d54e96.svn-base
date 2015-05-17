package calc;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

/**
 * This was created mostly by WindowBuilder.
 * @author Piravin
 */
public class CalcView extends JFrame {


    /**
     * Serial id of CalcView.
     */
    private static final long serialVersionUID = -4073714931153620805L;
    /**
     * The JPanel which holds the rest below.
     */
    private JPanel contentPane;
    /**
     * This is where the user inputs the expression.
     */
    private JTextField txtExpression = new JTextField();
    /**
     * This is where the user picks the RPN calculator.
     */
    private JRadioButton rdbtnRPN = new JRadioButton("Reverse Polish Notation");
    /**
     * This is where the user picks the Standard calculator.
     */
    private JRadioButton rdbtnStandard = new JRadioButton("Standard");
    /**
     * This is clicked to enter the expression to get the result.
     */
    private JButton btnNewButton = new JButton("Calculate");
    /**
     * This is where the answer is shown.
     */
    private JLabel lblAnswerIsHere = new JLabel("Answer is here");
    /**
     * The radio buttons is grouped with this.
     */
    private final ButtonGroup buttonGroup = new ButtonGroup();

//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    CalcView frame = new CalcView();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public CalcView() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 153, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout(
                "", "[][][][grow][][]", "[][][][][][][][][]"));

        JLabel lblTitle = new JLabel(
                "Reverse Polish Notation/Standard Calculator");
        lblTitle.setFont(new Font("Gill Sans MT", Font.PLAIN, 16));
        contentPane.add(lblTitle, "cell 3 0");
        buttonGroup.add(rdbtnRPN);

        rdbtnRPN.setForeground(new Color(255, 255, 255));
        rdbtnRPN.setBackground(new Color(51, 0, 255));
        contentPane.add(rdbtnRPN, "flowx,cell 3 2");
        buttonGroup.add(rdbtnStandard);

        rdbtnStandard.setBackground(new Color(51, 0, 255));
        rdbtnStandard.setForeground(new Color(255, 255, 255));
        contentPane.add(rdbtnStandard, "cell 3 2,alignx right,aligny baseline");

        JLabel lblNameExpression = new JLabel("Expression");
        contentPane.add(lblNameExpression, "cell 0 4 2 1");

        txtExpression.setText("Type in here");
        contentPane.add(txtExpression, "cell 3 4,growx");
        txtExpression.setColumns(10);

        contentPane.add(btnNewButton, "cell 3 6,alignx center");

        JLabel lblResult = new JLabel("Result:");
        contentPane.add(lblResult, "cell 0 8");

        contentPane.add(lblAnswerIsHere, "cell 3 8");
    }

    /**
     * Adds the Action Listener to the Calculate button.
     * @param mal Action Listener
     */
    public final void addCalculateListener(final ActionListener mal) {
        btnNewButton.addActionListener(mal);
    }

    /**
     * Adds the Action Listener to the Radio buttons.
     * @param mal Action Listener
     */
    public final void addRadioListener(final ActionListener mal) {
        rdbtnRPN.addActionListener(mal);
        rdbtnStandard.addActionListener(mal);
    }

    /**
     * Gets the user input from the user.
     * @return the expression.
     */
    public final String getUserInput() {
        return txtExpression.getText();
    }

    /**
     * Sets the answer to the GUI.
     * @param answer The answer from the expression.
     */
    public final void setAnswer(final String answer) {
        lblAnswerIsHere.setText(answer);
    }
}
