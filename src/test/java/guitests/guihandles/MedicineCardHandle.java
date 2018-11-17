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

    private final Label idLabel;
    private final Label medicineNameLabel;

    public MedicineCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        medicineNameLabel = getChildNode(MEDICINE_NAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getMedicineName() {
        return medicineNameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code medicine}.
     */
    public boolean equals(Medicine medicine) {
        return getMedicineName().equals(medicine.getMedicineName().medicineName);
    }

}
