package team.bitwin.ui;

import team.bitwin.algo.bisection.Bisection;

import javax.swing.*;

import java.util.Stack;

import static team.bitwin.ui.Utils.hasBlank;
import static team.bitwin.ui.Utils.hasInvalidNumber;

public class MainWindow {
    private JPanel contentPane;
    private JTextField tfFunctionInput;
    private JTabbedPane tbMethods;
    private JTextField tfBisectionA;
    private JTextField tfBisectionB;
    private JTextField tfBisectionE;
    private JTextField tfNewtonA;
    private JTextField tfNewtonE;
    private JTextField tfSecantX0;
    private JTextField tfSecantX1;
    private JTextField tfSecantE;
    private JButton btnCalculate;

    public MainWindow() {
        attachListeners();
    }

    private void attachListeners() {
        btnCalculate.addActionListener(onclick -> {
            try {
                // GENERAL EXECUTION FLOW:
                //  [1] Check for empty inputs
                //  [2] Check if inputs are valid
                //  [3] Check if function is valid
                //  [4] Evaluate expression
                //  [5] Display result

                if (hasBlank(tfFunctionInput)) {
                    alertWarning("Missing required input", "Input your f(x) first");
                    return;
                }

                if (tbMethods.getSelectedIndex() == 0) { // Selected bisection method
                    if (hasBlank(tfBisectionA, tfBisectionB, tfBisectionE)) {
                        alertWarning("Missing required input", "Input your bisection method first");
                        return;
                    }

                    if (hasInvalidNumber(tfBisectionA, tfBisectionB, tfBisectionE)) {
                        alertWarning("Invalid value", "Make sure your inputs are valid number");
                        return;
                    }

                    System.out.println("Working on bisection method");

                    double aD = Double.parseDouble(tfBisectionA.getText());
                    double bD = Double.parseDouble(tfBisectionB.getText());
                    double eD = Double.parseDouble(tfBisectionE.getText());

                    final Bisection bisection = new Bisection();

                    if (bisection.isValidInterval(aD, bD, tfFunctionInput.getText())) {
                        var result = bisection.approximateRoot(aD, bD, eD, tfFunctionInput.getText(), new Stack<>());
                        new ResultDialog(result).setVisible(true);
                    } else {
                        alertWarning("Please try again", "Interval has no roots");
                    }

                } else if (tbMethods.getSelectedIndex() == 1) { // Selected newton method
                    if (hasBlank(tfNewtonA, tfNewtonE)) {
                        alertWarning("Missing required input", "Input your values of newton's method");
                        return;
                    }

                    if (hasInvalidNumber(tfNewtonA, tfNewtonE)) {
                        alertWarning("Invalid value", "Make sure your inputs are valid number");
                        return;
                    }

                    System.out.println("Working on newton method");
                } else if (tbMethods.getSelectedIndex() == 2) { // Selected secant method
                    if (hasBlank(tfSecantX0, tfSecantX1, tfSecantE)) {
                        alertWarning("Missing required input", "Input your values of secant method first");
                        return;
                    }

                    if (hasInvalidNumber(tfSecantX0, tfSecantX1, tfSecantE)) {
                        alertWarning("Invalid value", "Make sure your input are valid number");
                        return;
                    }

                    System.out.println("Working on secant method");
                }
            } catch (Exception e) {
                e.printStackTrace();
                alertWarning("Something is wrong", e.getMessage());
            }
        });
    }

    private void alertWarning(String title, String message) {
        JOptionPane.showMessageDialog(contentPane, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public JFrame createWindow() {
        final JFrame frame = new JFrame("Math 113 - Machine Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(contentPane);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
