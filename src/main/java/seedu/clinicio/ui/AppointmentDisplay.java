package seedu.clinicio.ui;

//@@author gingivitiss

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.AppointmentPanelSelectionChangedEvent;

/**
 * Ui to display appointment details.
 */
public class AppointmentDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "AppointmentDisplay.fxml";

    @FXML
    private VBox appointmentBox;
    @FXML
    private Label header;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label name;
    @FXML
    private Label ic;
    @FXML
    private Label status;
    @FXML
    private Label type;
    @FXML
    private Label staff;

    public AppointmentDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
        appointmentBox.setVisible(false);
    }

    public void setVisible(boolean isVisible) {
        appointmentBox.setVisible(isVisible);
    }

    public void setAllText(AppointmentPanelSelectionChangedEvent event) {

        header.setText("Appointment Details");
        date.setText(event.getNewSelection().getAppointmentDate().toString());
        time.setText(event.getNewSelection().getAppointmentTime().toString());
        name.setText("Name: " + event.getNewSelection().getPatient().getName().toString());
        ic.setText("IC: " + event.getNewSelection().getPatient().getNric().toString());
        status.setText(event.getNewSelection().statusToString());
        type.setText(event.getNewSelection().typeToString());

        if (!event.getNewSelection().getAssignedStaff().isPresent()) {
            staff.setText("Doctor: None assigned");
        } else {
            staff.setText("Doctor: " + event.getNewSelection().getAssignedStaff().get().toString());
        }
    }

    @Subscribe
    private void handleAppointmentPanelSelectionEvent(AppointmentPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setAllText(event);
        appointmentBox.setVisible(true);
    }

}
