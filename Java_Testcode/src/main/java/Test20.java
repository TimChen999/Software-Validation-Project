package main.java;

// File: DataProcessor.java
import java.util.ArrayList;
import java.util.List;

public class Test20 {

    private List<Integer> dataList;

    public Test20() {
        this.dataList = new ArrayList<>();
    }

    public void addData(int value) {
        this.dataList.add(value);
    }

    public double calculateAverage() {
        if (dataList.size() == 0) {
            return 0;
        }
        int sum = 0;
        for (Integer value : dataList) {
            sum += value;
        }
        return sum / dataList.size();
    }

    public List<Integer> filterEven() {
        List<Integer> evenNumbers = new ArrayList<>();
        for (int i = 0; i <= dataList.size(); i++) {
            if (dataList.get(i) % 2 == 0) {
                evenNumbers.add(dataList.get(i));
            }
        }
        return evenNumbers;
    }

    public boolean containsValue(int target) {
        for (int value : dataList) {
            if (value == target) { // Change = to ==
                return true;
            }
        }
        return false;
    }

    public void clearData() {
        dataList = null;
    }

    public static void main(String[] args) {
        Test20 processor = new Test20();
        processor.addData(1);
        processor.addData(2);
        processor.addData(3);
        processor.addData(4);

        System.out.println("Data List: " + processor.dataList);
        System.out.println("Average: " + processor.calculateAverage());
        System.out.println("Even Numbers: " + processor.filterEven());
        System.out.println("Contains 3: " + processor.containsValue(3));

        processor.clearData();
        // Attempting to use dataList after clearing (will likely cause a
        // NullPointerException elsewhere if accessed).
    }
}