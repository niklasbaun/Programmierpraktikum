package PPClean.Similarity;

import PPClean.Data.Record;

/**
 * Simple heuristic to compare two Records
 * It compares two Records for equality based only on a single attribute
 */
public class SingleAttributeEquality implements RecordSimilarity {

    int attributeIndex;

    /**
     * @param attributeIndex Position of Record content at which to check for equality
     */
    public SingleAttributeEquality(int attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    /**
     * compares two attributes
     * @param r1 attribute 1
     * @param r2 attribute 2
     * @return 1 if r1 and r2 are equal at position {@link #attributeIndex}, else 0
     */
    @Override
    public double compare(Record r1, Record r2) {
        double res = 0;
        // BEGIN SOLUTION

        //check if r1 and r2 have the same length -> throw exception if not
        if(r1.getContent().size() != r2.getContent().size()){
            throw new IllegalArgumentException("Records have different length");
        }
        //check if attributeIndex is in range -> throw exception if not
        if(attributeIndex < 0 || attributeIndex >= r1.getContent().size()){
            throw new IllegalArgumentException("attributeIndex out of range");
        }


        //compare the two attributes at the given index if at same index returns 1
        if(r1.getContent().get(attributeIndex).equals(r2.getContent().get(attributeIndex))){
            res = 1;
        }

        // END SOLUTION
        return res;
    }
}
