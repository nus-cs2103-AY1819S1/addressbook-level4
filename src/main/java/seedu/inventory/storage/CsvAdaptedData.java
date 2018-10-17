package seedu.inventory.storage;

import java.util.List;

/**
 * A class to access data stored as an csv file on the hard disk.
 */
public interface CsvAdaptedData {
    List<List<String>> getContents();

    String getDataType();

    String[] getDataFields();

    CsvAdaptedData getInstance();

    CsvAdaptedData createInstance(List<List<String>> contents);
}
