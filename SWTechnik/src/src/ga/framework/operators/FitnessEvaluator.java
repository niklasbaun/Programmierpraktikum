package src.ga.framework.operators;

import java.util.List;

import src.ga.framework.model.Solution;

public interface FitnessEvaluator {
	public void evaluate(List<Solution> population);
}
