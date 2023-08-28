package de.umr.ds.task3;

public class Vector {

	private double[] p;

	/**
	 * Creates a vector with a minimum dimension of 2.
	 * 
	 * @param p values
	 */
	public Vector(double... p) {
		if (p.length < 2)
			throw new IllegalArgumentException("Minimum dimension is 2");
		this.p = p;
	}

	public double getX() {
		return p[0];
	}

	public double getY() {
		return p[1];
	}

	public double getDim(int d) {
		if (d >= p.length)
			throw new IllegalArgumentException("Maximum dimension is " + d);
		return p[d];
	}

	public int numDims() {
		return p.length;
	}

	/**
	 * Computes the dot product between the vector and another one.
	 * 
	 * @param v A vector of the same dimension
	 * @return The dot product between the two vectors
	 */
	public double dot(Vector v) {
		//check if vectors have the same dimension
		if(v.numDims() != this.numDims()){
			throw new IllegalArgumentException("Vectors must have the same dimension");
		}
		//compute dot product
		double result = 0;
		for(int i = 0; i < v.numDims(); i++){
			result += v.getDim(i) * this.getDim(i);
		}

		return result;
	}

	/**
	 * Adds the vector to another one.
	 * 
	 * @param v A vector of the same dimension
	 * @return A new vector
	 */
	public Vector add(Vector v) {
		//check if vectors have the same dimension
		if(v.numDims() != this.numDims()){
			throw new IllegalArgumentException("Vectors must have the same dimension");
		}
		//add vectors
		double[] result = new double[v.numDims()];
		for(int i = 0; i < v.numDims(); i++){
			result[i] = v.getDim(i) + this.getDim(i);
		}
		return new Vector(result);
	}

	/**
	 * Multiplies the vector with a scalar.
	 * 
	 * @param s A scalar
	 * @return A new vector
	 */
	public Vector mult(double s) {
		//multiply vector with scalar
		double[] result = new double[this.numDims()];
		for(int i = 0; i < this.numDims(); i++){
			result[i] = this.getDim(i) * s;
		}
		return new Vector(result);
	}
}
