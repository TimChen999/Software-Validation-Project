public class Main {
    //Test functions and class
    static class Nested{
        private static String name;
        public static void printName(){
            System.out.println(name);
            System.out.println("Length " + name.length());
        }
    }
    static Nested nestedClass;
    static int[] list = new int[10];

    public static void main(String[] args) {
        //Invalid value test (nested is null)
        nestedClass.printName();

        //Out of bounds error
        list[10] = 10;

        //Integer overflow
        int maxInt = Integer.MAX_VALUE;
        maxInt++;
        System.out.println("Overflow " + maxInt);

        //Infinite loop
        for(int i = 0; i < 5;){
            System.out.println("i = " + i);
        }

        //Unhandled exception
        throw new RuntimeException("Unhandled exception");
    }
}