/**
 * This class has several testing methods.
 * These methods test everything that could go wrong with the program.
 * Course: COMP 2100
 * Assignment: Project 2
 *
 * @author Jacob McIntosh
 * @author Johnathan Kneice
 * @version 1.0, 10/7/2021
 */

import org.junit.jupiter.api.*;

import java.io.*;

//read in a string as if you had typed it into the terminal.
public class InfixTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;


    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String equation) {
        testIn = new ByteArrayInputStream(equation.getBytes());
        System.setIn(testIn);
    }

    //returns the output from the main.
    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    //organizes the infix, postFix and answer into what the output should look like.
    private static String buildOutput(String infix, String postFix, String answer) {
        return "Enter infix expression: " +
                "Standardized infix: " + infix + System.lineSeparator() +
                "Postfix expression: " + postFix +
                System.lineSeparator() + "Answer: " + answer + System.lineSeparator();
    }

    //organize data when an exception is thrown
    private String buildOutputException(String infix, String exception) {
        return "Enter infix expression: Standardized infix: " + infix +
                System.lineSeparator() + exception +
                System.lineSeparator();
    }

    private String buildOutputParsingError(String error) {
        return "Enter infix expression: " + error + System.lineSeparator();
    }

    @Test
    //test the add operator with no spaces
    public void testAdd() {
        String testEquation = ("9+9");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("9.0 + 9.0", "9.0 9.0 +", "18.0"), getOutput());
    }

    @Test
    //test the subtraction operator with no spaces.
    public void testSub() {
        String testEquation = ("4-7");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("4.0 - 7.0", "4.0 7.0 -", "-3.0"), getOutput());
    }

    @Test
    //test the division operator with no spaces.
    public void testDiv() {
        String testEquation = ("10/5");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("10.0 / 5.0", "10.0 5.0 /", "2.0"), getOutput());
    }

    @Test
    //test the multiply operator with no spaces.
    public void testMult() {
        String testEquation = ("5*3");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("5.0 * 3.0", "5.0 3.0 *", "15.0"), getOutput());
    }

    @Test
    //test the add operator with spaces.
    public void testAddSpaces() {
        String testEquation = ("9 + 9");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("9.0 + 9.0", "9.0 9.0 +", "18.0"), getOutput());
    }

    @Test
    //test the subtraction operator with spaces.
    public void testSubSpaces() {
        String testEquation = ("4 - 7");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("4.0 - 7.0", "4.0 7.0 -", "-3.0"), getOutput());
    }

    @Test
    //test the division operator with spaces.
    public void testDivSpaces() {
        String testEquation = ("10 / 5");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("10.0 / 5.0", "10.0 5.0 /", "2.0"), getOutput());
    }

    @Test
    //test the multiply operator with spaces.
    public void testMultSpaces() {
        String testEquation = ("5 * 3");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("5.0 * 3.0", "5.0 3.0 *", "15.0"), getOutput());
    }

    @Test
    //test the use of parentheses to disambiguate.
    public void testParenthesesDisambiguation() {
        String testEquation = ("((9*9)+1)");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("( ( 9.0 * 9.0 ) + 1.0 )", "9.0 9.0 * 1.0 +", "82.0"), getOutput());
    }

    @Test
    //testing when there is an extra left parentheses
    public void testUnbalancedLeft() {
        String testEquation = ("((9+9)");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputException("( ( 9.0 + 9.0 )", "Unbalanced left parenthesis '('"), getOutput());
    }

    @Test
    //testing when there is an extra right parentheses
    public void testUnbalancedRight() {
        String testEquation = ("( 9+9))");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputException("( 9.0 + 9.0 ) )", "Unbalanced right parenthesis ')'"), getOutput());
    }

    //testing scientific notation
    @Test
    public void testScientific() {
        String testEquation = ("602000000000000000000000 - 14.231 * +800000000000000000000");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("6.02E23 - 14.231 * 8.0E20", "6.02E23 14.231 8.0E20 * -", "5.906152E23"), getOutput());
    }

    @Test
    //test a single number being entered
    public void testSingle() {
        String testEquation = ("9");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("9.0", "9.0", "9.0"), getOutput());
    }

    @Test
    //test a negative number
    public void testNegative() {
        String testEquation = ("(-9.0+8.0)");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("( -9.0 + 8.0 )", "-9.0 8.0 +", "-1.0"), getOutput());
    }

    @Test
    //test a lot of spaces being entered.
    public void testLotOfSpace() {
        String testEquation = ("(    9.9            +    8.1      )/2.0 ");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("( 9.9 + 8.1 ) / 2.0", "9.9 8.1 + 2.0 /", "9.0"), getOutput());
    }

    @Test
    //test resizing a stack or queue.
    public void testResize() {
        String testEquation = ("(((((9.9+8.1)/(2.0+ 0.0)*9))))+1+1+1+1-1-2+9 ");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("( ( ( ( ( 9.9 + 8.1 ) / ( 2.0 + 0.0 ) * 9.0 ) ) ) ) + 1.0 + 1.0 + 1.0 + 1.0 - 1.0 " +
                        "- 2.0 + 9.0", "9.9 8.1 + 2.0 0.0 + / 9.0 * 1.0 + 1.0 + 1.0 + 1.0 + 1.0 - 2.0 - 9.0 +"
                , "91.0"), getOutput());
    }

    @Test
    //test a positive number being entered.
    public void testPositive() {
        String testEquation = ("(+9.0+8.0)");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutput("( 9.0 + 8.0 )", "9.0 8.0 +", "17.0"), getOutput());
    }

    @Test
    //test nothing being entered
    public void testSpace() {
        String testEquation = (" ");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputParsingError("null"), getOutput());
    }

    @Test
    //test a invalid operand being entered.
    public void testInvalidOperand() {
        String testEquation = ("9+a");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputParsingError("Invalid operand"), getOutput());
    }

    //multiple bad operands
    @Test
    public void testInvalidMultOperand() {
        String testEquation = ("(9+a.0)-o.i+z-0");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputParsingError("Invalid operand"), getOutput());
    }


    @Test
    // test a invalid operator being entered
    public void testInvalidOperator() {
        String testEquation = ("9#9");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputParsingError("Invalid operator: #"), getOutput());
    }

    //multiple bad operators
    @Test
    public void testInvalidMultOperator() {
        String testEquation = ("9#9+{1+1}=9");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputParsingError("Invalid operator: #"), getOutput());
    }

    @Test
    // test missing opperand.
    public void testMissing() {
        String testEquation = ("9+");
        provideInput(testEquation);

        InfixToPostfix.main(new String[0]);
        Assertions.assertEquals(buildOutputParsingError("Missing operand"), getOutput());
    }
}

