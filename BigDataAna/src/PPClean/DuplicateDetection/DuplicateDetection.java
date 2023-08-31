package PPClean.DuplicateDetection;

import PPClean.Data.Duplicate;
import PPClean.Data.Table;
import PPClean.Similarity.RecordSimilarity;

import java.util.Set;

/**
 * Interface for duplicate detection algorithms
 */
public interface DuplicateDetection {
    /**
     * @param table Table to check for duplicates
     * @param recSim Similarity measure to use for comparing two records
     * @return Set of detected duplicates
     */
    Set<Duplicate> detect(Table table, RecordSimilarity recSim);
}
