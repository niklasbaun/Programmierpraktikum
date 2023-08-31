package PPClean.Similarity;

import PPClean.Data.Record;

import java.util.List;

/**
 * Record similarity for comparing two Records attribute by attribute either with {@link Levenshtein} or {@link Jaccard}
 */
public class Hybrid implements RecordSimilarity {

    private List<String> policies;
    private final int JACCARD_N = 3;
    private StringSimilarity jaccard;
    private StringSimilarity levenshtein;

    /**
     * @param policies List of comparison policies, write "L" for {@link Levenshtein},
     *                 "J" {@link Jaccard}, and null to skip an attribute.
     *                 The policies are applied in order of attributes (e.g., first policy to first attribute)
     */
    public Hybrid(List<String> policies) {
        this.policies = policies;
        this.jaccard = new Jaccard(JACCARD_N);
        this.levenshtein = new Levenshtein();
    }

    /**
     * Compares two Records attribute by attribute according to {@link #policies}.
     * For Jaccard similarity, a default window size of {@link #JACCARD_N} is used
     * @param r1 Record 1
     * @param r2 Record 2
     * @return Similarity score in range [0,1] (1=same, 0=very different)
     */
    @Override
    public double compare(Record r1, Record r2) {
        double res = 0;
        int counter = 0;
        // BEGIN SOLUTION

        //check if r1 and r2 have the same length -> throw exception if not
        if (r1.getContent().size() != r2.getContent().size()) {
            throw new IllegalArgumentException("Records have different length");
        }
        //compare the two records attribute by attribute
        for(int i = 0; i < r1.getContent().size(); i++){
            //if policy is null skip attribute
            if(policies.get(i % policies.size()) == null){
                continue;
            } else if (policies.get(i % policies.size()).equals("L")){
                //compare with levenshtein
                res += levenshtein.compare(r1.getContent().get(i), r2.getContent().get(i));
                counter++;
            } else if (policies.get(i % policies.size()).equals( "J")){
                //compare with jaccard
                res += jaccard.compare(r1.getContent().get(i), r2.getContent().get(i));
                counter++;
            }
        }
        //res is the average of all comparisons
        res = res/counter;

        // END SOLUTION
        return res;
    }
}

