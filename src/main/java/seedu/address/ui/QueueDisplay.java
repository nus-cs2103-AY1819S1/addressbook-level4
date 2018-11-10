package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    private WebViewScript webViewScript;

    @FXML
    private WebView display;

    public QueueDisplay() {
        super(FXML);

        // Disable right-click
        this.display.setContextMenuEnabled(false);

        // Init web view script runner
        this.webViewScript = new WebViewScriptManager(this.display);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadQueueDisplay(null, null, null);
        registerAsAnEventHandler(this);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> display.getEngine().load(url));
    }

    /**
     * Loads a HTML file representing the queue display.
     */
    private void loadQueueDisplay(PatientQueue patientQueue, ServedPatientList servedPatientList,
                                  CurrentPatient currentPatient) {
        List<Patient> patientQueueList = patientQueue == null ? null : patientQueue.getPatientsAsList();
        String currentPatientString;
        if (currentPatient == null) {
            currentPatientString = "empty";
        } else {
            try {
                currentPatientString = currentPatient.getPatient().getName().fullName;
            } catch (NullPointerException npe) {
                currentPatientString = "empty";
            }
        }
        List<ServedPatient> servedPatients = servedPatientList == null ? null : servedPatientList.getPatientsAsList();

        String queueDisplayPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE).toExternalForm();
        this.webViewScript.runScript(getScriptForQueueDisplay(patientQueueList, currentPatientString, servedPatients));
        loadPage(queueDisplayPage);
    }

    /**
     * Gets the javascript code for QueueDisplay.html
     */
    private String getScriptForQueueDisplay(List<Patient> patientQueueList, String currentPatientString,
                                            List<ServedPatient> servedPatientList) {
        List<String> patientQueueIcList = patientQueueList == null ? null : patientQueueList
                .stream()
                .map(patient -> patient.getIcNumber().value.substring(5, 9))
                .collect(Collectors.toList());

        List<String> servedPatientIcList = servedPatientList == null ? null : servedPatientList
                .stream()
                .map(servedPatient -> servedPatient.getIcNumber().value.substring(5, 9))
                .collect(Collectors.toList());

        String script = "loadQueueDisplay(";
        script += convertListToScriptArgs(patientQueueIcList);
        script += ", '";
        script += currentPatientString;
        script += "', ";
        script += convertListToScriptArgs(servedPatientIcList);
        script += ");";

        return script;
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
     * Converts a list of strings into javascript function args.
     */
    private String convertListToScriptArgs(List<String> list) {
        String script = "";
        for (int index = 0; index < 6; index++) {
            try {
                script += "'";
                script += list.get(index);
            } catch (IndexOutOfBoundsException ioobe) {
                script += "empty";
            } catch (NullPointerException npe) {
                script += "empty";
            } finally {
                script += "', ";
            }
        }

        assert(script.length() >= 2);

        return script.substring(0, script.length() - 2);
    }
}
