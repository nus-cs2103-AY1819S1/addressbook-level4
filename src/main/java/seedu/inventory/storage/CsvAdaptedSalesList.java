package seedu.inventory.storage;

import java.util.List;

/**
 * A class to access item list data stored as an csv file on the hard disk.
 */
public class CsvAdaptedSalesList implements CsvAdaptedData {
    public static final String DATA_TYPE = new StringBuilder("Sales").toString();
    public static final String[] FIELDS = {"saleProduct", "saleQuantity", "saleDate"};

    private List<List<String>> contents;

    public CsvAdaptedSalesList(List<List<String>> contents) {
        this.contents = contents;
    }

    private CsvAdaptedSalesList() {}

    @Override
    public List<List<String>> getContents() {
        return contents;
    }

    @Override
    public String getDataType() {
        return DATA_TYPE;
    }

    @Override
    public String[] getDataFields() {
        return FIELDS;
    }

    @Override
    public CsvAdaptedData getInstance() {
        return new CsvAdaptedSalesList();
    }

    @Override
    public CsvAdaptedData createInstance(List<List<String>> contents) {
        return new CsvAdaptedSalesList(contents);
    }
}
