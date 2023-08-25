package de.umr.ds.task1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KernelTest {

    @Test
    void convolve() {
        //create image
        int[][] img = { {0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 1, 1, 0},
                        {0, 0, 1, 1, 0, 0, 0},
                        {0, 1, 1, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0, 0, 0}};
        //create kernel
        Kernel k = new Kernel(new double[][]{   {1, 0, 1},
                                                {0, 1, 0},
                                                {1, 0, 1}});
        //create expected result
        int[][] result = {  {1, 4, 3, 4, 1},
                            {1, 2, 4, 3, 3},
                            {1, 2, 3, 4, 1},
                            {1, 3, 3, 1, 1},
                            {3, 3, 1, 1, 0}};
        //convolve image with kernel
        int[][] convolved = k.convolve(img);
        //compare result with expected result
        //check if size is correct
        assertEquals(5, result.length);
        assertEquals(5, result[0].length);
        assertEquals(4, convolved[0][3]);
    }
}