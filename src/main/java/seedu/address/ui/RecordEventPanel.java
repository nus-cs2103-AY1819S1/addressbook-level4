package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;

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

    private List<Person> volunteerList;

    public RecordEventPanel(ObservableList<Record> recordList) {
        super(FXML);
        setConnections(recordList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Record> recordList) {

    }


    @Subscribe
    private void handleRecordChangeEvent(RecordChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventNameLabel.setText(event.getCurrentEvent().getName().fullName);
        numOfVolunteersLabel.setText("0");
    }
}
