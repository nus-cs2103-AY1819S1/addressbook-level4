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

    /**
     * Loads the page for given patient
     * @param patient patient to load
     */
    private void loadPersonPage(Patient patient) {
        // loadPage(SEARCH_PAGE_URL + patient.getName().fullName);
        String filePath = "/view/PatientView.html";
        String url = MainApp.class.getResource(filePath).toExternalForm();
        url = addPatientDetailsAsArgs(patient, url);
        loadPage(url);
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
     * Loads a HTML file that displays the queue information
     */
    private void loadQueueInfomationPage(PatientQueue patientQueue, CurrentPatient currentPatient,
                                         ServedPatientList servedPatientList) {
        String filePath = "/view/QueueInformation.html";
        String url = MainApp.class.getResource(filePath).toExternalForm();
        url = addQueueDetailsAsArgs(url, patientQueue, currentPatient, servedPatientList);
        loadPage(url);
    }

    /**
     * Loads a HTML file that displays the current patient's information.
     */
    private void loadCurrentPatientPage(CurrentPatient currentPatient) {
        String filePath = "/view/CurrentPatientView.html";
        String url = MainApp.class.getResource(filePath).toExternalForm();
        url = addCurrentPatientDetailsAsArgs(url, currentPatient);
        loadPage(url);
        System.out.println(url);
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
     * Parses CurrentPatient's details and adds to url as parameters
     * @param url filepath
     * @param currentPatient currentpatient to parse
     * @return Final url with args
     */
    private String addCurrentPatientDetailsAsArgs(String url, CurrentPatient currentPatient) {

        url = addPatientDetailsAsArgs(currentPatient.getPatient(), url);

        url += "&noteContent=";
        url += currentPatient.getNoteContent();

        url += "&mcContent=";
        url += currentPatient.getMcContent();

        url += "&referralContent=";
        url += currentPatient.getReferralContent();

        url += "&allocatedMedicine=";
        if (currentPatient.getMedicineAllocated().isEmpty()) {
            url += "<br>";
        } else {
            for (Map.Entry<Medicine, QuantityToDispense> entry : currentPatient.getMedicineAllocated().entrySet()) {
                url += entry.getKey().getMedicineName().fullName;
                url += " x ";
                url += entry.getValue().getValue();
                url += "<br>";
            }
        }

        url = url.substring(0, url.length() - 4);

        return url;
    }

    /**
     * Parses patient details from patient object and appends to URL as html parameters.
     * @param patient Patient details to parse
     * @param url filepath
     * @return Final url with args
     */
    private String addPatientDetailsAsArgs(Patient patient, String url) {

        url += "?name=";
        url += patient.getName().fullName;

        url += "&ic=";
        url += patient.getIcNumber().value;

        url += "&address=";
        url += patient.getAddress().value;

        url += "&phone=";
        url += patient.getPhone().value;

        url += "&email=";
        url += patient.getEmail().value;

        url += "&blood=";
        String bloodTypeString = patient.getMedicalRecord().getBloodType().value.equals("") ? "not taken"
                : patient.getMedicalRecord().getBloodType().value;
        url += bloodTypeString;

        url += "&diseases=";
        url += convertListToString(patient.getMedicalRecord().getDiseaseHistory());

        url += "&drugs=";
        url += convertListToString(patient.getMedicalRecord().getDrugAllergies());

        url += "&notes=";
        url += convertListToString(patient.getMedicalRecord().getNotes());

        return url;
    }

    /**
     * Parses object arguments and appends them to given url as parameters
     * @param url given url string.
     * @param patientQueue given patient queue object.
     * @param currentPatient given current patient object.
     * @param servedPatientList given served pateint list object.
     * @return complete url string.
     */
    private String addQueueDetailsAsArgs(String url, PatientQueue patientQueue,
                                         CurrentPatient currentPatient, ServedPatientList servedPatientList) {
        url += "?queue=";
        if (patientQueue.getPatientsAsList().isEmpty()) {
            url += "empty_";
        } else {
            for (Patient patient : patientQueue.getPatientsAsList()) {
                url += patient.toNameAndIc();
                url += "_";
            }
        }

        url = url.substring(0, url.length() - 1);

        url += "&current=";
        url += currentPatient.toNameAndIc();

        url += "&served=";
        if (servedPatientList.getPatientsAsList().isEmpty()) {
            url += "empty_";
        } else {
            for (ServedPatient patient : servedPatientList.getPatientsAsList()) {
                url += patient.toNameAndIc();
                url += "_";
            }
        }

        url = url.substring(0, url.length() - 1);

        return url;

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
