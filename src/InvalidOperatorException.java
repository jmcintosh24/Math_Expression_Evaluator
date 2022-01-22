/**
 * Course: COMP 2100
 * Assignment: Project 2
 *
 * @author Johnathan Kneice
 * @author Jacob McIntosh
 * @version 1.0, 10/7/2021
 */

public class InvalidOperatorException extends Exception {
    public InvalidOperatorException(char c) {
        super("Invalid operator: " + c);
    }
}
