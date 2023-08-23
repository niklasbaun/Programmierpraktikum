import src.ga.framework.model.NoSolutionException;
import src.ga.framework.model.Problem;
import src.ga.framework.model.Solution;
import src.ga.framework.operators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements Problem, EvolutionaryOperator, FitnessEvaluator, SurvivalOperator, SelectionOperator {
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
        //while termination condition not met
        while (terminationCondition != 0) {
            //apply evolutionary operators (offsprings) to best solutions
                //use evolutionary operators to evolve the solutions
                List<Solution> offspring = new ArrayList<>();
                for (EvolutionaryOperator evolutionaryOperator : evolutionaryOperators) {
                    try {
                        offspring.add(evolutionaryOperator.evolve(selectParent(solutions)));
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
                solutions = survivorOperator.selectPopulation(solutions, populationSize);
            } catch (SurvivalException e) {
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

        /**
         * method createNewSolution() to create a new solution for the problem
         * @return the new solution
         */
        @Override
        public Solution createNewSolution() throws NoSolutionException {
            //create a new solution for the problem
            //return the new solution
            try {
                return problem.createNewSolution();
            } catch (NoSolutionException e) {
                System.out.println("No could be created");
            }
            return null;
        }

    /**
     * method to evolve a solution
     * @param solution the solution to evolve
     * @return the evolved solution
     * @throws EvolutionException
     */
    @Override
        public Solution evolve(Solution solution) throws EvolutionException {
            //evolve the solution
            //return the evolved solution
            Random r = new Random();
            int max =  evolutionaryOperators.size();
            int i = r.nextInt(max);
            try {
                return evolutionaryOperators.get(i).evolve(solution);
            } catch (EvolutionException e) {
                System.out.println("Evolutionary Operator not found");
            }
            return null;
        }

        /**
         * method to evaluate the fitness of a population and set the fitness of each solution
         * @param population the population to evaluate
         */
        @Override
        public void evaluate(List<Solution> population) {
            //evaluate the fitness of each solution in the population
            fitnessEval.evaluate(population);
        }



        /**
         * method to select a population from a list of candidates
         * @param candidates the candidates to select from
         * @param populationSize the size of the population to select
         * @return the selected population dedicated to survive
         * @throws SurvivalException
         */
        @Override
        public List<Solution> selectPopulation(List<Solution> candidates, int populationSize) throws SurvivalException {
            //select the population dedicated to survive
            //chosen by the fitness of the solutions in the population
            //choose the top solutions maximum population size

            //sort the solutions select with best fitness
            for (int i = 0; i < candidates.size(); i++) {
                for (int j = 0; j < candidates.size(); j++) {
                    if (candidates.get(i).getFitness() > candidates.get(j).getFitness()) {
                        Solution temp = candidates.get(i);
                        candidates.set(i, candidates.get(j));
                        candidates.set(j, temp);
                    }
                }
            }
            //return the best solutions
            try {
                return candidates.subList(0, populationSize);
            } catch (IndexOutOfBoundsException e) {
                //if the population size is bigger than the candidates size
                //return the candidates
                System.out.println("Population size is bigger than the candidates size");
                return candidates;
            }
        }
}

