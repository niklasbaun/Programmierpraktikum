package PPClean.DuplicateDetection;

import PPClean.Data.Duplicate;
import PPClean.Data.Record;
import PPClean.Data.Table;
import PPClean.Similarity.RecordSimilarity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Duplicate detection algorithm which first sorts the Table (according to a key)
 * and then only compares neighboring Records
 */
public class SortedNeighborhoodDetection implements DuplicateDetection {

    double threshold;
    int windowSize;
    int[] keyComponents;

    /**
     * @param threshold Threshold in range [0,1], Records at least this similar are considered Duplicates
     * @param windowSize Each record is compared with 2*(windowSize-1) neighboring Records
     * @param keyComponents Each component indicates how many characters of the content
     *                      value at the same list position are integrated in the key
     *                      Enter 0 to omit a Record value
     */
    public SortedNeighborhoodDetection(double threshold, int windowSize, int[] keyComponents) {
        this.threshold = threshold;
        this.windowSize = windowSize;
        this.keyComponents = keyComponents;
    }

    /**
     * @param table Table to check for duplicates
     * @param recSim Similarity measure to use for comparing two records
     * @return Set of detected duplicates
     */
    @Override
    public Set<Duplicate> detect(Table table, RecordSimilarity recSim) {
        Set<Duplicate> duplicates = new HashSet<>();
        int numComparisons = 0;
        // BEGIN SOLUTION

        //generate keys
        table.generateKeys(keyComponents);
        //sort table by key
        table.sortByKey();

        //iterate over table
       for(int i = 0; i < table.getData().size() - windowSize; i++){

           //iterate through a window
           for(int j = i +1; j < i + windowSize; j++){
                   //compare records
                   double sim = recSim.compare(table.getData().get(i), table.getData().get(j));
                   numComparisons++;
                   //check if similarity is above a threshold
                   if(sim >= threshold){
                       //add duplicate
                       duplicates.add(new Duplicate(table.getData().get(i), table.getData().get(j)));
                   }
           }
       }
        // END SOLUTION
        System.out.printf("Sorted Neighborhood Detection found %d duplicates after %d comparisons%n", duplicates.size(), numComparisons);
        return duplicates;
    }
}
