public class Test1 {
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
        nestedClass.printName();

        list[10] = 10;

        int maxInt = Integer.MAX_VALUE;
        maxInt++;
        System.out.println("Overflow " + maxInt);

        for(int i = 0; i < 5;){
            System.out.println("i = " + i);
        }

        throw new RuntimeException("Unhandled exception");
    }
}