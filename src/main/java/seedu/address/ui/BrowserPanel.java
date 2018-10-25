package seedu.address.ui;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowCurrentPatientViewEvent;
import seedu.address.commons.events.ui.ShowQueueInformationEvent;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";
    private static final String FXML = "BrowserPanel.fxml";

    private static int counter = 0;

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * This function runs the executes some javascript in the html file.
     * @param script script to run
     * @param scriptCounter Ensure that only the script called is ran using an index counter.
     */
    private void runScript(String script, int scriptCounter) {
        browser.getEngine().getLoadWorker().stateProperty().addListener((
                ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue != Worker.State.SUCCEEDED) {
                // Browser not loaded, return.
                return;
            }
            if (counter != scriptCounter) {
                return;
            }
            System.out.println("Script to be run: " + script);
            Platform.runLater(() -> browser.getEngine().executeScript(script));
            counter++;
        });
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
        String filePath = "/view/PatientView.html";
        String url = MainApp.class.getResource(filePath).toExternalForm();
        runScript(getScriptForPatientView(patient), counter);
        loadPage(url);
    }

    /**
     * Loads a HTML file that displays the queue information
     */
    private void loadQueueInfomationPage(PatientQueue patientQueue, CurrentPatient currentPatient,
                                         ServedPatientList servedPatientList) {
        String filePath = "/view/QueueInformation.html";
        String url = MainApp.class.getResource(filePath).toExternalForm();
        runScript(getScriptForQueueInformation(patientQueue, currentPatient, servedPatientList), counter);
        loadPage(url);
    }

    /**
     * Loads a HTML file that displays the current patient's information.
     */
    private void loadCurrentPatientPage(CurrentPatient currentPatient) {
        String filePath = "/view/CurrentPatientView.html";
        String url = MainApp.class.getResource(filePath).toExternalForm();
        runScript(getScriptForCurrentPatientView(currentPatient), counter);
        loadPage(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
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
        script += convertListToString(patient.getMedicalRecord().getNotes());
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
        script += currentPatient.getPatient().getAddress().value;
        script += "', '";
        script += currentPatient.getPatient().getPhone().value;
        script += "', '";
        script += currentPatient.getPatient().getEmail().value;
        script += "', '";
        script += currentPatient.getPatient().getMedicalRecord().getBloodType().value;
        script += "', '";
        script += convertListToString(currentPatient.getPatient().getMedicalRecord().getDiseaseHistory());
        script += "', '";
        script += convertListToString(currentPatient.getPatient().getMedicalRecord().getDrugAllergies());
        script += "', '";
        script += convertListToString(currentPatient.getPatient().getMedicalRecord().getNotes());
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
}
