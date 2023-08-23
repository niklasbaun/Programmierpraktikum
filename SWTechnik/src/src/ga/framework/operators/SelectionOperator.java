package src.ga.framework.operators;

import src.ga.framework.model.Solution;

import java.util.List;

public interface SelectionOperator {
    public Solution selectParent(List<Solution> candidates);
}
