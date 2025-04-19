package main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test7 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader reader = null;
        reader = new FileReader("file.txt");
        int data = reader.read();
        while (data != -1) {
            System.out.print((char) data);
            data = reader.read();
        }
        reader.close();
    }
}
