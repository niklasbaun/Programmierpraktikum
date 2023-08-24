import src.ga.framework.model.NoSolutionException;
import src.ga.framework.model.Problem;
import src.ga.framework.model.Solution;
import src.ga.framework.operators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    //the following are set by the user inputs
    //problem
    private Problem problem;

    //population size
    private int populationSize;

    //list of evolutionary operators
    private List<EvolutionaryOperator> evolutionaryOperators;

    //fitness eval
    private FitnessEvaluator fitnessEval;

    //survivor selection
    private SurvivalOperator survivorOperator;
    private SelectionOperator selectionOperator;

    private int terminationCondition;

    //constructor
    private GeneticAlgorithm (Problem problem, int populationSize, List<EvolutionaryOperator> evolutionaryOperators, FitnessEvaluator fitnessEval, SurvivalOperator survivorOperator, SelectionOperator selectionOperator, int terminationCondition) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.evolutionaryOperators = evolutionaryOperators;
        this.fitnessEval = fitnessEval;
        this.survivorOperator = survivorOperator;
        this.selectionOperator = selectionOperator;
        this.terminationCondition = terminationCondition;
    }
    public GeneticAlgorithm() {
        this.problem = null;
        this.populationSize = 0;
        this.evolutionaryOperators = null;
        this.fitnessEval = null;
        this.survivorOperator = null;
        this.selectionOperator = null;
        this.terminationCondition = 0;
    }

    //setter
    public void setProblem(Problem problem) {
        this.problem = problem;
    }
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }
    public void setEvolutionaryOperators(List<EvolutionaryOperator> evolutionaryOperators) {
        this.evolutionaryOperators = evolutionaryOperators;
    }
    public void setFitnessEval(FitnessEvaluator fitnessEval) {
        this.fitnessEval = fitnessEval;
    }
    public void setSurvivorOperator(SurvivalOperator survivorOperator) {
        this.survivorOperator = survivorOperator;
    }
    public void setSelectionOperator(SelectionOperator selectionOperator) {
        this.selectionOperator = selectionOperator;
    }
    public void setTerminationCondition(int terminationCondition) {
        this.terminationCondition = terminationCondition;
    }

    //getter
    public Problem getProblem() {
        return problem;
    }
    public int getPopulationSize() {
        return populationSize;
    }
    public List<EvolutionaryOperator> getEvolutionaryOperators() {
        return evolutionaryOperators;
    }
    public FitnessEvaluator getFitnessEval() {
        return fitnessEval;
    }
    public SurvivalOperator getSurvivorOperator() {
        return survivorOperator;
    }
    public SelectionOperator getSelectionOperator() {
        return selectionOperator;
    }
    public int getTerminationCondition() {
        return terminationCondition;
    }

    /**
     * method runOptimization() to start the optimization process of the genetic algorithm
     * @param problem the problem to be solved
     * @param populationSize the size of the population
     * @param evolutionaryOperators the list of evolutionary operators
     * @param fitnessEval the fitness evaluator
     * @param survivorOperator the survivor selection operator
     * @param terminationCondition the termination condition
     * @return the best solution found
     */
    public Solution runOptimization(Problem problem, int populationSize, List<EvolutionaryOperator> evolutionaryOperators, FitnessEvaluator fitnessEval, SurvivalOperator survivorOperator, int terminationCondition) throws EvolutionException, SurvivalException, NoSolutionException {
        //create number of solutions = population size
        //list to save the solutions
        List<Solution> solutions = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            try {
                Solution solution = problem.createNewSolution();
                solutions.add(solution);
            } catch (NoSolutionException e) {
                e.printStackTrace();
            }
        }


        //evaluate fitness
        fitnessEval.evaluate(solutions);
        //while termination condition isn't met
        while (terminationCondition != 0) {
            //apply evolutionary operators (offsprings) to the best solutions
                //use evolutionary operators to evolve the solutions
                List<Solution> offspring = new ArrayList<>();
                //do as many times as the population size
                for (int i = 0; i < populationSize; i++) {

                    //choose random evolutionary operator
                    Random r = new Random();
                    int max = evolutionaryOperators.size();
                    int j = r.nextInt(max);
                    EvolutionaryOperator evolutionaryOperator = evolutionaryOperators.get(j);
                    try {
                        TournamentSelection select = new TournamentSelection();
                        offspring.add(evolutionaryOperator.evolve(select.selectParent(solutions)));
                    } catch (EvolutionException e) {
                        e.printStackTrace();
                    }
                }
                //evaluate fitness
                fitnessEval.evaluate(offspring);
                //add the offspring to the solutions
                solutions.addAll(offspring);

            //select survivors
            try {
               //use TopKSurvival to select the best solutions
                solutions = survivorOperator.selectPopulation(solutions, populationSize);

            } catch (Exception e) {
                e.printStackTrace();
            }
            terminationCondition--;
        }
        //return the best solution
        //evaluate fitness
        fitnessEval.evaluate(solutions);
        //sort the solutions select with best fitness
        for (int i = 0; i < solutions.size(); i++) {
            for (int j = 0; j < solutions.size(); j++) {
                if (solutions.get(i).getFitness() > solutions.get(j).getFitness()) {
                    Solution temp = solutions.get(i);
                    solutions.set(i, solutions.get(j));
                    solutions.set(j, temp);
                }
            }
        }
        //return the best solution using selectPopulation
        return solutions.get(0);
    }

    //---------------------------------------------------------------------------

    /**
     * method to start the fluent interface chain
     * @return list of possible solutions
     */
    public Solve solve(Problem problem){
        //method to start the chain
        //return list of possible solutions
        problem = problem;
        return new Solve();
    }

    class Solve {
        //method to set the population size
        //return list of possible solutions
        WithPopSizeOf withPopSizeOf(int populationSize){
            populationSize = populationSize;
            return new WithPopSizeOf();
        }

    }

    class WithPopSizeOf {
        //method to add an evolutionary operator
        WithPopSizeOf withEvolutionaryOP(EvolutionaryOperator evoOP){
            evolutionaryOperators.add(evoOP);
            return new WithPopSizeOf();
        }

        //method to finish the loop
        WithEvolutionaryOP withEvolutionaryOP2(EvolutionaryOperator evoOP){
            evolutionaryOperators.add(evoOP);
            return new WithEvolutionaryOP();
        }
    }

    class WithEvolutionaryOP {
        EvaluatingSolutionsBy evaluatingSolutionsBy(FitnessEvaluator fitnessEvaluator){
            fitnessEval = fitnessEvaluator;
            return new EvaluatingSolutionsBy();
        }
    }

    class EvaluatingSolutionsBy {

        StoppingAtEvolution stoppingAtEvolution(int terminationCondition){
            terminationCondition = terminationCondition;
            return new StoppingAtEvolution();
        }

    }

    class StoppingAtEvolution {

        RunOptimazation runOptimazation(){
            //method to start the optimization process
            return new RunOptimazation();
        }
    }

    class RunOptimazation {
        //method to start the optimization process
        /**
         * method runOptimization() to start the optimization process of the genetic algorithm
         * @param problem the problem to be solved
         * @param populationSize the size of the population
         * @param evolutionaryOperators the list of evolutionary operators
         * @param fitnessEval the fitness evaluator
         * @param survivorOperator the survivor selection operator
         * @param terminationCondition the termination condition
         * @return the best solution found
         */
        public Solution runOptimization(Problem problem, int populationSize, List<EvolutionaryOperator> evolutionaryOperators, FitnessEvaluator fitnessEval, SurvivalOperator survivorOperator, int terminationCondition) throws EvolutionException, SurvivalException, NoSolutionException {
            //create number of solutions = population size
            //list to save the solutions
            List<Solution> solutions = new ArrayList<>(populationSize);
            for (int i = 0; i < populationSize; i++) {
                try {
                    Solution solution = problem.createNewSolution();
                    solutions.add(solution);
                } catch (NoSolutionException e) {
                    e.printStackTrace();
                }
            }


            //evaluate fitness
            fitnessEval.evaluate(solutions);
            //while termination condition isn't met
            while (terminationCondition != 0) {
                //apply evolutionary operators (offsprings) to the best solutions
                //use evolutionary operators to evolve the solutions
                List<Solution> offspring = new ArrayList<>();
                //do as many times as the population size
                for (int i = 0; i < populationSize; i++) {

                    //choose random evolutionary operator
                    Random r = new Random();
                    int max = evolutionaryOperators.size();
                    int j = r.nextInt(max);
                    EvolutionaryOperator evolutionaryOperator = evolutionaryOperators.get(j);
                    try {
                        TournamentSelection select = new TournamentSelection();
                        offspring.add(evolutionaryOperator.evolve(select.selectParent(solutions)));
                    } catch (EvolutionException e) {
                        e.printStackTrace();
                    }
                }
                //evaluate fitness
                fitnessEval.evaluate(offspring);
                //add the offspring to the solutions
                solutions.addAll(offspring);

                //select survivors
                try {
                    //use TopKSurvival to select the best solutions
                    solutions = survivorOperator.selectPopulation(solutions, populationSize);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                terminationCondition--;
            }
            //return the best solution
            //evaluate fitness
            fitnessEval.evaluate(solutions);
            //sort the solutions select with best fitness
            for (int i = 0; i < solutions.size(); i++) {
                for (int j = 0; j < solutions.size(); j++) {
                    if (solutions.get(i).getFitness() > solutions.get(j).getFitness()) {
                        Solution temp = solutions.get(i);
                        solutions.set(i, solutions.get(j));
                        solutions.set(j, temp);
                    }
                }
            }
            //return the best solution using selectPopulation
            return solutions.get(0);
        }
    }
}

