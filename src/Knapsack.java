import java.util.ArrayList;
import java.util.List;

// Representa o armazém e seus itens
public class Knapsack {
    private final double capacity;
    private final List<Item> items;
    private final String name;  // Nome do armazém

    public Knapsack(String name, double capacity) {
        this.name = name;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getName() {
        return name;
    }

    public double getCapacity() {
        return capacity;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
}
