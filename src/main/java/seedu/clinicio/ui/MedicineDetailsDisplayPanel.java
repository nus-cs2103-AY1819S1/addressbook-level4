package seedu.clinicio.ui;

//@@author aaronseahyh

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.layout.Region;

import javafx.scene.text.Text;

import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.MedicinePanelSelectionChangedEvent;
import seedu.clinicio.model.medicine.Medicine;

/**
 * UI to display medicine details.
 */
public class MedicineDetailsDisplayPanel extends UiPart<Region> {

    private static final String FXML = "MedicineDetailsDisplayPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Text medicineName;

    @FXML
    private Text medicineType;

    @FXML
    private Text effectiveDosage;

    @FXML
    private Text lethalDosage;

    @FXML
    private Text price;

    @FXML
    private Text quantity;

    public MedicineDetailsDisplayPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Display medicine's details into the display panel.
     * @param medicine The medicine to be displayed.
     */
    public void displayMedicine(Medicine medicine) {
        Platform.runLater(() -> {
            medicineName.setText(medicine.getMedicineName().medicineName);
            medicineType.setText("Type: " + medicine.getMedicineType().medicineType);
            effectiveDosage.setText("Effective Dosage: " + medicine.getEffectiveDosage().medicineDosage + " units");
            lethalDosage.setText("Lethal Dosage: " + medicine.getLethalDosage().medicineDosage + " units");
            price.setText("Price: $" + medicine.getPrice().medicinePrice + " per unit");
            quantity.setText("Quantity: " + medicine.getQuantity().medicineQuantity + " units");
        });
    }

    @Subscribe
    private void handleMedicinePanelSelectionChangedEvent(MedicinePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayMedicine(event.getNewSelection());
    }
}
