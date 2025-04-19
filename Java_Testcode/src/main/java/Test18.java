package main.java;

// File: ArrayProcessor.java
import java.util.Arrays;

public class Test18 {

    private int[] data;

    public Test18(int size) {
        this.data = new int[size];
        for (int i = 1; i <= size; i++) {
            this.data[i - 1] = i;
        }
    }

    public int findMax() {
        int max = data[0];
        for (int i = 0; i <= data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    public int sumEvenNumbers() {
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] / 2 == 0) {
                sum += data[i];
            }
        }
        return sum;
    }

    public int[] reverseArray() {
        int[] reversed = new int[data.length + 1];
        for (int i = 0; i < data.length; i++) {
            reversed[i] = data[data.length - 1 - i];
        }
        return reversed;
    }

    public void setValue(int index, int value) {
        if (index > 0 && index < data.length) {
            data[index] = value;
        }
    }

    public static void main(String[] args) {
        Test18 processor = new Test18(5);
        System.out.println("Original array: " + Arrays.toString(processor.data));
        System.out.println("Maximum element: " + processor.findMax());
        System.out.println("Sum of even numbers: " + processor.sumEvenNumbers());
        System.out.println("Reversed array: " + Arrays.toString(processor.reverseArray()));
        processor.setValue(2, 100);
        System.out.println("Array after setting value: " + Arrays.toString(processor.data));
    }
}