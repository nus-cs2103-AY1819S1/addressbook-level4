package seedu.clinicio.ui;

//@@author aaronseahyh

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.clinicio.model.medicine.Medicine;

/**
 * An UI component that displays information of a {@code Medicine}.
 */
public class MedicineCard extends UiPart<Region> {

    private static final String FXML = "MedicineListCard.fxml";

    public final Medicine medicine;

    @FXML
    private HBox cardPane;
    @FXML
    private Label medicineName;
    @FXML
    private Label medicineType;
    @FXML
    private Label effectiveDosage;
    @FXML
    private Label lethalDosage;
    @FXML
    private Label price;
    @FXML
    private Label quantity;
    @FXML
    private Label id;

    public MedicineCard(Medicine medicine, int displayedIndex) {
        super(FXML);
        this.medicine = medicine;
        id.setText(displayedIndex + ". ");
        medicineName.setText(medicine.getMedicineName().medicineName);
        medicineType.setText(medicine.getMedicineType().medicineType);
        effectiveDosage.setText(medicine.getEffectiveDosage().medicineDosage);
        lethalDosage.setText(medicine.getLethalDosage().medicineDosage);
        price.setText(medicine.getPrice().medicinePrice);
        quantity.setText(medicine.getQuantity().medicineQuantity);
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
