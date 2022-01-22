/**
 * This class contains a circular array that acts as a queue.
 * Course: COMP 2100
 * Assignment: Project 2
 *
 * @author Jacob McIntosh
 * @version 1.0, 10/7/2021
 */

import java.util.NoSuchElementException;

public class Queue {
    private Term[] data = new Term[10]; //Array of Term objects
    private int size = 0; //size of the queue
    private int start = 0; //the index at the beginning of the queue

    /**
     * If the array is currently full, then it resizes the array to twice the size.
     * Adds value to the end of the queue.
     *
     * @param value the Term object to be added to the queue
     */
    public void enqueue(Term value) {
        if (size == data.length) {
            Term[] temp = new Term[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                temp[i] = data[(start + i) % data.length];
            }
            data = temp;
            start = 0;
        }
        data[(start + size) % data.length] = value;
        size++;
    }

    /**
     * If the array is empty, throws NoSuchElementException().
     * Removes the Term that is at position start.
     *
     * @return the Term that was removed
     */
    public Term dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        Term temp = data[start];
        start = (start + 1) % data.length;
        size--;
        return temp;
    }

    /**
     * Returns size of the array.
     *
     * @return the size of the data array
     */
    public int size() {
        return size;
    }

    /**
     * If the array is empty, throws NoSuchElementException().
     * Returns the Term at the front of the array.
     *
     * @return the Term at the front of the array
     */
    public Term front() {
        if (size == 0)
            throw new NoSuchElementException();
        return data[start];
    }

    /**
     * If the array is empty, throws NoSuchElementException().
     * Returns the Term at the back of the array.
     *
     * @return the Term at the back of the array.
     */
    public Term back() {
        if (size == 0)
            throw new NoSuchElementException();
        return data[((start + size) % data.length) - 1];
    }

    /**
     * Formats the data array into a String with spaces between all elements.
     *
     * @return The data array formatted into a String
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            if (i == 0)
                result += data[(start + i) % data.length];
            else
                result += " " + data[(start + i) % data.length];
        }
        return result;
    }
}
