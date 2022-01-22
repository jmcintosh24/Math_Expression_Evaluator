/**
 * This class contains a main method that calls three different static methods.
 * The first method parses the input string into a queue of Terms.
 * The second method converts the queue of Terms into a queue of Terms representing a postfix expression.
 * The third method uses the postfix expression queue to evaluate the final answer.
 * Course: COMP 2100
 * Assignment: Project 2
 *
 * @author Jacob McIntosh
 * @author Johnathan Kneice
 * @version 1.0, 10/7/2021
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InfixToPostfix {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter infix expression: ");
        String expression = in.nextLine();
        try {
            Queue inputQ = parseInput(expression); //Parses the input string into a queue of Terms
            Queue outputQ = infixToPostfix(inputQ); //Converts the queue of Terms into a postfix expression queue
            getAnswer(outputQ); //Evaluates the answer from the postfix expression queue
        } catch (InvalidOperandException | InvalidOperatorException | MissingOperandException | NoSuchElementException |
                UnbalancedLeftParenthesisException | UnbalancedRightParenthesisException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Turns a string into a queue of Terms.
     *
     * @param expression the input string from the user
     * @return the final queue of Terms
     * @throws InvalidOperandException
     * @throws InvalidOperatorException
     * @throws MissingOperandException
     */
    private static Queue parseInput(String expression) throws InvalidOperandException, InvalidOperatorException, MissingOperandException {
        Queue inputQ = new Queue();
        boolean expectingOperand = true;
        String num = "";
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (expectingOperand) { //If an operand is expected
                if (Character.isDigit(c) || c == '.') {
                    num += c;
                } else if (c == '+' || c == '-') {
                    if (num.length() == 0)
                        num += c;
                    else {
                        parseDouble(num, inputQ);
                        num = "";
                        i--;
                        expectingOperand = false;
                    }
                } else if ((c == '*' || c == '/' || c == ')') || (!(Character.isDigit(c)) && c != '(' && c != ' ')) {
                    parseDouble(num, inputQ);
                    num = "";
                    i--;
                    expectingOperand = false;
                } else if (c == ' ') {
                    if (num.length() > 0) {
                        parseDouble(num, inputQ);
                        num = "";
                        expectingOperand = false;
                    }
                } else if (c == '(') {
                    inputQ.enqueue(new Term(c));
                } else
                    throw new InvalidOperandException();
            } else { //If an operand is not expected.
                if (c != ' ') {
                    if (c == '+' || c == '-' || c == '/' || c == '*') {
                        inputQ.enqueue(new Term(c));
                        expectingOperand = true;
                    } else if (c == ')') {
                        inputQ.enqueue(new Term(c));
                    } else {
                        throw new InvalidOperatorException(c);
                    }
                }
            }
        }

        if (num.length() > 0)
            parseDouble(num, inputQ);
        if (inputQ.back().isOperand() == false && (inputQ.back().getOperator() != '(' && inputQ.back().getOperator() != ')'))
            throw new MissingOperandException();

        System.out.println("Standardized infix: " + inputQ.toString());
        return inputQ;
    }

    /**
     * Converts a queue of Terms into another queue of Terms representing a postfix expression.
     *
     * @param inputQ the queue of terms to be converted
     * @return the postfix queue
     * @throws UnbalancedLeftParenthesisException
     * @throws UnbalancedRightParenthesisException
     */
    private static Queue infixToPostfix(Queue inputQ) throws UnbalancedLeftParenthesisException, UnbalancedRightParenthesisException {
        Queue outputQ = new Queue();
        Stack opStack = new Stack();
        while (inputQ.size() > 0) {
            Term token = inputQ.dequeue();
            if (token.isOperand()) //If the token is an operand
                outputQ.enqueue(token);
            else if (token.getOperator() == '(') //If the token is a left parentheses
                opStack.push(token);
            else if (token.getOperator() == ')') { //If the token is a right parentheses
                while (opStack.size() > 0 && opStack.peek().getOperator() != '(') {
                    outputQ.enqueue(opStack.pop());
                }
                if (opStack.size() == 0)
                    throw new UnbalancedRightParenthesisException();
                opStack.pop();
            } else { //If the token is any other operator
                while (opStack.size() > 0 && precedence(token.getOperator()) <= precedence(opStack.peek().getOperator())) {
                    outputQ.enqueue(opStack.pop());
                }
                opStack.push(token);
            }
        }

        while (opStack.size() > 0) {
            if (opStack.peek().getOperator() == '(')
                throw new UnbalancedLeftParenthesisException();
            outputQ.enqueue(opStack.pop());
        }
        System.out.println("Postfix expression: " + outputQ);
        return outputQ;
    }

    /**
     * Evaluates the final answer from the postfix queue.
     *
     * @param outputQ the postfix queue
     */
    private static void getAnswer(Queue outputQ) {
        Stack operandStack = new Stack();
        while (outputQ.size() > 0) {
            Term token = outputQ.dequeue();
            if (token.isOperand())
                operandStack.push(token);
            else {
                double num1 = operandStack.pop().getOperand();
                double num2 = operandStack.pop().getOperand();
                operandStack.push(new Term(evaluate(num2, num1, token.getOperator())));
            }
        }
        System.out.println("Answer: " + operandStack.pop());
    }

    /**
     * A helper method that parses the given number into a double. Throws an InvalidOperandException
     * if it fails. If it succeeds, then the double is added to the queue.
     *
     * @param num
     * @param q   the queue we want to add the number to
     * @throws InvalidOperandException
     */
    private static void parseDouble(String num, Queue q) throws InvalidOperandException {
        try {
            q.enqueue(new Term(Double.parseDouble(num)));
        } catch (Exception e) {
            throw new InvalidOperandException();
        }
    }

    /**
     * A helper method that returns a number representing the precedence of an operator.
     *
     * @param c the operator
     * @return the number representing precedence
     */
    private static int precedence(char c) {
        if (c == '(')
            return 0;
        else if (c == '+' || c == '-')
            return 1;
        else
            return 2;
    }

    /**
     * A helper method that evaluates two numbers based on the operator given.
     *
     * @param num1
     * @param num2
     * @param c    the operator
     * @return the answer
     */
    private static double evaluate(double num1, double num2, char c) {
        if (c == '+')
            return num1 + num2;
        else if (c == '-')
            return num1 - num2;
        else if (c == '/')
            return num1 / num2;
        else
            return num1 * num2;
    }
}