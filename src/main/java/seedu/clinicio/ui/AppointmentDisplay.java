package seedu.clinicio.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.AppointmentPanelSelectionChangedEvent;

/**
 * A ui to display appointment details.
 */
public class AppointmentDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "AppointmentDisplay.fxml";

    @FXML
    private VBox appointmentBox;
    @FXML
    private Label appointmentDetails;

    public AppointmentDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
        appointmentBox.setVisible(false);
    }

    @Subscribe
    private void handleAppointmentPanelSelectionEvent(AppointmentPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        appointmentDetails.setText(event.getNewSelection().toString());
        appointmentBox.setVisible(true);
    }

}
