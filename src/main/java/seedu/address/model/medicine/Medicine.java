package seedu.address.model.medicine;

import java.util.ArrayList;

import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.Stock;

public class Medicine {
    private static ArrayList<Medicine> medicineLowStockList;
    private MedicineName medicineName;
    private MinimumStockQuantity minimumStockQuantity;
    private PricePerUnit pricePerUnit;
    private SerialNumber serialNumber;
    private Stock stock;

    public Medicine(MedicineName medicineName, MinimumStockQuantity minimumStockQuantity, PricePerUnit pricePerUnit, SerialNumber serialNumber, Stock stock) {
        this.medicineName = medicineName;
        this.minimumStockQuantity = minimumStockQuantity;
        this.pricePerUnit = pricePerUnit;
        this.serialNumber = serialNumber;
        this.stock = stock;
    }
}
