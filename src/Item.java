// Representa um produto no estoque
public class Item {
    private final double weight;
    private final double value;
    private final String name;  // Nome do produto

    public Item(String name, double weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s (Peso: %.2f, Valor: %.2f)", name, weight, value);
    }
}
