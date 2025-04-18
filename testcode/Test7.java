import java.io.FileReader;
import java.io.IOException;

public class FaultyResource {
    public static void main(String[] args) {
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
