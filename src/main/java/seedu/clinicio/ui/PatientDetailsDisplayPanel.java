package seedu.clinicio.ui;

import com.google.common.eventbus.Subscribe;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.LoginSuccessEvent;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Staff;

/**
 * A ui for display patient details.
 */
public class PatientDetailsDisplayPanel extends UiPart<Region>  {

    private static final String FXML = "PatientDetailsDisplayPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label name;
    
    public PatientDetailsDisplayPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    public void displayPatient(Patient patient) {
        Platform.runLater(() -> name.setText(patient.getName().toString()));
    }
    
    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPatient(event.getNewSelection());
    }
}
