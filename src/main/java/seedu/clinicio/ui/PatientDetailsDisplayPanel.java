package seedu.clinicio.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.layout.Region;

import javafx.scene.text.Text;

import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;
import seedu.clinicio.model.patient.Patient;

/**
 * A ui for display patient details.
 */
public class PatientDetailsDisplayPanel extends UiPart<Region> {

    private static final String FXML = "PatientDetailsDisplayPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Text name;

    @FXML
    private Text nric;

    @FXML
    private Text phone;

    @FXML
    private Text email;

    @FXML
    private Text address;
    
    @FXML
    private Text preferredDoctor;

    public PatientDetailsDisplayPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Display patient's details into the display panel.
     * @param patient The patient to be displayed.
     */
    public void displayPatient(Patient patient) {
        Platform.runLater(() -> {
            name.setText(patient.getName().toString());
            nric.setText("NRIC: " + patient.getNric().toString());
            phone.setText("Phone: " + patient.getPhone().toString());
            email.setText("Email: " + patient.getEmail().toString());
            address.setText("Address: " + patient.getAddress().toString());
            preferredDoctor.setText("Preferred Doctor: " + patient
                    .getPreferredDoctor().map(doctor -> doctor.getName().fullName).orElse("None"));
        });
    }

    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPatient(event.getNewSelection());
    }
}
