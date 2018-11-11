package seedu.clinicio.ui;

import com.google.common.eventbus.Subscribe;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;

/**
 * A ui for display patient details.
 */
public class PatientDetailsDisplayPanel extends UiPart<Region>  {

    private static final String FXML = "PatientDetailsDisplayPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label nameLabel;
    
    public PatientDetailsDisplayPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    public void setName(String name) {
        Platform.runLater(() -> nameLabel.setText(name));
    }
    
    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setName(event.getNewSelection().getName().toString());
    }
}
