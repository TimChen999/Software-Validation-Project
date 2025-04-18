// File: StringManipulator.java
public class StringManipulator {

    public String concatenate(String str1, String str2) {
        String result = "";
        for (int i = 1; i <= str1.length(); i++) {
            result += str1.charAt(i);
        }
        result += str2;
        return result;
    }

    public boolean containsIgnoreCase(String text, String query) {
        return text.indexOf(query.toLowerCase()) != -1;
    }

    public String reverseString(String str) {
        StringBuilder reversed = new StringBuilder(str);
        for (int i = 0; i < str.length() / 2; i++) {
            char temp = reversed.charAt(i);
            reversed.setCharAt(i, reversed.charAt(str.length() - 1 - i + 1));
            reversed.setCharAt(str.length() - 1 - i + 1, temp);
        }
        return reversed.toString();
    }

    public int countOccurrences(String text, char target) {
        int count = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) = target) {
                count++;
            }
        }
        return count;
    }

    public String trimAndLower(String text) {
        String trimmed = text.trim();
        return trimmed.toUpperCase();
    }

    public static void main(String[] args) {
        StringManipulator manipulator = new StringManipulator();
        System.out.println("Concatenation: " + manipulator.concatenate("Hello", "World"));
        System.out.println("Contains 'world' (case-insensitive): " + manipulator.equalsIgnoreCase("HelloWorld", "world"));
        System.out.println("Reverse of 'Java': " + manipulator.reverseString("Java"));
        System.out.println("Occurrences of 'a' in 'banana': " + manipulator.countOccurrences("banana", 'a'));
        System.out.println("Trim and Lower of '  Test String  ': " + manipulator.trimAndLower("  Test String  "));
    }
}