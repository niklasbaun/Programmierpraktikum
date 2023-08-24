package src.ga.problems;

import src.ga.framework.model.NoSolutionException;
import src.ga.framework.model.Solution;
import src.ga.framework.operators.EvolutionException;
import src.ga.framework.GeneticAlgorithm;

public class ConcreteProblem extends KnapsackProblem {

    private ConcreteProblem(int weightLimit) {
        super(weightLimit);
    }


    public static void main(String[] args) throws NoSolutionException, EvolutionException {
        //create a problem
        ConcreteProblem problem = new ConcreteProblem(11);
        //add items to the problem
        problem.addItem(Item.createItem("good internet connection in germany",5, 10));
        problem.addItem(Item.createItem("shout of a fish",4, 8));
        problem.addItem(Item.createItem("step of a cat",4, 6));
        problem.addItem(Item.createItem("donut", 4, 4));
        problem.addItem(Item.createItem("source of immortality",3, 7));
        problem.addItem(Item.createItem("root of a mountain",3, 4));
        problem.addItem(Item.createItem("fountain of youth", 2, 6));
        problem.addItem(Item.createItem("end of the world", 2, 3));
        problem.addItem(Item.createItem("siva nanites",1, 3));
        problem.addItem(Item.createItem("world peace", 1, 1));
        //create instance of Mutation
        KnapsackMutation mutation = new KnapsackMutation();
        //create instance of evaluation
        KnapsackFitnessEval eval = new KnapsackFitnessEval();

        //create list of Solutions
        Solution solution;

        //create a solution using GeneticAlgorithm
        GeneticAlgorithm ga = new GeneticAlgorithm();
        solution = ga.solve(problem).withPopSizeOf(4).withEvolutionaryOP2(mutation).evaluatingSolutionsBy(eval).stoppingAtEvolution(10).runOptimization();


        //print out the solution
        System.out.println(solution);
        System.out.println(solution.getFitness());
        System.out.println(solution.getProblem());
        KnapsackSolution kS = (KnapsackSolution) solution;
        for (int i = 0; i < kS.content.size(); i++) {
            System.out.println("--------------------");
            System.out.println(kS.content.get(i).getDesc());
            System.out.println(kS.content.get(i).getWeight());
            System.out.println(kS.content.get(i).getValue());
        }
    }
}
