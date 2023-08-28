package de.umr.ds.task3;

import java.util.Random;

/**
 * A Perceptron holds weights and bias and can be applied to a data vector to
 * predict its class. Weights and bias are initialized randomly.
 */
public class Perceptron {
    Vector weight;
    double bias;

	//Constructor
    public Perceptron() {
        Random r = new Random();
        this.weight = new Vector(r.nextDouble(), r.nextDouble());
        this.bias = r.nextDouble();
    }


    /**
     * Apply the perceptron to classify a data vector.
     *
     * @param x An input vector
     * @return 0 or 1
     */
    public int predict(Vector x) {
        //if x * weight + bias is greater than 0 return 1 else return 0
        if(x.dot(this.weight) + this.bias > 0){
            return 1;
        }
        return 0;
    }
}
