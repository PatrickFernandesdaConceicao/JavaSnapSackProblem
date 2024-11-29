import java.util.*;

// Solver usando Algoritmo A* para otimização
//public class KnapsackSolver {
//    private static final int MAX_QUEUE_SIZE = 1000;
    private static final int MAX_DEPTH = 50;

    public KnapsackState solve(Knapsack knapsack) {
        double capacity = knapsack.getCapacity();
        List<Item> items = knapsack.getItems();

        // Fila de prioridade para estados
        PriorityQueue<KnapsackState> openStates = new PriorityQueue<>(
                Comparator.comparingDouble((KnapsackState state) -> -calculatePriority(state))
        );

        KnapsackState initialState = new KnapsackState();
        openStates.offer(initialState);
        KnapsackState bestSolution = initialState;

        // Conjunto para rastrear estados visitados
        Set<List<Item>> visitedStates = new HashSet<>();

        while (!openStates.isEmpty() && openStates.size() <= MAX_QUEUE_SIZE) {
            KnapsackState currentState = openStates.poll();

            // Verifica limite de profundidade
            if (currentState.getSelectedItems().size() >= MAX_DEPTH) continue;

            // Atualiza melhor solução
            if (currentState.getTotalValue() > bestSolution.getTotalValue()) {
                bestSolution = currentState;
            }

            // Explora novos estados
            for (Item item : items) {
                // Evita itens já adicionados e verifica capacidade
                if (!currentState.getSelectedItems().contains(item) &&
                        currentState.getTotalWeight() + item.getWeight() <= capacity) {

                    KnapsackState newState = new KnapsackState(currentState.getSelectedItems());
                    newState.addItem(item);

                    // Verifica limite superior e estados únicos
                    double bound = calculateBound(newState, items, capacity);
                    if (bound > bestSolution.getTotalValue() &&
                            !visitedStates.contains(newState.getSelectedItems())) {

                        openStates.offer(newState);
                        visitedStates.add(newState.getSelectedItems());
                    }
                }
            }
        }

        return bestSolution;
    }

    // Calcula prioridade baseada na razão valor/peso
    private double calculatePriority(KnapsackState state) {
        return state.getTotalValue() / (state.getTotalWeight() + 1);
    }

    // Calcula limite superior com estimativa fracionária
    private double calculateBound(KnapsackState state, List<Item> items, double capacity) {
        double remainingCapacity = capacity - state.getTotalWeight();
        double bound = state.getTotalValue();

        // Adiciona valor fracionário de itens restantes
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems.sort((a, b) -> Double.compare(b.getValue() / b.getWeight(),
                a.getValue() / a.getWeight()));

        for (Item item : sortedItems) {
            if (!state.getSelectedItems().contains(item) && remainingCapacity > 0) {
                double fraction = Math.min(item.getWeight(), remainingCapacity) / item.getWeight();
                bound += item.getValue() * fraction;
                remainingCapacity -= item.getWeight();

                if (remainingCapacity <= 0) break;
            }
        }

        return bound;
    }
}
