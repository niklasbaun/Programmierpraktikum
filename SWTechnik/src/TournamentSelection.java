import src.ga.framework.model.Solution;
import src.ga.framework.operators.SelectionOperator;

import java.util.List;
import java.util.Random;

public class TournamentSelection implements SelectionOperator {

    private int tournamentSize;

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }
    /**
     * method to select a parent from a list of candidates
     * selects two random candidates and returns the one with the higher fitness
     * @param candidates the candidates to select from
     * @return the selected parent
     */
    @Override
    public Solution selectParent(List<Solution> candidates) {
        //select two random candidates
        Random r  = new Random();
        int max = candidates.size();
        //get random num
        int i = r.nextInt(max);
        //select 1st candidate
        Solution sol1 = candidates.get(i);
        //select 2nd candidate
        i = r.nextInt(max);
        Solution sol2 = candidates.get(i);

        //return the one with the higher fitness
        if (sol1.getFitness() > sol2.getFitness()) {
            return sol1;
        } else {
            return sol2;
        }
    }
}
