import src.ga.framework.model.Solution;
import src.ga.framework.operators.SurvivalException;
import src.ga.framework.operators.SurvivalOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TopKSurvival implements SurvivalOperator {

    int k;

    TopKSurvival(int k) {
        this.k = k;
    }

    /**
     * Selects the best k solutions from the candidates.
     * @param candidates List of solutions to select from.
     * @param populationSize The size of the population.
     * @return List of the best k solutions.
     * @throws SurvivalException
     */
    @Override
    public List<Solution> selectPopulation(List<Solution> candidates, int populationSize) throws SurvivalException {
        //select the population dedicated to survive
        List<Solution> newPop = new ArrayList<>(populationSize);
        //sort the solutions select with best fitness
        for (int i = 0; i < candidates.size(); i++) {
            for (int j = 0; j < candidates.size() - 1; j++) {
                if (candidates.get(j).getFitness() < candidates.get(j + 1).getFitness()) {
                    Solution temp = candidates.get(j);
                    candidates.set(j, candidates.get(j + 1));
                    candidates.set(j + 1, temp);
                }
            }
        }
        //return the best k solutions
        try {
            newPop = candidates.subList(0, k);
        } catch (IndexOutOfBoundsException e) {
            //if the population size is bigger than the candidates size
            //return the candidates
            System.out.println("Population size is bigger than the candidates size");
            return candidates;
        }
        //if k is smaller than the population size, fill the rest with random solutions
        //get random num
        Random r = new Random();
        int max = candidates.size();
        int i = r.nextInt(max);
        //fill
        while (newPop.size() < populationSize) {
            newPop.add(candidates.get(i));
            i = r.nextInt(max);
        }
        return newPop;
    }
}
