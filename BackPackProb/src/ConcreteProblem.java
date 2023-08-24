import src.ga.framework.model.NoSolutionException;
import src.ga.framework.operators.EvolutionException;

import java.util.ArrayList;
import java.util.List;

public class ConcreteProblem extends KnapsackProblem {

    private ConcreteProblem(int weightLimit) {
        super(weightLimit);
    }


    public static void main(String[] args) throws NoSolutionException, EvolutionException {
        //create a problem
        ConcreteProblem problem = new ConcreteProblem(11);
        //add items to the problem
        problem.addItem(Item.createItem(5, 10));
        problem.addItem(Item.createItem(4, 8));
        problem.addItem(Item.createItem(4, 6));
        problem.addItem(Item.createItem(4, 4));
        problem.addItem(Item.createItem(3, 7));
        problem.addItem(Item.createItem(3, 4));
        problem.addItem(Item.createItem(2, 6));
        problem.addItem(Item.createItem(2, 3));
        problem.addItem(Item.createItem(1, 3));
        problem.addItem(Item.createItem(1, 1));
        //create instance of Mutation
        KnapsackMutation mutation = new KnapsackMutation();
        //create instance of evaluation
        KnapsackFitnessEval eval = new KnapsackFitnessEval();

        //create list of Solutions
        List<Solution> solutions = new ArrayList<>();

        //create a solution using GeneticAlgorithm
        GeneticAlgorithm ga = new GeneticAlgorithm();
        solutions = ga.solve(problem).withPopSizeOf(4).withEvolutionaryOP2(mutation).evaluatingSolutionsBy(eval).stoppingAtEvolution(10).runOptimazation();


        //print out the solutions
        for (Solution solution : solutions) {
            System.out.println(solution);
            System.out.println(solution.getProblem());
            System.out.println(solution.getFitness());
        }
    }
}
