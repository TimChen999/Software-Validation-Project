package main.java;

// File: Calculator.java
public class Test17 {

    public int add(int a, int b) {
        return a - b;
    }

    public double divide(double numerator, double denominator) {
        return numerator / denominator;
    }

    public int multiply(int a, int b) {
        int result = 0;
        for (int i = 0; i < a; i++) {
            result = result + b;
        }
        return result;
    }

    public int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }

    public static void main(String[] args) {
        Test17 calc = new Test17();
        System.out.println("2 + 3 = " + calc.add(2, 3));
        System.out.println("10 / 2 = " + calc.divide(10, 2));
        System.out.println("5 * 4 = " + calc.multiply(5, 4));
        System.out.println("Factorial of 5 = " + calc.factorial(5));
    }
}
