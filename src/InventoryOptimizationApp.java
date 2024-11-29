import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryOptimizationApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Knapsack warehouse = null;
        List<Item> items = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\n--------- BEM-VINDO ---------");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Cadastrar Armazém");
            System.out.println("3 - Executar Otimização");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir o \n restante

            switch (option) {
                case 1:
                    if (warehouse == null) {
                        System.out.println("Por favor, cadastre o armazém antes de adicionar produtos.");
                        break;
                    }
                    System.out.print("Nome do produto: ");
                    String itemName = scanner.nextLine();

                    System.out.print("Peso do produto (m³): ");
                    double weight = scanner.nextDouble();

                    System.out.print("Valor do produto (R$): ");
                    double value = scanner.nextDouble();
                    scanner.nextLine(); // Consumir o \n restante

                    Item item = new Item(itemName, weight, value);
                    items.add(item);
                    warehouse.addItem(item);
                    System.out.println("Produto cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite o nome do armazém: ");
                    String warehouseName = scanner.nextLine();

                    System.out.print("Digite a capacidade do armazém em metros cúbicos: ");
                    double capacity = scanner.nextDouble();
                    scanner.nextLine(); // Consumir o \n restante

                    warehouse = new Knapsack(warehouseName, capacity);
                    items.clear(); // Limpa itens antigos se houver um novo armazém
                    System.out.println("Armazém cadastrado com sucesso!");
                    break;

                case 3:
                    if (warehouse == null) {
                        System.out.println("Por favor, cadastre o armazém antes de executar a otimização.");
                        break;
                    }
                    if (items.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado. Por favor, adicione produtos antes de executar a otimização.");
                        break;
                    }

                    // Resolver o problema de otimização
                    KnapsackSolver solver = new KnapsackSolver();
                    KnapsackState bestState = solver.solve(warehouse);

                    // Exibir relatório de otimização
                    printOptimizationReport(warehouse, bestState);
                    break;

                case 4:
                    System.out.println("Saindo do sistema. Até a próxima!");
                    running = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void printOptimizationReport(Knapsack warehouse, KnapsackState bestState) {
        System.out.println("\nRelatório de Otimização de Estoque:");
        System.out.println("Armazém: " + warehouse.getName());
        System.out.println("Capacidade Total: " + warehouse.getCapacity() + " m³");

        System.out.println("\nProdutos Selecionados:");
        for (Item item : bestState.getSelectedItems()) {
            System.out.println(item);
        }

        System.out.printf("\nResumo da Otimização:");
        System.out.printf("\nPeso Total Ocupado: %.2f m³%n", bestState.getTotalWeight());
        System.out.printf("Valor Total dos Produtos: R$ %.2f%n", bestState.getTotalValue());
        System.out.printf("Espaço Remanescente: %.2f m³%n",
                warehouse.getCapacity() - bestState.getTotalWeight());
    }
}
