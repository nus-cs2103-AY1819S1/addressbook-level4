package seedu.clinicio.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
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

    @FXML
    private TabPane patientTabLists;

    @FXML
    private ListView<String> medProbListView;

    @FXML
    private ListView<String> medListView;

    @FXML
    private ListView<String> alrgListView;

    public PatientDetailsDisplayPanel() {
        super(FXML);
        patientTabLists.setVisible(false);
        registerAsAnEventHandler(this);
    }

    /**
     * Display patient's details into the display panel.
     * @param patient The patient to be displayed.
     */
    public void displayPatient(Patient patient) {
        Platform.runLater(() -> {
            patientTabLists.setVisible(true);
            setUpPatientDetails(patient);
        });
    }

    /**
     * Set up all patient details into the display panel.
     * @param patient The patient to be displayed.
     */
    private void setUpPatientDetails(Patient patient) {
        name.setText(patient.getName().toString());
        nric.setText("NRIC: " + patient.getNric().toString());
        phone.setText("Phone: " + patient.getPhone().toString());
        email.setText("Email: " + patient.getEmail().toString());
        address.setText("Address: " + patient.getAddress().toString());
        preferredDoctor.setText("Preferred Doctor: " + patient
                .getPreferredDoctor().map(doctor -> doctor.getName().fullName).orElse("None"));

        setAllListView(patient);
    }

    /**
     * Set all the list view with items.
     * @param patient Selected patient
     */
    private void setAllListView(Patient patient) {
        clearListView();

        List<String> medicalProblemsList = patient.getMedicalProblems()
                .stream().map(medProb -> medProb.medProb).collect(Collectors.toList());
        List<String> medicationsList = patient.getMedications()
                .stream().map(med -> med.value).collect(Collectors.toList());
        List<String> allergiesList = patient.getAllergies()
                .stream().map(allergy -> allergy.allergy).collect(Collectors.toList());
        addItemsToListView(medProbListView, medicalProblemsList);
        addItemsToListView(medListView, medicationsList);
        addItemsToListView(alrgListView, allergiesList);
    }

    /**
     * Add list into the list view.
     * @param listView The selected list view to add items in.
     * @param itemsList The valid list of strings.
     */
    private void addItemsToListView(ListView<String> listView, List<String> itemsList) {
        // Add the no content message if there is nothing in the items list.
        if (itemsList.isEmpty()) {
            itemsList.add("No items found.");
        }
        itemsList.forEach(item -> listView.getItems().add(item));

    }

    /**
     * Clear all the items in all list views.
     */
    private void clearListView() {
        medProbListView.getItems().clear();
        medListView.getItems().clear();
        alrgListView.getItems().clear();
    }

    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPatient(event.getNewSelection());
    }
}
