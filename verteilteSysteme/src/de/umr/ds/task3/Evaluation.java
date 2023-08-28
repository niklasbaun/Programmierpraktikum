package de.umr.ds.task3;

public class Evaluation {

	/**
	 * Applies the model to each data vector in the dataset and computes the
	 * accuracy.
	 * 
	 * @return accuracy
	 */
	public static double accuracy(Perceptron model, Dataset dataset) {
		//check if dataset is empty
		if(dataset.size() == 0){
			throw new IllegalArgumentException("Dataset is empty");
		}
		//compute accuracy
		int correct = 0;
		for(DataPoint dataPoint : dataset){
			if(model.predict(dataPoint) == dataPoint.getLabel()){
				correct++;
			}
		}
		return (double) correct / dataset.size();
	}
}
