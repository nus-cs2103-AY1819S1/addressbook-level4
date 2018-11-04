package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OverviewPanelChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Panel containing the statistics overview.
 */
public class OverviewPanel extends UiPart<Region> {
    private static final String FXML = "OverviewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label upcomingLabel;
    @FXML
    private Label ongoingLabel;
    @FXML
    private Label completedLabel;

    private ObservableList<Volunteer> volunteerList;
    private ObservableList<Event> eventList;
    private ObservableList<Record> recordList;


    public OverviewPanel(ObservableList<Volunteer> volunteerList, ObservableList<Event> eventList,
                                                                    ObservableList<Record> recordList) {
        super(FXML);
        this.volunteerList = volunteerList;
        this.eventList = eventList;
        this.recordList = recordList;
        registerAsAnEventHandler(this);
    }

    private void setLabelText() {
        upcomingLabel.setText("Number of events: " + Integer.toString(eventList.size()));
    }

    @Subscribe
    private void handleOverviewPanelSelectionChangedEvent(OverviewPanelChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setLabelText();
    }
}
