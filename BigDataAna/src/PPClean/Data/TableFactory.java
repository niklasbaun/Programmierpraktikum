package PPClean.Data;

import static PPClean.Configuration.DATA_SEPARATOR;
import static PPClean.Configuration.PATH_INPUT_DATA;

/**
 * Factory to produce instances of Table
 */
public class TableFactory {
    /**
     * @return Table for default input path and default data separator (see {@link PPClean.Configuration})
     */
    public static Table getDefaultInputTable() {
        return Table.createFromFile(PATH_INPUT_DATA, DATA_SEPARATOR);
    }

    /**
     * Get a table from a custom input path and with a custom data separator
     * @param pathToTable Absolute or relative path for table file
     * @param dataSeparator Separator between data points (e.g., comma for CSV)
     * @return Table parsed from file
     */
    public static Table getCustomTable(String pathToTable, String dataSeparator) {
        return Table.createFromFile(pathToTable, dataSeparator);
    }
}
