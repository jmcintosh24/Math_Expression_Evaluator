/**
 * Course: COMP 2100
 * Assignment: Project 2
 *
 * @author Jacob McIntosh
 * @author Johnathan Kneice
 * @version 1.0, 10/7/2021
 */

public class Term {
    private char operator;
    private double operand;
    private boolean isOperand;

    public Term(char c) {
        operator = c;
        isOperand = false;
    }

    public Term(double d) {
        operand = d;
        isOperand = true;
    }

    public char getOperator() {
        return operator;
    }

    public double getOperand() {
        return operand;
    }

    public boolean isOperand() {
        return isOperand;
    }

    public String toString() {
        if (isOperand)
            return "" + operand;
        return "" + operator;
    }
}
