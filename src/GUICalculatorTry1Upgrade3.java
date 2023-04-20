import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GUICalculatorTry1Upgrade3 extends JFrame implements ActionListener {

    JButton[] button = new JButton[11];
    String[] expressions = {"+", "-", "*", "/", "=", "<-", "C"};
    JButton[] expressionsButton = new JButton[7];

    JLabel displayLabel;

    String input = "";
    String inputDisplay = "";
    double value1;
    double value2;
    double answer;
    char expression;
    boolean input1Complete = false;

    GUICalculatorTry1Upgrade3() {

        //JButton
        for (int i = 0; i < 11; i++) {
            // Index 10 is comma
            button[i] = new JButton(String.valueOf(i));
            if (i == 10) button[i] = new JButton(",");
            button[i].setPreferredSize(new Dimension(150, 150));
            button[i].addActionListener(this);
        }
        for (int i = 0; i < 7; i++) {
            expressionsButton[i] = new JButton(expressions[i]);
            expressionsButton[i].setPreferredSize(new Dimension(100, 100));
            expressionsButton[i].addActionListener(this);
        }

        //JLabel
        displayLabel = new JLabel();
        displayLabel.setText("");
        displayLabel.setPreferredSize(new Dimension(600, 100));
        displayLabel.setLayout(new BorderLayout());
        displayLabel.setFont(new Font("Times New Roman", Font.BOLD, 48));
        displayLabel.setBackground(Color.RED);
        displayLabel.setOpaque(true);
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        //JPanel (Design)
            //calcBodyPanel
        JPanel calcBodyPanel = new JPanel();
        calcBodyPanel.setPreferredSize(new Dimension(400, 600));
        calcBodyPanel.setBackground(Color.GREEN);
        calcBodyPanel.setOpaque(true);
        calcBodyPanel.setLayout(new GridLayout(4, 3, 15, 15));
        for (int i = 1; i < 11; i++) {
            calcBodyPanel.add(button[i]);
        }
        calcBodyPanel.add(button[0]);
            //expressionBodyPanel
        JPanel expressionBodyPanel = new JPanel();
        expressionBodyPanel.setPreferredSize(new Dimension(200, 600));
        expressionBodyPanel.setBackground(Color.ORANGE);
        expressionBodyPanel.setOpaque(true);
        expressionBodyPanel.setLayout(new GridLayout(4, 2, 8, 8));
        for (int i = 0; i < 7; i++) {
            expressionBodyPanel.add(expressionsButton[i]);
        }
            //bodyDown
        JPanel bodyDown = new JPanel();
        bodyDown.setPreferredSize(new Dimension(600,600));
        bodyDown.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        bodyDown.add(calcBodyPanel, BorderLayout.WEST);
        bodyDown.add(expressionBodyPanel, BorderLayout.EAST);
            //bodyUp
        JPanel bodyUp = new JPanel();
        bodyUp.setPreferredSize(new Dimension(600, 100));
        bodyUp.setLayout(new BorderLayout());
        bodyUp.setBackground(Color.CYAN);
        bodyUp.add(displayLabel, BorderLayout.EAST);
            //body
        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());
        body.add(bodyUp, BorderLayout.NORTH);
        body.add(bodyDown, BorderLayout.SOUTH);

        //JFrame (Window)
        this.setTitle("Calculator");
        this.setSize(600, 700);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.add(body);
        this.pack();
    }

    //Methods
    public double calculate(double val1, char expr, double val2) {
        double tempAnswer = 0;
        if (expr == '+') tempAnswer = val1 + val2;
        else if (expr == '-') tempAnswer = val1 - val2;
        else if (expr == '*') tempAnswer = val1 * val2;
        else if (expr == '/') tempAnswer = val1 / val2;
        return tempAnswer;
    }

    public void updateDisplay() {
        displayLabel.setText(inputDisplay);
    }

    public void expression() {
        if (input1Complete) {
            //Check if input has any value
            if (Objects.equals(input, "")) {
                System.out.println("Input does not have any value");
                inputDisplay = inputDisplay.substring(0, (inputDisplay.length() > 0)?inputDisplay.length()-1:0);
                return;
            }
            value2 = Double.parseDouble(input.replace(',', '.'));

            answer = calculate(value1, expression, value2);
            inputDisplay = Double.toString(answer);
            updateDisplay();
            input = Double.toString(answer);
            value2 = 0;
            input1Complete = false;
        }
        else {
            //Check if input has any value
            if (Objects.equals(input, "")) {
                System.out.println("Input does not have any value");
                inputDisplay = inputDisplay.substring(0, (inputDisplay.length() > 0)?inputDisplay.length()-1:0);
                return;
            }
            value1 = Double.parseDouble(input.replaceAll(",+", "."));
            updateDisplay();
            input = "";
            input1Complete = true;
        }
    }
    public void backwardButton() {
        input = input.substring(0, (input.length() > 0)?input.length()-1:0);
        inputDisplay = inputDisplay.substring(0, (inputDisplay.length() > 0)?inputDisplay.length()-1:0);
        updateDisplay();
    }
    public void clearButton() {
        input = "";
        inputDisplay = "";
        updateDisplay();
        input1Complete = false;
    }
    public void equalButton() {
        if (!input1Complete) {
            System.out.println("You cannot place '=' here");
            return;
        }
        expression();
    }

    //Initiate
    public static void main(String[] args) {
        new GUICalculatorTry1Upgrade3();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 11; i++) {
            if (e.getSource() == button[i]) {
                input = input.concat(button[i].getText());
                inputDisplay = inputDisplay.concat(button[i].getText());
                updateDisplay();
                break;
            }
        }
        for (int i = 0; i < 7; i++) {
            if (e.getSource() == expressionsButton[i]) {
                if (expressionsButton[i].getText().equals("<-")) backwardButton();
                else if (expressionsButton[i].getText().equals("C")) clearButton();
                else if (expressionsButton[i].getText().equals("=")) equalButton();
                else {
                    inputDisplay = inputDisplay.concat(expressionsButton[i].getText());
                    expression = expressionsButton[i].getText().charAt(0);
                    expression();
                }
            }
        }
    }
}
