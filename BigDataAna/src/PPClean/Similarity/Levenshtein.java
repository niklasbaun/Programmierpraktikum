package PPClean.Similarity;

import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Levenshtein String similarity
 */
public class Levenshtein implements StringSimilarity {


    public Levenshtein() {
    }

    /**
     * Calculates Levenshtein String similarity for x and y
     * @param x
     * @param y
     * @return Similarity score in range [0,1]
     */
    @Override
    public double compare(String x, String y) {
        double res = 0;
        int m = x.length();
        int n = y.length();
        // BEGIN SOLUTION

        //create matrix
        int[][] matrix = new int[m+1][n+1];

        //fill matrix with basis m(i, 0) = i and n(j,0) = j
        for(int i = 0; i <= m; i++){
            for (int j = 0; j <= n; j++){
                //if either String is empty, fill with the other String length
                if(m==0){
                    matrix[m][n] = n;
                } else if (n == 0) {
                    matrix[m][n] = m;
                }
                if(i==0){
                    matrix[i][j] = j;
                } else if (j == 0) {
                    matrix[i][j] = i;
                }
                //find the minimum of the three possible operations
                else {
                    //compare the values in the original string if same only increment elements on top and left of pos at right now
                    //find min value of the three and set this at the current position
                    if(compareChar(x, y, i, j) == 0){
                        matrix[i][j] = min(min(matrix[i-1][j] + 1, matrix[i][j-1] + 1), matrix[i-1][j-1]);
                    } else {
                        //increment value on top, left and diagonal of current position and find min of the three
                        matrix[i][j] = min(min(matrix[i-1][j] + 1, matrix[i][j-1] + 1), matrix[i-1][j-1] + 1);
                    }
                }
            }
        }

        //calc levenshtein similiarity
         res = 1 -(double) matrix[m][n]/max(m,n);

        // END SOLUTION
        return res;
    }

    /**
     * helper to compare the connected characters in the original Strings to the position in the matrix
     * @param x String 1
     * @param y String 2
     * @param i position in String 1
     * @param j position in String 2
     * @return 0 if the characters are equal, 1 if not
     */
    private int compareChar(String x, String y, int i, int j) {
        if (x.charAt(i-1) == y.charAt(j-1)) {
            return 0;
        } else {
            return 1;
        }
    }
}
