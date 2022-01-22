/**
 * This class uses an array to create a stack. This allows you to remove, add, see what is at the top and
 * how many items are in the array. If the array size is equal to the size variable, the array is resized to be
 * double the length of its size.
 * Course: COMP 2100
 * Assignment: Project 2
 *
 * @author Johnathan Kneice
 * @version 1.0, 10/7/2021
 */

import java.util.NoSuchElementException;

public class Stack {
    private Term[] data = new Term[10];
    private int size = 0;

    //Put value on top of the stack. if the size is = to the length of the stack
    //resize the array to be 2* size.
    public void push(Term value) {
        if (size == data.length) {
            Term[] temp = new Term[size * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
        data[size] = value;
        size++;
    }

    //Remove the top value.
    public Term pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Term temp = data[size - 1];
        data[size - 1] = null;
        size--;
        return temp;
    }

    //Show what the value is on top of stack.
    public Term peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[size - 1];
    }

    //Show how many items are in the stack.
    public int size() {
        return size;

    }

    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            if (i == 0)
                result += data[i];
            else
                result += " " + data[i];
        }
        return result;
    }
}

