import java.util.List;

public class KnapsackFitnessEval {

    //calculate the fitness of a whole population

    /**
     * method to calculate the fitness of a whole population
     * using streams and lambda expressions
     * @param population the population to calculate the fitness
     */
    public void evaluate(List<Solution> population) {
        population.stream().forEach(s -> {
            if(s instanceof KnapsackSolution){
                KnapsackSolution knapsackSolution = (KnapsackSolution) s;
                int value = knapsackSolution.content().stream().map(item -> item.getValue()).reduce((item, item2) -> {
                    item + item2;
                    return item;
                });
                knapsackSolution.setFitness(value);
            }

                });
    }

}
