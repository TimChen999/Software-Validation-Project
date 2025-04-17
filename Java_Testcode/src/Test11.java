import java.util.*;

public class ExposedField {
    private final List<String> items;

    public ExposedField() {
        this.items = new ArrayList<>();
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public static void main(String[] args) {
        ExposedField ef = new ExposedField();
        ef.addItem("apple");
        ef.getItems().clear();  // External code mutating internal list
        System.out.println("Size: " + ef.getItems().size());
    }
}