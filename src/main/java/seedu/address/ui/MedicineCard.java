package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.medicine.Medicine;

/**
 * An UI component that displays information of a {@code Medicine}.
 */
public class MedicineCard extends UiPart<Region> {

    public static final String SERIAL_NUMBER_PREFIX = "S/N: ";
    public static final String PRICE_PER_UNIT_PREFIX = "Price/Unit: ";
    public static final String MIN_STOCK_QUANTITY_PREFIX = "Minimum Qty: ";
    public static final String STOCK_PREFIX = "Stock: ";

    private static final String FXML = "MedicineListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Medicine medicine;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label medicineName;
    @FXML
    private Label serialNumber;
    @FXML
    private Label pricePerUnit;
    @FXML
    private Label minStockQuantity;
    @FXML
    private Label stock;

    public MedicineCard(Medicine medicine, int displayedIndex) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        medicineName.setText(medicine.getMedicineName().fullName);
        serialNumber.setText(SERIAL_NUMBER_PREFIX + medicine.getSerialNumber().value);
        pricePerUnit.setText(PRICE_PER_UNIT_PREFIX + medicine.getPricePerUnit().value);
        minStockQuantity.setText(MIN_STOCK_QUANTITY_PREFIX + medicine.getMinimumStockQuantity().value);
        stock.setText(STOCK_PREFIX + medicine.getStock().value);

        if (medicine.getStock().value < medicine.getMinimumStockQuantity().value) {
            // Need to restock! Let's colour it red!
            stock.setStyle("-fx-text-fill: rgba(255,56,34,0.87)");
        } else if (medicine.getStock().value <= medicine.getMinimumStockQuantity().value * 1.1) {
            // Going below minimum stock soon! Let's colour it yellow!
            stock.setStyle("-fx-text-fill: rgba(255,201,15,0.87)");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedicineCard)) {
            return false;
        }

        // state check
        MedicineCard card = (MedicineCard) other;
        return id.getText().equals(card.id.getText())
                && medicine.equals(card.medicine);
    }
}
