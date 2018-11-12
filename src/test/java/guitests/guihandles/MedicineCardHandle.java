package guitests.guihandles;

//@@author aaronseahyh

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.clinicio.model.medicine.Medicine;

/**
 * Provides a handle to a medicine card in the medicine list panel.
 */
public class MedicineCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String MEDICINE_NAME_FIELD_ID = "#medicineName";
    private static final String MEDICINE_TYPE_FIELD_ID = "#medicineType";
    private static final String EFFECTIVE_DOSAGE_FIELD_ID = "#effectiveDosage";
    private static final String LETHAL_DOSAGE_FIELD_ID = "#lethalDosage";
    private static final String MEDICINE_PRICE_FIELD_ID = "#price";
    private static final String MEDICINE_QUANTITY_FIELD_ID = "#quantity";

    private final Label idLabel;
    private final Label medicineNameLabel;
    private final Label medicineTypeLabel;
    private final Label effectiveDosageLabel;
    private final Label lethalDosageLabel;
    private final Label priceLabel;
    private final Label quantityLabel;

    public MedicineCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        medicineNameLabel = getChildNode(MEDICINE_NAME_FIELD_ID);
        medicineTypeLabel = getChildNode(MEDICINE_TYPE_FIELD_ID);
        effectiveDosageLabel = getChildNode(EFFECTIVE_DOSAGE_FIELD_ID);
        lethalDosageLabel = getChildNode(LETHAL_DOSAGE_FIELD_ID);
        priceLabel = getChildNode(MEDICINE_PRICE_FIELD_ID);
        quantityLabel = getChildNode(MEDICINE_QUANTITY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getMedicineName() {
        return medicineNameLabel.getText();
    }

    public String getMedicineType() {
        return medicineTypeLabel.getText();
    }

    public String getEffectiveDosage() {
        return effectiveDosageLabel.getText();
    }

    public String getLethalDosage() {
        return lethalDosageLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getQuantity() {
        return quantityLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code medicine}.
     */
    public boolean equals(Medicine medicine) {
        return getMedicineName().equals(medicine.getMedicineName().medicineName)
                && getMedicineType().equals(medicine.getMedicineType().medicineType)
                && getEffectiveDosage().equals(medicine.getEffectiveDosage().medicineDosage)
                && getLethalDosage().equals(medicine.getLethalDosage().medicineDosage)
                && getPrice().equals(medicine.getPrice().medicinePrice)
                && getQuantity().equals(medicine.getQuantity().medicineQuantity);
    }

}
