package seedu.address.ui;

import java.util.Iterator;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * The Information Panel of the App.
 */
public class InformationPanel extends UiPart<Region> {
    private static final String FXML = "InformationPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Patient patient;

    @FXML
    private ListView medicalHistoryPane;

    @FXML
    private TableView<Appointment> upcomingAppointmentTable;

    @FXML
    private TableView<Appointment> pastAppointmentTable;

    public InformationPanel() {
        super(FXML);
        this.patient = null;
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Appointment> upcomingAppointmentList,
                                ObservableList<Appointment> pastAppointmentList) {
        // TODO - set medicalHistory in medicalHistoryPane
        upcomingAppointmentTable.getItems().clear();
        pastAppointmentTable.getItems().clear();
        upcomingAppointmentTable.getItems().addAll(upcomingAppointmentList);
        pastAppointmentTable.getItems().addAll(pastAppointmentList);
    }

    /**
     * Loads a patient's information on the panel.
     */
    private void loadPatientInformation(Patient patient) {
        Iterator<Appointment> upcomingAppointmentItr = patient.getUpcomingAppointments().iterator();
        Iterator<Appointment> pastAppointmentItr = patient.getPastAppointments().iterator();
        ObservableList<Appointment> upcomingAppointmentList = FXCollections.observableArrayList();
        ObservableList<Appointment> pastAppointmentList = FXCollections.observableArrayList();
        while (upcomingAppointmentItr.hasNext()) {
            upcomingAppointmentList.add(upcomingAppointmentItr.next());
        }
        while (pastAppointmentItr.hasNext()) {
            pastAppointmentList.add(pastAppointmentItr.next());
        }
        setConnections(upcomingAppointmentList, pastAppointmentList);
    }

    @Subscribe
    private void handleInformationPanelChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection() instanceof Patient) {
            loadPatientInformation((Patient) (event.getNewSelection()));
        }
    }
}
