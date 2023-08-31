package PPClean.DuplicateDetection;

import PPClean.Data.Duplicate;
import PPClean.Data.Record;
import PPClean.Data.Table;
import src.PPClean.Similarity.RecordSimilarity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Duplicate detection algorithm which compares every record with every other
 */
public class NaiveDetection implements DuplicateDetection {

    double threshold;

    /**
     * @param threshold Threshold in range [0,1], Records at least this similar are considered Duplicates
     */
    public NaiveDetection(double threshold) {
        this.threshold = threshold;
    }

    /**
     * @param table Table to check for duplicates
     * @param recSim Similarity measure to use for comparing two records
     * @return Set of detected duplicates
     */
    public Set<Duplicate> detect(Table table, RecordSimilarity recSim) {
        List<Record> records = table.getData();
        Set<Duplicate> duplicates = new HashSet<>();
        int numComparisons = 0;
        // BEGIN SOLUTION
        //compare every record with every other and increment numComparisons if duplicate found
        for(int i = 0; i < records.size(); i++){
            for(int j = i+1; j < records.size(); j++){
                if(recSim.compare(records.get(i), records.get(j)) >= threshold){
                    duplicates.add(new Duplicate(records.get(i), records.get(j)));
                    numComparisons++;
                    //check if duplicate is already in set else add it
                    if(duplicates.contains(new Duplicate(records.get(j), records.get(i)))){
                        duplicates.add(new Duplicate(records.get(j), records.get(i)));
                    }
                }
            }
        }


        // END SOLUTION
        System.out.printf("Naive Detection found %d duplicates after %d comparisons%n", duplicates.size(), numComparisons);
        return duplicates;
    }
}
