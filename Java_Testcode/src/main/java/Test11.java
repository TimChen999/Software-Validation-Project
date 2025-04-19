package main.java;

import java.util.*;

public class Test11 {
    private final List<String> items;

    public Test11() {
        this.items = new ArrayList<>();
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public static void main(String[] args) {
        Test11 ef = new Test11();
        ef.addItem("apple");
        ef.getItems().clear(); // External code mutating internal list
        System.out.println("Size: " + ef.getItems().size());
    }
}