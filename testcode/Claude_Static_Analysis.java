package bugbench;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//This was made using claude, prompt was telling claude to provide a suite of cases that can effectively test static analysis algorithms

/**
 * A collection of common Java bugs that static analyzers should detect.
 * Each method contains one or more bugs of a specific type.
 */
public class BugCollection {

    // ---------- NULL POINTER DEREFERENCE ----------
    public void nullDereference(String input) {
        String value = null;
        if (input.equals("special")) {
            value = "not null";
        }
        // NPE if input is not "special"
        System.out.println(value.length());
    }

    public String conditionalNullDereference(boolean condition) {
        String result = null;
        if (condition) {
            result = "initialized";
        }
        // NPE if condition is false
        return result.trim();
    }

    // ---------- RESOURCE LEAKS ----------
    public void resourceLeak() throws IOException {
        FileInputStream fis = new FileInputStream("test.txt");
        byte[] data = new byte[1024];
        fis.read(data);
        // Missing fis.close()
    }

    public void partialResourceLeak() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("file.txt"));
            String line = reader.readLine();
            if (line == null) {
                return; // Reader not closed in this path
            }
            reader.close();
        } catch (IOException e) {
            // Reader not closed on exception
            throw e;
        }
    }

    // ---------- CONCURRENCY BUGS ----------
    private int counter = 0;
    // Missing synchronized keyword
    public void increment() {
        counter++;
    }

    // Race condition on non-atomic operation
    private long total = 0;
    public void addToTotal(int value) {
        total += value; // Non-atomic operation
    }

    // Deadlock potential
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void deadlockMethod1() {
        synchronized(lock1) {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            synchronized(lock2) {
                System.out.println("Method 1");
            }
        }
    }

    public void deadlockMethod2() {
        synchronized(lock2) {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            synchronized(lock1) {
                System.out.println("Method 2");
            }
        }
    }

    // ---------- ARRAY INDEX BUGS ----------
    public int arrayBoundsBug(int[] array) {
        int sum = 0;
        // Off-by-one error, should be < array.length
        for (int i = 0; i <= array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public void negativeArrayIndex(int position) {
        int[] data = new int[10];
        // Potential negative index
        data[position] = 100;
    }

    // ---------- INTEGER BUGS ----------
    public int integerOverflow(int a, int b) {
        // Can overflow for large positive values
        return a + b;
    }

    public long integerUnderflow(int a, int b) {
        // Can underflow if a < b
        return a - b;
    }

    public int divisionByZero(int a, int b) {
        // Potential division by zero
        return a / b;
    }

    // ---------- SECURITY VULNERABILITIES ----------
    // SQL Injection
    public ResultSet sqlInjection(String userInput, Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        // Vulnerable to SQL injection
        return stmt.executeQuery("SELECT * FROM users WHERE name = '" + userInput + "'");
    }

    // Weak cryptography
    public byte[] weakCrypto(String password, byte[] data) throws Exception {
        // Insecure - uses DES (weak) and hardcoded key
        byte[] key = password.getBytes();
        SecretKeySpec spec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        return cipher.doFinal(data);
    }

    // Hardcoded credentials
    public void hardcodedPassword() {
        String username = "admin";
        String password = "Password123!"; // Hardcoded credential
        authenticateUser(username, password);
    }

    private void authenticateUser(String username, String password) {
        // Authentication logic
    }

    // Path traversal
    public File insecureFileAccess(String fileName) {
        // Vulnerable to path traversal
        return new File("/safe/directory/" + fileName);
    }

    // ---------- MEMORY LEAKS ----------
    private static final Map<String, Object> leakyMap = new HashMap<>();
    
    public void memoryLeak(String key, Object value) {
        // Objects continuously added but never removed
        leakyMap.put(key, value);
    }

    // ---------- UNCHECKED TYPE CASTS ----------
    @SuppressWarnings("unchecked")
    public List<String> unsafeCast(Object obj) {
        // Unchecked cast
        return (List<String>) obj;
    }

    // ---------- INFINITE LOOPS ----------
    public void infiniteLoop() {
        int x = 10;
        // Condition never changes
        while (x > 5) {
            System.out.println("Looping");
        }
    }

    // ---------- UNREACHABLE CODE ----------
    public void unreachableCode(int value) {
        if (value > 0) {
            return;
        } else {
            throw new IllegalArgumentException("Value must be positive");
        }
        // This line is unreachable
        System.out.println("This will never execute");
    }

    // ---------- SERIALIZATION BUGS ----------
    public static class SerializableBug implements Serializable {
        private static final long serialVersionUID = 1L;
        private transient int counter;
        // Non-serializable field in serializable class
        private Thread thread;
    }

    // ---------- PERFORMANCE ISSUES ----------
    public String inefficientStringConcatenation(List<String> items) {
        String result = "";
        for (String item : items) {
            // Inefficient string concatenation
            result = result + item + ", ";
        }
        return result;
    }

    public boolean inefficientCollectionIteration(List<String> list, String target) {
        // O(n²) complexity when it could be O(n)
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) {
                return true;
            }
        }
        return false;
    }

    // ---------- THREAD SAFETY ISSUES ----------
    private final Map<String, Integer> sharedMap = new HashMap<>();
    
    public void threadUnsafeMap(String key, int value) {
        // HashMap is not thread-safe
        sharedMap.put(key, value);
    }

    private final Lock lock = new ReentrantLock();
    
    public void unlockWithoutTry() {
        lock.lock();
        // Missing try-finally to ensure unlock
        doSomething();
        lock.unlock();
    }
    
    private void doSomething() {
        // Could throw an exception
        if (Math.random() < 0.5) {
            throw new RuntimeException("Random failure");
        }
    }

    // ---------- EQUALS/HASHCODE CONTRACT ----------
    public static class EqualsHashCodeViolation {
        private int id;
        private String name;
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof EqualsHashCodeViolation)) return false;
            EqualsHashCodeViolation other = (EqualsHashCodeViolation) obj;
            return this.id == other.id && Objects.equals(this.name, other.name);
        }
        
        // Missing matching hashCode() implementation
    }

    // ---------- UNCLOSED CLOSEABLE RESOURCES ----------
    public List<String> readFileLines(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
        // Missing reader.close()
    }

    // ---------- EMPTY EXCEPTION HANDLERS ----------
    public void emptyExceptionHandler() {
        try {
            File file = new File("nonexistent.txt");
            FileInputStream fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // Empty catch block swallows exception
        }
    }

    // ---------- UNUSED VARIABLES ----------
    public void unusedVariables(int input) {
        int unused = input * 10; // Unused variable
        System.out.println("Processing: " + input);
    }

    // ---------- INTEGER DIVISION TRUNCATION ----------
    public double calculateAverage(int total, int count) {
        // Integer division will truncate
        return total / count;
    }

    // ---------- UNINITIALIZED VARIABLES ----------
    public int uninitializedVariable() {
        int result;
        // result may not be initialized on all paths
        if (Math.random() > 0.5) {
            result = 42;
        }
        return result; // May be uninitialized
    }
}

// Additional class to demonstrate inheritance bugs
class InheritanceBugs extends BugCollection {
    // Overridden method with different synchronization
    @Override
    public void increment() {
        // Parent method not synchronized, but this one is
        synchronized(this) {
            super.increment();
        }
    }
    
    // Broken clone implementation
    @Override
    public Object clone() throws CloneNotSupportedException {
        // Does not call super.clone()
        return new InheritanceBugs();
    }
}