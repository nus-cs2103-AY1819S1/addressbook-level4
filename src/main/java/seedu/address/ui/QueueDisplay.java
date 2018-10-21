package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.QueueUpdatedEvent;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.person.CurrentPatient;
import seedu.address.model.person.Patient;
import seedu.address.model.person.ServedPatient;

/**
 * A display panel that shows the queue information to the user.
 */
public class QueueDisplay extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "QueueDisplay.html";

    private static final String FXML = "QueueDisplay.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView display;

    public QueueDisplay() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadQueueDisplay(null, null, null);
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> display.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadQueueDisplay(PatientQueue patientQueue, ServedPatientList servedPatientList,
                                  CurrentPatient currentPatient) {
        String patientQueueString = generatePatientQueuePrettyString(patientQueue);
        String servedPatientListString = generateServedPatientListPrettyString(servedPatientList);

        String currentPatientString = currentPatient != null
                ? currentPatient.toNameAndIc() : "No current patient!";

        String queueDisplayPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE).toExternalForm();
        queueDisplayPage += "?queue=";
        queueDisplayPage += patientQueueString;
        queueDisplayPage += "&served=";
        queueDisplayPage += servedPatientListString;
        queueDisplayPage += "&current=";
        queueDisplayPage += currentPatientString;
        loadPage(queueDisplayPage);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        display = null;
    }

    @Subscribe
    private void handleQueueUpdatedEvent(QueueUpdatedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadQueueDisplay(event.getPatientQueue(), event.getServedPatientList(), event.getCurrentPatient());
    }

    /**
     * Convert queue object to a nice looking readable string.
     * @param patientQueue queue object to convert.
     * @return nice readable string
     */
    private String generatePatientQueuePrettyString(PatientQueue patientQueue) {
        if (patientQueue == null) {
            return "(none)";
        }
        int counter = 1;
        String result = "";
        for (Patient patient: patientQueue.getPatientsAsList()) {
            result += counter++ + ".) ";
            result += patient.toNameAndIc();
            result += "<br>";
        }
        return result == "" ? "(none)" : result.substring(0, result.length() - 4);
    }

    /**
     * Convert patient list object to a nice looking readable string
     * @param servedPatientList list to convert
     * @return nice readable string
     */
    private String generateServedPatientListPrettyString(ServedPatientList servedPatientList) {
        if (servedPatientList == null) {
            return "(none)";
        }

        int counter = 1;
        String result = "";
        for (ServedPatient patient: servedPatientList.getPatientsAsList()) {
            result += counter++ + ".) ";
            result += patient.toNameAndIc();
            result += "<br>";
        }
        return result == "" ? "(none)" : result.substring(0, result.length() - 4);
    }


}
