package de.umr.ds.task3;

import java.util.Collections;

public class Training {

	private static final double alpha = 0.05; // learning rate
	private static final int epochs = 100; // number of epochs

	/**
	 * A perceptron is trained on a dataset. After each epoch the perceptron's
	 * parameters are updated, the dataset is shuffled and the accuracy is computed.
	 * 
	 * @param perceptron the perceptron to train
	 * @param dataset the training dataset
	 */
	private static void train(Perceptron perceptron, Dataset dataset) {
		//save the best accuracy and the best perceptron to show at the end
		double bestAccuracy = 0;
		Perceptron bestPerceptron = perceptron;

		//visualize the perceptron
		Visualization vis = new Visualization(dataset, perceptron.weight, perceptron.bias);
		//evaluation object
		Evaluation eval = new Evaluation();
		//perceptron wanted
		int wanted;
		//percepton prediction
		int prediction;

		//difference vector
		Vector dVector;
		//diffference Bias
		double dBias;
		//new weight vector
		Vector newWeight;

		//iterate over epochs
		for(int i = 0; i < epochs; i++){
			//iterate over dataset
			for(DataPoint dataPoint : dataset){
				//find prediction and actual
				wanted = dataset.get(dataPoint.getLabel()).getLabel();
				prediction = perceptron.predict(dataPoint);
				//if prediction is 1 and actual is 0
				//set shift in weight
				dVector = dataPoint.mult(alpha * (wanted -prediction));
				//set new weight
				newWeight = perceptron.weight.add(dVector);
				perceptron.weight = newWeight;

				//set shift in bias
				dBias = alpha * (wanted - prediction);
				//set new bias
				perceptron.bias += dBias;

				//check if this is the best perceptron and accuracy
				if(bestAccuracy < eval.accuracy(perceptron, dataset)){
					bestAccuracy = eval.accuracy(perceptron, dataset);
					bestPerceptron = perceptron;
				}
			}
			//visualize the perceptron
			vis.update(perceptron.weight, perceptron.bias, epochs);
			//print accuracy
			System.out.println("Accuracy: " + eval.accuracy(perceptron, dataset));
			//shuffle dataset
			Collections.shuffle(dataset);
		}
		//print best accuracy
		System.out.println("Best Accuracy: " + bestAccuracy);
	}

	public static void main(String[] args) {

		Dataset dataset = new Dataset(1000);
		Perceptron perceptron = new Perceptron();
		train(perceptron, dataset);
		//visualize the perceptron
	}

}
