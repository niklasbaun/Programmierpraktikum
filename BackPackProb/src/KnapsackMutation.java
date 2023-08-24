import src.ga.framework.model.Solution;
import src.ga.framework.operators.EvolutionException;
import src.ga.framework.operators.EvolutionaryOperator;

import java.util.Random;

public class KnapsackMutation implements EvolutionaryOperator {

    /**
     * inner class for remove mutation
     */
    static class removeMutation {
        KnapsackSolution s;
        int r;

        public removeMutation(KnapsackSolution solution, int r) {
            this.s = solution;
            this.r = r;
        }

        /**
         * method to remove an item from a solution
         */
        public KnapsackSolution removeItem() {
            //remove item from a solution
            s.content.remove(r);
            return s;
        }
    }

    /**
     * inner class for add mutation
     */
    static class addMutation {
        KnapsackSolution s;
        int r;

        public addMutation(KnapsackSolution solution, int r) {
            this.s = solution;
            this.r = r;
        }
        /**
         * method to add an item to a solution
         */
        public KnapsackSolution addItem() {
            //add item to a solution
            s.content.add(s.getKnapsackProblem().getItemFromPos(r));
            return s;
        }
    }
        /**
         * method to evolve a solution
         * by either adding an item or removing an item
         *
         * @param solution the solution to be evolved
         * @return the evolved solution
         * @throws EvolutionException
         */
        @Override
        public Solution evolve(Solution solution) throws EvolutionException {
            //create copy of a solution
            KnapsackSolution newSolution = new KnapsackSolution(solution);
            //extract problem
            KnapsackProblem problem = newSolution.getKnapsackProblem();
            //create random Generator
            Random random = new Random();
            //check if a solution has items
            if (newSolution.content.isEmpty()) {
                //only add random item
                int randomInt = random.nextInt(problem.getItemList().size());
                newSolution.content.add(problem.getItemFromPos(randomInt));
            } else if (newSolution.content.size() > 1) {
                //check if item should be added or removed
                if (random.nextBoolean()) {
                    //add random item
                    int randomInt = random.nextInt(problem.getItemList().size());
                    newSolution = new addMutation(newSolution, randomInt).addItem();
                } else {
                    //remove random item
                    int randomInt = random.nextInt(newSolution.content.size());
                    newSolution = new removeMutation(newSolution, randomInt).removeItem();
                }
            } else {
                //if nothing was possible throw exception
                throw new EvolutionException("No possible mutation was found.");
            }
            return newSolution;
        }
}
