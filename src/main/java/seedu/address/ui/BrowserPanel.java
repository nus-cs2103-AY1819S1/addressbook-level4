package seedu.address.ui;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowCurrentPatientViewEvent;
import seedu.address.commons.events.ui.ShowDefaultBrowserEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;
import seedu.address.model.person.medicalrecord.Note;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    private static final String FXML = "BrowserPanel.fxml";
    private static final String PATIENT_VIEW_URL = "PatientView.html";
    private static final String QUEUE_INFORMATION_URL = "QueueInformation.html";
    private static final String CURRENT_PATIENT_VIEW_URL = "CurrentPatientView.html";

    private WebViewScript webViewScript;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // Initialise WebViewScript to run the script in the browser
        this.webViewScript = new WebViewScriptManager(this.browser);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Loads the page for given patient
     * @param patient patient to load
     */
    private void loadPersonPage(Patient patient) {
        String url = MainApp.class.getResource(FXML_FILE_FOLDER + PATIENT_VIEW_URL).toExternalForm();
        webViewScript.runScript(getScriptForPatientView(patient));
        loadPage(url);
    }

    /**
     * Loads a HTML file that displays the queue information
     */
    private void loadQueueInfomationPage(PatientQueue patientQueue, CurrentPatient currentPatient,
                                         ServedPatientList servedPatientList) {
        String url = MainApp.class.getResource(FXML_FILE_FOLDER + QUEUE_INFORMATION_URL).toExternalForm();
        webViewScript.runScript(getScriptForQueueInformation(patientQueue, currentPatient, servedPatientList));
        loadPage(url);
    }

    /**
     * Loads a HTML file that displays the current patient's information.
     */
    private void loadCurrentPatientPage(CurrentPatient currentPatient) {
        String url = MainApp.class.getResource(FXML_FILE_FOLDER + CURRENT_PATIENT_VIEW_URL).toExternalForm();
        webViewScript.runScript(getScriptForCurrentPatientView(currentPatient));
        loadPage(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleShowDefaultBrowserEvent(ShowDefaultBrowserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }

    @Subscribe
    private void handleShowQueueInformationEvent(ShowQueueInformationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadQueueInfomationPage(event.getPatientQueue(), event.getCurrentPatient(), event.getServedPatientList());
    }

    @Subscribe
    private void handleShowCurrentPatientView(ShowCurrentPatientViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCurrentPatientPage(event.getCurrentPatient());
    }

    /**
     * Gets the javascript code to run for PatientView.html
     */
    private String getScriptForPatientView(Patient patient) {
        String script = "loadPatientDetails('";
        script += patient.getName().fullName;
        script += "', '";
        script += patient.getIcNumber().value;
        script += "', '";
        script += patient.getAddress().value;
        script += "', '";
        script += patient.getPhone().value;
        script += "', '";
        script += patient.getEmail().value;
        script += "', '";
        script += patient.getMedicalRecord().getBloodType().value;
        script += "', '";
        script += convertListToString(patient.getMedicalRecord().getDiseaseHistory());
        script += "', '";
        script += convertListToString(patient.getMedicalRecord().getDrugAllergies());
        script += "', '";
        script += convertNotesToString(patient.getMedicalRecord().getNotes());
        script += "');";
        return script;
    }

    /**
     * Gets the javascript code for CurrentPatientView.html
     */
    private String getScriptForCurrentPatientView(CurrentPatient currentPatient) {
        String script = "loadCurrentPatientDetails('";
        script += currentPatient.getPatient().getName().fullName;
        script += "', '";
        script += currentPatient.getPatient().getIcNumber().value;
        script += "', '";
        script += currentPatient.getPatient().getMedicalRecord().getBloodType().value;
        script += "', '";
        script += convertListToString(currentPatient.getPatient().getMedicalRecord().getDiseaseHistory());
        script += "', '";
        script += convertListToString(currentPatient.getPatient().getMedicalRecord().getDrugAllergies());
        script += "', '";
        script += convertNotesToString(currentPatient.getPatient().getMedicalRecord().getNotes());
        script += "', '";
        script += currentPatient.getMcContent();
        script += "', '";
        script += currentPatient.getReferralContent();
        script += "', '";
        script += currentPatient.getNoteContent();
        script += "', '";
        if (currentPatient.getMedicineAllocated().isEmpty()) {
            script += "<br>";
        } else {
            for (Map.Entry<Medicine, QuantityToDispense> entry : currentPatient.getMedicineAllocated().entrySet()) {
                script += entry.getKey().getMedicineName().fullName;
                script += " x ";
                script += entry.getValue().toString();
                script += "<br>";
            }
        }
        script = script.substring(0, script.length() - 4);
        script += "');";

        return script;
    }

    /**
     * Gets the javascript code for QueueInformation.html
     */
    private String getScriptForQueueInformation(PatientQueue patientQueue, CurrentPatient currentPatient,
                                                ServedPatientList servedPatientList) {
        String script = "loadQueueInformation('";

        String queueString = "";
        if (patientQueue.getPatientsAsList().isEmpty()) {
            queueString += "empty_";
        } else {
            for (Patient patient : patientQueue.getPatientsAsList()) {
                queueString += patient.toNameAndIc();
                queueString += "_";
            }
        }
        queueString = queueString.substring(0, queueString.length() - 1);

        String currentString = currentPatient.toNameAndIc();

        String servedString = "";
        if (servedPatientList.getPatientsAsList().isEmpty()) {
            servedString += "empty_";
        } else {
            for (ServedPatient patient : servedPatientList.getPatientsAsList()) {
                servedString += patient.toNameAndIc();
                servedString += "_";
            }
        }
        servedString = servedString.substring(0, servedString.length() - 1);

        script += queueString;
        script += "', '";
        script += currentString;
        script += "', '";
        script += servedString;
        script += "');";

        return script;
    }

    /**
     * Convert any list into a String with commas.
     * @param list list to convert
     * @param <T> Generic method
     * @return converted list as String
     */
    private <T> String convertListToString(List<T> list) {
        String result = "";
        for (T item: list) {
            result += item.toString();
            result += ", ";
        }
        return result.length() >= 2 ? result.substring(0, result.length() - 2) : "none";
    }

    /**
     * Converts provided list of notes to a readable string.
     * @param notes list of notes provided
     * @return readable string
     */
    private String convertNotesToString(List<Note> notes) {
        String result = "";
        for (Note note: notes) {
            result += "- ";
            result += note.toString();
            result += "<br>";
        }
        return result.length() >= 2 ? result.substring(0, result.length() - 4) : "none";
    }
}
