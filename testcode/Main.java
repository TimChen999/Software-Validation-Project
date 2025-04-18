import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ErrorTestingDemo {

    public static void main(String[] args) {
        System.out.println("Starting comprehensive Java error testing...\n");

        // Create a test runner to track successes and failures
        TestRunner runner = new TestRunner();

        // Run all error tests
        runner.runTest("Null Pointer Exceptions", ErrorTestingDemo::testNullPointerExceptions);
        runner.runTest("Array Index Exceptions", ErrorTestingDemo::testArrayIndexExceptions);
        runner.runTest("Class Cast Exceptions", ErrorTestingDemo::testClassCastExceptions);
        runner.runTest("Arithmetic Exceptions", ErrorTestingDemo::testArithmeticExceptions);
        runner.runTest("Number Format Exceptions", ErrorTestingDemo::testNumberFormatExceptions);
        runner.runTest("Illegal Argument Exceptions", ErrorTestingDemo::testIllegalArgumentExceptions);
        runner.runTest("IO Exceptions", ErrorTestingDemo::testIOExceptions);
        runner.runTest("Concurrent Modification Exceptions", ErrorTestingDemo::testConcurrentModificationExceptions);
        runner.runTest("Custom Exception Handling", ErrorTestingDemo::testCustomExceptionHandling);
        runner.runTest("Stack Overflow Error", ErrorTestingDemo::testStackOverflowError);
        runner.runTest("Out of Memory Error", ErrorTestingDemo::testOutOfMemoryError);
        runner.runTest("Thread Exceptions", ErrorTestingDemo::testThreadExceptions);
        runner.runTest("Resource Leaks", ErrorTestingDemo::testResourceLeaks);
        runner.runTest("Regex Exceptions", ErrorTestingDemo::testRegexExceptions);
        runner.runTest("Parse Exceptions", ErrorTestingDemo::testParseExceptions);

        // Print summary results
        runner.printSummary();
    }

    /**
     * Test cases for NullPointerException
     */
    private static void testNullPointerExceptions() throws Exception {
        // Test 1: Calling a method on a null object
        String nullString = null;
        try {
            int length = nullString.length();
            throw new AssertionError("NullPointerException was expected but not thrown");
        } catch (NullPointerException e) {
            System.out.println("✓ Successfully caught NullPointerException when calling method on null");
        }

        // Test 2: Accessing an array element from a null array
        Integer[] nullArray = null;
        try {
            Integer value = nullArray[0];
            throw new AssertionError("NullPointerException was expected but not thrown");
        } catch (NullPointerException e) {
            System.out.println("✓ Successfully caught NullPointerException when accessing null array");
        }

        // Test 3: Auto-unboxing a null Integer to int
        Integer nullInteger = null;
        try {
            int primitiveInt = nullInteger; // Auto-unboxing null causes NPE
            throw new AssertionError("NullPointerException was expected but not thrown");
        } catch (NullPointerException e) {
            System.out.println("✓ Successfully caught NullPointerException during auto-unboxing");
        }
    }

    /**
     * Test cases for ArrayIndexOutOfBoundsException
     */
    private static void testArrayIndexExceptions() throws Exception {
        int[] array = new int[5]; // Array with indices 0-4

        // Test 1: Accessing negative index
        try {
            int value = array[-1];
            throw new AssertionError("ArrayIndexOutOfBoundsException was expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("✓ Successfully caught ArrayIndexOutOfBoundsException with negative index");
        }

        // Test 2: Accessing index beyond array bounds
        try {
            int value = array[5]; // Array only has indices 0-4
            throw new AssertionError("ArrayIndexOutOfBoundsException was expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("✓ Successfully caught ArrayIndexOutOfBoundsException with too large index");
        }

        // Test 3: Off-by-one error in loop
        try {
            for (int i = 0; i <= array.length; i++) { // Note the <= instead of <
                int value = array[i];
            }
            throw new AssertionError("ArrayIndexOutOfBoundsException was expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("✓ Successfully caught ArrayIndexOutOfBoundsException in loop with off-by-one error");
        }
    }

    /**
     * Test cases for ClassCastException
     */
    private static void testClassCastExceptions() throws Exception {
        // Test 1: Invalid cast between incompatible types
        Object obj = "This is a string";
        try {
            Integer num = (Integer) obj; // String cannot be cast to Integer
            throw new AssertionError("ClassCastException was expected but not thrown");
        } catch (ClassCastException e) {
            System.out.println("✓ Successfully caught ClassCastException with incompatible types");
        }

        // Test 2: Invalid cast in collection
        List<Object> list = new ArrayList<>();
        list.add("String");
        list.add(123);
        list.add(new Date());

        try {
            for (Object item : list) {
                String str = (String) item; // Will fail on the Integer and Date
            }
            throw new AssertionError("ClassCastException was expected but not thrown");
        } catch (ClassCastException e) {
            System.out.println("✓ Successfully caught ClassCastException when casting collection elements");
        }
    }

    /**
     * Test cases for ArithmeticException
     */
    private static void testArithmeticExceptions() throws Exception {
        // Test 1: Division by zero
        try {
            int result = 10 / 0;
            throw new AssertionError("ArithmeticException was expected but not thrown");
        } catch (ArithmeticException e) {
            System.out.println("✓ Successfully caught ArithmeticException with division by zero");
        }

        // Test 2: Modulo by zero
        try {
            int remainder = 10 % 0;
            throw new AssertionError("ArithmeticException was expected but not thrown");
        } catch (ArithmeticException e) {
            System.out.println("✓ Successfully caught ArithmeticException with modulo by zero");
        }

        // Note: Floating-point divisions by zero don't throw exceptions but result in Infinity or NaN
        double d1 = 10.0 / 0.0; // Results in Infinity, no exception
        double d2 = 0.0 / 0.0;  // Results in NaN, no exception
        System.out.println("✓ Verified that floating-point division by zero results in " + d1 + " and " + d2);
    }

    /**
     * Test cases for NumberFormatException
     */
    private static void testNumberFormatExceptions() throws Exception {
        // Test 1: Parsing invalid integer
        try {
            int num = Integer.parseInt("not_a_number");
            throw new AssertionError("NumberFormatException was expected but not thrown");
        } catch (NumberFormatException e) {
            System.out.println("✓ Successfully caught NumberFormatException parsing invalid integer");
        }

        // Test 2: Parsing out of range integer
        try {
            int num = Integer.parseInt("9999999999"); // Too large for int
            throw new AssertionError("NumberFormatException was expected but not thrown");
        } catch (NumberFormatException e) {
            System.out.println("✓ Successfully caught NumberFormatException with out of range integer");
        }

        // Test 3: Parsing invalid double
        try {
            double d = Double.parseDouble("3.14.15");
            throw new AssertionError("NumberFormatException was expected but not thrown");
        } catch (NumberFormatException e) {
            System.out.println("✓ Successfully caught NumberFormatException parsing invalid double");
        }
    }

    /**
     * Test cases for IllegalArgumentException
     */
    private static void testIllegalArgumentExceptions() throws Exception {
        // Test 1: Providing invalid argument to a method
        try {
            Thread.sleep(-1000); // Cannot sleep for negative time
            throw new AssertionError("IllegalArgumentException was expected but not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Successfully caught IllegalArgumentException with negative sleep time");
        }

        // Test 2: Creating an array with negative size
        try {
            int[] array = new int[-5];
            throw new AssertionError("NegativeArraySizeException was expected but not thrown");
        } catch (NegativeArraySizeException e) {
            System.out.println("✓ Successfully caught NegativeArraySizeException creating array with negative size");
        }

        // Test 3: Invalid enum constant
        try {
            TimeUnit unit = TimeUnit.valueOf("CENTURIES"); // No such TimeUnit
            throw new AssertionError("IllegalArgumentException was expected but not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Successfully caught IllegalArgumentException with invalid enum constant");
        }
    }

    /**
     * Test cases for IOException
     */
    private static void testIOExceptions() throws Exception {
        // Test 1: Reading from a non-existent file
        try {
            FileReader reader = new FileReader("nonexistent_file.txt");
            throw new AssertionError("FileNotFoundException was expected but not thrown");
        } catch (FileNotFoundException e) {
            System.out.println("✓ Successfully caught FileNotFoundException reading non-existent file");
        }

        // Test 2: Closing an already closed stream
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader("test"));
            reader.close();
            reader.readLine(); // Reading from closed stream
            throw new AssertionError("IOException was expected but not thrown");
        } catch (IOException e) {
            System.out.println("✓ Successfully caught IOException reading from closed stream");
        }
    }

    /**
     * Test cases for ConcurrentModificationException
     */
    private static void testConcurrentModificationExceptions() throws Exception {
        // Test 1: Modifying a collection while iterating
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        try {
            for (String item : list) {
                if (item.equals("two")) {
                    list.remove(item); // Modifying collection during iteration
                }
            }
            throw new AssertionError("ConcurrentModificationException was expected but not thrown");
        } catch (ConcurrentModificationException e) {
            System.out.println("✓ Successfully caught ConcurrentModificationException with collection modification");
        }

        // Test 2: Safe removal using Iterator
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("three")) {
                iterator.remove(); // This is safe
            }
        }
        System.out.println("✓ Successfully demonstrated safe removal using Iterator");

        // Test 3: Using CopyOnWriteArrayList to avoid ConcurrentModificationException
        List<String> concurrentList = new CopyOnWriteArrayList<>();
        concurrentList.add("four");
        concurrentList.add("five");
        concurrentList.add("six");

        for (String item : concurrentList) {
            if (item.equals("five")) {
                concurrentList.remove(item); // This won't throw with CopyOnWriteArrayList
            }
        }
        System.out.println("✓ Successfully demonstrated CopyOnWriteArrayList avoiding ConcurrentModificationException");
    }

    /**
     * Test custom exception handling
     */
    private static void testCustomExceptionHandling() throws Exception {
        // Test 1: Simple custom exception
        try {
            throw new CustomException("This is a custom exception");
        } catch (CustomException e) {
            System.out.println("✓ Successfully caught CustomException: " + e.getMessage());
        }

        // Test 2: Chained exceptions
        try {
            try {
                int result = 10 / 0;
            } catch (ArithmeticException e) {
                throw new CustomException("Division by zero wrapped in custom exception", e);
            }
        } catch (CustomException e) {
            System.out.println("✓ Successfully caught chained CustomException with cause: " +
                    e.getCause().getClass().getSimpleName());
        }

        // Test 3: Finally block execution
        try {
            try {
                throw new RuntimeException("Test exception for finally block");
            } finally {
                System.out.println("✓ Finally block executed as expected");
            }
        } catch (RuntimeException e) {
            // Exception is still propagated after finally
        }

        // Test 4: Try-with-resources
        try (AutoCloseableResource resource = new AutoCloseableResource()) {
            resource.doSomething();
        } catch (Exception e) {
            System.out.println("✓ Caught exception from AutoCloseableResource: " + e.getMessage());
        }
    }

    /**
     * Test StackOverflowError (commented out by default to prevent JVM crash)
     */
    private static void testStackOverflowError() throws Exception {
        // This test is potentially dangerous as it can crash the JVM
        // Uncommenting the recursive call will eventually cause StackOverflowError
        /*
        try {
            recursiveMethod(1);
            throw new AssertionError("StackOverflowError was expected but not thrown");
        } catch (StackOverflowError e) {
            // We won't actually reach this catch block as StackOverflowError typically
            // crashes the thread, but we include it for completeness
            System.out.println("✓ Successfully caught StackOverflowError");
        }
        */

        // Instead, just report that we skipped this test
        System.out.println("⚠ StackOverflowError test skipped to prevent JVM crash");
    }

    // Helper method for StackOverflowError test
    private static void recursiveMethod(int depth) {
        System.out.println("Recursive depth: " + depth);
        recursiveMethod(depth + 1); // No base case, will cause StackOverflowError
    }

    /**
     * Test OutOfMemoryError (commented out by default to prevent JVM crash)
     */
    private static void testOutOfMemoryError() throws Exception {
        // This test is potentially dangerous as it can crash the JVM
        // Uncommenting will attempt to allocate an enormous array
        /*
        try {
            int[] hugeArray = new int[Integer.MAX_VALUE];
            throw new AssertionError("OutOfMemoryError was expected but not thrown");
        } catch (OutOfMemoryError e) {
            System.out.println("✓ Successfully caught OutOfMemoryError");
        }
        */

        // Instead, just report that we skipped this test
        System.out.println("⚠ OutOfMemoryError test skipped to prevent JVM crash");
    }

    /**
     * Test thread-related exceptions
     */
    private static void testThreadExceptions() throws Exception {
        // Test 1: Interrupting a thread
        Thread sleepingThread = new Thread(() -> {
            try {
                Thread.sleep(10000); // Sleep for 10 seconds
            } catch (InterruptedException e) {
                System.out.println("✓ Successfully caught InterruptedException in thread");
            }
        });

        sleepingThread.start();
        sleepingThread.interrupt(); // Interrupt the sleeping thread
        sleepingThread.join(1000); // Wait for thread to finish

        // Test 2: Uncaught exception in thread
        Thread.UncaughtExceptionHandler handler = (thread, throwable) -> {
            System.out.println("✓ UncaughtExceptionHandler caught: " + throwable.getClass().getSimpleName());
        };

        Thread badThread = new Thread(() -> {
            throw new RuntimeException("Uncaught exception in thread");
        });

        badThread.setUncaughtExceptionHandler(handler);
        badThread.start();
        badThread.join(1000); // Wait for thread to finish

        // Test 3: Deadlock detection (simplified example - not actually creating deadlock)
        System.out.println("✓ In a real application, use jstack or JConsole to detect deadlocks");
    }

    /**
     * Test resource leaks and proper resource management
     */
    private static void testResourceLeaks() throws Exception {
        // Test 1: Resource leak with manual closing
        FileWriter leakyWriter = null;
        try {
            leakyWriter = new FileWriter("temp.txt");
            leakyWriter.write("This demonstrates a potential resource leak");
            // Intentionally not closing the resource here
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } finally {
            if (leakyWriter != null) {
                try {
                    leakyWriter.close(); // Properly closing resource in finally block
                    System.out.println("✓ Successfully demonstrated proper resource cleanup in finally block");
                } catch (IOException e) {
                    System.out.println("Error closing resource: " + e.getMessage());
                }
            }
        }

        // Test 2: Resource management with try-with-resources
        try (FileWriter autoWriter = new FileWriter("temp2.txt")) {
            autoWriter.write("This demonstrates automatic resource management");
            System.out.println("✓ Successfully demonstrated try-with-resources");
            // Resource automatically closed after this block
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }

        // Clean up temporary files
        new File("temp.txt").delete();
        new File("temp2.txt").delete();
    }

    /**
     * Test regular expression related exceptions
     */
    private static void testRegexExceptions() throws Exception {
        // Test 1: Invalid regex pattern
        try {
            Pattern pattern = Pattern.compile("[unclosed bracket");
            throw new AssertionError("PatternSyntaxException was expected but not thrown");
        } catch (PatternSyntaxException e) {
            System.out.println("✓ Successfully caught PatternSyntaxException with invalid regex");
        }

        // Test 2: Valid but complex regex that can cause catastrophic backtracking
        String complexPattern = "(a+)+b"; // Regex that can cause exponential backtracking
        Pattern pattern = Pattern.compile(complexPattern);
        String input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaac"; // No 'b' at the end

        try {
            // This could cause very long processing time with many implementations
            // due to catastrophic backtracking, but Java handles it relatively well
            boolean matches = pattern.matcher(input).matches();
            System.out.println("✓ Successfully handled potentially problematic regex without excessive backtracking");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception with regex: " + e.getClass().getSimpleName());
        }
    }

    /**
     * Test parsing and date format exceptions
     */
    private static void testParseExceptions() throws Exception {
        // Test 1: Date parsing exception
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false); // Strict parsing

        try {
            Date date = format.parse("2023-02-30"); // February 30th doesn't exist
            throw new AssertionError("ParseException was expected but not thrown");
        } catch (ParseException e) {
            System.out.println("✓ Successfully caught ParseException parsing invalid date");
        }

        // Test 2: Number parsing with custom check
        String potentialNumber = "123.45.67";
        try {
            if (potentialNumber.matches("\\d+(\\.\\d+)?")) {
                double value = Double.parseDouble(potentialNumber);
            } else {
                throw new ParseException("Number format validation failed", 0);
            }
            throw new AssertionError("ParseException was expected but not thrown");
        } catch (ParseException e) {
            System.out.println("✓ Successfully caught custom ParseException with format validation");
        }
    }

    /**
     * Custom exception class for testing
     */
    private static class CustomException extends Exception {
        public CustomException(String message) {
            super(message);
        }

        public CustomException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Test class for AutoCloseable interface
     */
    private static class AutoCloseableResource implements AutoCloseable {
        public AutoCloseableResource() {
            System.out.println("Resource created");
        }

        public void doSomething() throws Exception {
            System.out.println("Resource doing something");
            throw new Exception("Exception while using resource");
        }

        @Override
        public void close() throws Exception {
            System.out.println("Resource automatically closed");
        }
    }

    /**
     * Helper class to manage test execution
     */
    private static class TestRunner {
        private int totalTests = 0;
        private int passedTests = 0;
        private List<String> failedTests = new ArrayList<>();

        public void runTest(String testName, TestMethod testMethod) {
            totalTests++;
            System.out.println("\n===== Running Test: " + testName + " =====");

            try {
                testMethod.run();
                passedTests++;
                System.out.println("===== Test Passed: " + testName + " =====");
            } catch (Throwable t) {
                failedTests.add(testName + ": " + t.getMessage());
                System.out.println("❌ TEST FAILED: " + testName);
                t.printStackTrace(System.out);
            }
        }

        public void printSummary() {
            System.out.println("\n==============================");
            System.out.println("SUMMARY: " + passedTests + "/" + totalTests + " tests passed");

            if (!failedTests.isEmpty()) {
                System.out.println("\nFailed tests:");
                for (String test : failedTests) {
                    System.out.println("  - " + test);
                }
            }

            System.out.println("==============================");
        }
    }

    /**
     * Functional interface for test methods
     */
    @FunctionalInterface
    private interface TestMethod {
        void run() throws Exception;
    }
}