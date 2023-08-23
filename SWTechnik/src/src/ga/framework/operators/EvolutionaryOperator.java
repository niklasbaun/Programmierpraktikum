package src.ga.framework.operators;

import src.ga.framework.model.Solution;

public interface EvolutionaryOperator {
	public Solution evolve(Solution solution) 
			throws EvolutionException;
}
