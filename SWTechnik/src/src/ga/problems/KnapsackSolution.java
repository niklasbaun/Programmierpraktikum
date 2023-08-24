package src.ga.problems;

import src.ga.framework.model.Problem;
import src.ga.framework.model.Solution;

import java.util.List;

public class KnapsackSolution extends Solution{

    List<Item> content;

    public KnapsackSolution(Problem problem, List<Item> content) {
        super(problem);
        this.content = content;
    }

    public KnapsackSolution(Solution toCopy) {
        super(toCopy);
    }

    public KnapsackProblem getKnapsackProblem() {
        return (KnapsackProblem) super.getProblem();
    }
}
