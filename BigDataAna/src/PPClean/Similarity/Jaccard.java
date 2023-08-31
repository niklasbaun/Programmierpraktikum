package PPClean.Similarity;

import java.util.HashSet;
import java.util.Set;

/**
 * Jaccard String similarity
 */
public class Jaccard implements StringSimilarity {

    int n;

    /**
     * @param n Length of substrings
     */
    public Jaccard(int n) {
        this.n = n;
    }

    /**
     * Calculates Jaccard String similarity for x and y, using ngrams of length {@link #n}
     * @param x
     * @param y
     * @return Similarity score in range [0,1]
     */
    @Override
    public double compare(String x, String y) {
        double res = 0;
        Set<String> ngramsX = new HashSet<>();
        Set<String> ngramsY = new HashSet<>();
        // BEGIN SOLUTION

        //create ngrams for x and y
        for (int i = 0; i < x.length() - n + 1; i++) {
            ngramsX.add(x.substring(i, i + n));
        }
        for(int i = 0; i < y.length() - n + 1; i++){
            ngramsY.add(y.substring(i, i + n));
        }

        //calc intersection
        Set<String> intersection = new HashSet<>(ngramsX);
        //method from Set to find intersection (only keeps elements that are in both sets)
        intersection.retainAll(ngramsY);

        //calc union
        Set<String> union = new HashSet<>(ngramsX);
        //addAll, will only add Strings not prevously contained, because of Set proporties
        union.addAll(ngramsY);

        //calc jaccard similarity
        res = (double) intersection.size()/union.size();

        // END SOLUTION
        return res;
    }
}
