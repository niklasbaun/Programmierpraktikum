package PPClean;

import PPClean.Data.Duplicate;
import PPClean.Data.Table;
import PPClean.Data.TableFactory;
import PPClean.Performance.Performance;

import java.util.Set;

import static PPClean.Configuration.*;

public class PPClean {

    public static void main(String[] args) {
        Table inputTable = TableFactory.getDefaultInputTable();
        Set<Duplicate> groundTruth = Helper.readDuplicatesFromDefaultGT();
        Performance performance = Performance.initInstance(groundTruth);
        // Hier könnt ihr nach Belieben rumexperimentieren
        // Zum Bestehen wichtig sind lediglich die Tests
    }
}
