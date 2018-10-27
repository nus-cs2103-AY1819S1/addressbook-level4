package guitests.guihandles;

import static seedu.address.ui.MedicineCard.MIN_STOCK_QUANTITY_PREFIX;
import static seedu.address.ui.MedicineCard.PRICE_PER_UNIT_PREFIX;
import static seedu.address.ui.MedicineCard.SERIAL_NUMBER_PREFIX;
import static seedu.address.ui.MedicineCard.STOCK_PREFIX;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.medicine.Medicine;

/**
 * Provides a handle to a medicine card in the medicine list panel.
 */
public class MedicineCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String MEDICINE_NAME_FIELD_ID = "#medicineName";
    private static final String SERIAL_NUMBER_FIELD_ID = "#serialNumber";
    private static final String PRICE_PER_UNIT_FIELD_ID = "#pricePerUnit";
    private static final String MIN_STOCK_FIELD_ID = "#minStockQuantity";
    private static final String STOCK_FIELD_ID = "#stock";

    private final Label idLabel;
    private final Label medicineNameLabel;
    private final Label serialNumberLabel;
    private final Label pricePerUnitLabel;
    private final Label minStockQuantityLabel;
    private final Label stockLabel;

    public MedicineCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        medicineNameLabel = getChildNode(MEDICINE_NAME_FIELD_ID);
        serialNumberLabel = getChildNode(SERIAL_NUMBER_FIELD_ID);
        pricePerUnitLabel = getChildNode(PRICE_PER_UNIT_FIELD_ID);
        minStockQuantityLabel = getChildNode(MIN_STOCK_FIELD_ID);
        stockLabel = getChildNode(STOCK_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getMedicineName() {
        return medicineNameLabel.getText();
    }

    public String getSerialNumber() {
        return serialNumberLabel.getText().substring(SERIAL_NUMBER_PREFIX.length());
    }

    public String getPricePerUnit() {
        return pricePerUnitLabel.getText().substring(PRICE_PER_UNIT_PREFIX.length());
    }

    public String getMinStockQuantity() {
        return minStockQuantityLabel.getText().substring(MIN_STOCK_QUANTITY_PREFIX.length());
    }

    public String getStock() {
        return stockLabel.getText().substring(STOCK_PREFIX.length());
    }

    /**
     * Returns true if this handle contains {@code patient}.
     */
    public boolean equals(Medicine medicine) {
        return getMedicineName().equals(medicine.getMedicineName().fullName)
                && getSerialNumber().equals(medicine.getSerialNumber().value)
                && getPricePerUnit().equals(medicine.getPricePerUnit().value)
                && getMinStockQuantity().equals(medicine.getMinimumStockQuantity().value)
                && getStock().equals(medicine.getStock().value);
    }

}
