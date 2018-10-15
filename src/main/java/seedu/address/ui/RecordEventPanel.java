package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.model.event.Event;

/**
 * Panel containing the list of persons.
 */
public class RecordEventPanel extends UiPart<Region> {
    private static final String FXML = "RecordEventPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordEventPanel.class);

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label numOfVolunteersLabel;
    @FXML
    private TableView volunteerRecordTableView;

    public RecordEventPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleRecordChangeEvent(RecordChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventNameLabel.setText("EVENT!");
        numOfVolunteersLabel.setText("0");
    }

}
