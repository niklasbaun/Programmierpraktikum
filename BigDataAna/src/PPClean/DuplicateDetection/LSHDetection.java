package PPClean.DuplicateDetection;

import PPClean.Helper;

import PPClean.Data.Duplicate;
import PPClean.Data.Record;
import PPClean.Data.Table;
import PPClean.Similarity.RecordSimilarity;

import java.util.*;

public class LSHDetection implements DuplicateDetection {

    // Hashing
    int HASH_BASE = 17;
    int HASH_PRIME = 19;
    // Tokenization
    int tokenSize;
    List<String> tokenUniverse;
    boolean[][] tokenMatrix;
    // MinHashing
    int numMinHashs;
    int[][] signatureMatrix;
    // Locality Sensitive Hashing
    int numBands;
    ArrayList<Hashtable<Integer, List<Integer>>> LSH;
    // Duplicate Detection
    double threshold;

    /**
     * @param tokenSize Number of characters per token
     * @param numMinHashs Number of min hashes
     * @param numBands Number of bands
     * @param threshold Similarity threshold between 0 and 1 to use for filtering duplicates
     */
    public LSHDetection(int tokenSize, int numMinHashs, int numBands, double threshold) {
        if (numMinHashs % numBands != 0) {
            throw new IllegalArgumentException("numMinHashs needs to be divisible by numBands");
        }
        this.tokenSize = tokenSize;
        this.numMinHashs = numMinHashs;
        this.numBands = numBands;
        this.threshold = threshold;
    }

    /**
     * Calculates {@link LSHDetection#tokenUniverse}: a list of all tokens in the entire table
     * and {@link LSHDetection#tokenMatrix}: a boolean matrix with as many rows as there are tokens in
     * the tokenUniverse and as many columns as there are records in the table. A true boolean value in
     * cell (i, j) means that the i-th token appears in the j-th record.
     * The size of tokens is determined by {@link LSHDetection#tokenSize}.
     * @param table Table to use to calculate tokens
     */
    private void calculateTokens(Table table) {
        // BEGIN SOLUTION

        //create tokenUniverse
        tokenUniverse = new ArrayList<>();
        //fill tokenUniverse
        for(int i = 0; i < table.getData().size(); i++){
            //get record
            Record record = table.getData().get(i);
            String recordString  = record.toString();
            //iterate over recordString
            for (int j = 0; j < recordString.length() - tokenSize; j++){
                //check if token is in tokenUniverse
                if(!tokenUniverse.contains(recordString.substring(j, j + tokenSize))){
                    //add token to tokenUniverse
                    tokenUniverse.add(recordString.substring(j, j + tokenSize));
                }
            }
        }
        //create tokenMatrix
        tokenMatrix = new boolean[tokenUniverse.size()][table.getData().size()];
        //fill tokenMatrix
        for (int i = 0; i < tokenUniverse.size(); i++){
            for (int j = 0; j < table.getData().size(); j++){
                //get record
                Record record = table.getData().get(j);
                //make string from record
                String recordString  = record.toString();
                //check if token is in record
                if(recordString.contains(tokenUniverse.get(i))){
                    //set tokenMatrix[i][j] to true
                    tokenMatrix[i][j] = true;
                }
            }
        }

        // END SOLUTION
    }

    /**
     * Calculates {@link LSHDetection#signatureMatrix}: a matrix with {@link LSHDetection#numMinHashs} many rows
     * and as many columns as there are records in the table.
     * An integer value k at cell (i,j) says that for the i-th permutation of the {@link LSHDetection#tokenMatrix}
     * and for the j-th record in the table, a token of record j is at row k and rows 0 to k-1 have no tokens of record j.
     * @param table Table used to calculate min hashes
     */
    private void calculateMinHashes(Table table) {
        // BEGIN SOLUTION

        //control boolean
        boolean control = false;

        //create signatureMatrix
        signatureMatrix = new int[numMinHashs][table.getData().size()];
        //fill signatureMatrix
        for(int i = 0; i < numMinHashs; i++){
            //go over tabel
            for(int j = 0; j < table.getData().size(); j++){
                control = true;
                //iterate over tokenMatrix
                for(int k = 0; k < tokenMatrix.length; k++){
                    //check if tokenMatrix[k][j] is true
                    if(tokenMatrix[k][j]){
                        //set signatureMatrix[i][j] to k
                        signatureMatrix[i][j] = k;
                        control = false;
                        break;
                    }
                }
                //check if control is true
                if(control){
                    //set entry to -1
                    signatureMatrix[i][j] = -1;
                }
            }
        }
        //shuffle tokenMatrix
        Helper.shuffleMatrixRows(tokenMatrix);
        // END SOLUTION
    }

    private int hash(int[] band) {
        int hash = this.HASH_BASE;
        for (int i : band) {
            hash = hash * this.HASH_PRIME + i;
        }
        return hash;
    }

    /**
     * Calculates a hashtable for every band and adds it to {@link LSHDetection#LSH}. Uses {@link LSHDetection#hash(int[])}
     * to hash a band to an integer. For every hash value we store a list of record ids, these lists represent
     * buckets of duplicate candidates.
     */
    private void calculateHashBuckets() {
        // BEGIN SOLUTION

        //create LSH
        LSH = new ArrayList<>();

        //set band length
        int bandLength = numMinHashs/ numBands;

        //generate new bands
        for (int i = 0; i < numMinHashs -bandLength; i++){
            //create bucket
            Hashtable<Integer, List<Integer>> bucket = new Hashtable<>();
            //iterate over each record
            for(int j = 0; j < signatureMatrix[0].length; j++){
                //create band
                int[] band = new int[bandLength];
                for(int k = 0; k < bandLength; k++){
                    band[k] = signatureMatrix[i + k][j];
                }
                //Integerlist with all record ids in one bucket
                List<Integer> recordIds = new ArrayList<>();
                //check if bucket contains content
                if(bucket.containsKey(hash(band))){
                    //get recordIds
                    recordIds = bucket.get(hash(band));
                }else {
                    //add recordId to recordIds
                    recordIds.add(j);
                }
                //add recordIds to bucket
                bucket.put(hash(band), recordIds);
            }
            //add bucket to LSH
            LSH.add(bucket);
            //jump to the next band
            i = i + bandLength;
        }



        // END SOLUTION
    }

    /**
     * First calculates tokens, minHashes and hash buckets.
     * Then iterates over all hashtables in {@link LSHDetection#LSH} and over all hash keys to compare all records
     * who share at least one hash bucket.
     * @param table Table to check for duplicates
     * @param recSim Similarity measure to use for comparing two records
     * @return Set of detected duplicates
     */
    @Override
    public Set<Duplicate> detect(Table table, RecordSimilarity recSim) {
        Set<Duplicate> duplicates = new HashSet<>();
        int numComparisons = 0;
        calculateTokens(table);
        calculateMinHashes(table);
        calculateHashBuckets();
        // BEGIN SOLUTION

        //iterate over LSH
        for (int i = 0; i< LSH.size(); i++){
            //in each bucket check for duplicates
            //if bucket only contains one record -> no duplicates
            if(LSH.get(i).size() > 1){
                //in each bucket check for duplicates
                for (int key : LSH.get(i).keySet()){
                    //get recordIds
                    List<Integer> recordIds = LSH.get(i).get(key);
                    //iterate over recordIds
                    for(int j = 0; j < recordIds.size(); j++){
                        //iterate over recordIds
                        for(int k = j + 1; k < recordIds.size(); k++){
                            //compare records
                            double sim = recSim.compare(table.getData().get(recordIds.get(j)), table.getData().get(recordIds.get(k)));
                            numComparisons++;
                            //check if similarity is above a threshold
                            if(sim >= threshold){
                                //add duplicate
                                duplicates.add(new Duplicate(table.getData().get(recordIds.get(j)), table.getData().get(recordIds.get(k))));
                            }
                        }
                    }
                }
            }
        }

        // END SOLUTION
        System.out.printf("LSH Detection found %d duplicates after %d comparisons%n", duplicates.size(), numComparisons);
        return duplicates;
    }
}
