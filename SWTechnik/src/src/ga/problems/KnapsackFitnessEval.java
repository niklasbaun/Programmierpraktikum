package src.ga.problems;

import src.ga.framework.model.Solution;

import java.util.List;

public class KnapsackFitnessEval implements src.ga.framework.operators.FitnessEvaluator {
    /**
     * method to calculate the fitness of a whole population
     * using streams and lambda expressions
     * @param population the population to calculate the fitness
     */
    @Override
    public void evaluate(List<Solution> population) {
        population.stream().forEach(s -> {
                KnapsackSolution kS = (KnapsackSolution) s;
                int value = kS.content.stream().map(Item::getValue).reduce(0, Integer::sum);
                kS.setFitness(value);
                });
    }

}
