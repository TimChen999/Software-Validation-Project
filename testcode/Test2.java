public class Test2 {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};
        for (int i = 0; i <= numbers.length; i++) { // BUG: should be i < numbers.length
            System.out.println("Number: " + numbers[i]); // ArrayIndexOutOfBoundsException
        }
    }
}
