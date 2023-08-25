package de.umr.ds.task1;

/**
 * A kernel is a 2D-array. The array is transposed after initialization which
 * enables a more intuitive way of initializing a kernel. E.g a non-symmetric
 * kernel can be initialized by Kernel({{0,0,1} {0,1,0} {1,0,0}}) although the
 * array dimensions are actually [height][width].
 *
 */
public class Kernel {

	private double[][] k;
	private int height;
	private int width;

	/**
	 * Initializes the kernel by its transpose.
	 * 
	 * @param k 2D array
	 */
	Kernel(double[][] k) {
		// transpose
		this.k = new double[k[0].length][k.length];
		for (int x = 0; x < k[0].length; x++)
			for (int y = 0; y < k.length; y++)
				this.k[x][y] = k[y][x];
		this.width = this.k.length;
		this.height = this.k[0].length;

		if (this.width % 2 != 1 || this.height % 2 != 1)
			throw new IllegalArgumentException("Kernel size need to be odd-numbered");
		if (this.width < 3 || this.height < 3)
			throw new IllegalArgumentException("Minimum dimension is 3");
	}

	/**
	 * Convolves a single-channel image with the kernel.
	 * 
	 * @param img A single-channel image
	 * @return The convolved image
	 */
	public int[][] convolve(int[][] img) {
		//difference in Size between input and output image
		int a = k.length -1;
		int b = k[0].length -1;
		//create result array
		int[][] result = new int[img.length - a][img[0].length - b];
		//iterate over image
		//move the kernel over the image one step at a time
		for(int x = 0; x < result.length; x++){
			for(int y = 0; y < result[0].length; y++){
				//calculate the value for the current pixel
				double value = 0;
				for(int i = 0; i < k.length; i++){
					for(int j = 0; j < k[0].length; j++){
						//x; y for image and i; j for kernel
						value += (img[x + a - i][y + b - j] * k[i][j]);
					}
				}
				//set the value for the current pixel
				result[x][y] = (int) Math.round(value);
			}
		}
		return result;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
