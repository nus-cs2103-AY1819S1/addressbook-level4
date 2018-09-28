package seedu.address.model.medicine;

import java.util.ArrayList;

public class Medicine {
    private static ArrayList<Medicine> medicineLowStockList;
    private String name;
    private int minimumQuantityInStock;
    private int stock;
    private int serialNumber;
    private long pricePerUnit;

    public Medicine(String name, int minimumQuantityInStock, int stock, int serialNumber, long pricePerUnit) {
        this.name = name;
        this.minimumQuantityInStock = minimumQuantityInStock;
        this.stock = stock;
        this.serialNumber = serialNumber;
        this.pricePerUnit = pricePerUnit;
    }

}
