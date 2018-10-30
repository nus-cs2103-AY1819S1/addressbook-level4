package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordContainsEventIdPredicate;

/**
 * Panel containing the event details.
 */
public class EventPanel extends UiPart<Region> {
    private static final String FXML = "EventPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label numOfVolunteersLabel;
    @FXML
    private Label eventLocationLabel;
    @FXML
    private Label eventStartDateLabel;
    @FXML
    private Label eventEndDateLabel;
    @FXML
    private Label eventStartTimeLabel;
    @FXML
    private Label eventEndTimeLabel;
    @FXML
    private Label eventDescriptionLabel;
    @FXML
    private FlowPane tags;

    private ObservableList<Record> recordList;

    public EventPanel(ObservableList<Record> recordList) {
        super(FXML);
        this.recordList = recordList;
        registerAsAnEventHandler(this);
    }

    private void setLabelText(Event event) {
        eventNameLabel.setText(event.getName().fullName);
        eventLocationLabel.setText(event.getLocation().value);
        eventStartDateLabel.setText(DateTimeUtil.getFriendlyDateFromEventDate(event.getStartDate()));

        if (!event.getStartDate().equals(event.getEndDate())) {
            eventEndDateLabel.setText(" - " + DateTimeUtil.getFriendlyDateFromEventDate(event.getEndDate()));
        } else {
            eventEndDateLabel.setText("");
        }

        String friendlyStartTime = DateTimeUtil.getFriendlyTimeFromEventTime(event.getStartTime());
        String friendlyEndTime = DateTimeUtil.getFriendlyTimeFromEventTime(event.getEndTime());

        eventStartTimeLabel.setText(friendlyStartTime);
        if (!friendlyStartTime.equals(friendlyEndTime)) {
            eventEndTimeLabel.setText(" - " + friendlyEndTime);
        } else {
            eventEndTimeLabel.setText("");
        }

        numOfVolunteersLabel.setText("Total Number of Volunteers: "
                + String.valueOf(recordList.filtered(new RecordContainsEventIdPredicate(event.getEventId())).size()));
        eventDescriptionLabel.setText(event.getDescription().description);

        tags.getChildren().clear();

        int status = DateTimeUtil.getEventStatus(event.getStartDate(), event.getStartTime(),
                event.getEndDate(), event.getEndTime());
        if (status != DateTimeUtil.INVALID_STATUS) {
            Label statusLabel = new Label(DateTimeUtil.STATUS[status]);
            statusLabel.getStyleClass().add(DateTimeUtil.STATUS[status]);
            tags.getChildren().add(statusLabel);
        }

        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setLabelText(event.getNewSelection());
    }

    /**
     * Ckears details in the event panel.
     */
    public void clearDetails() {
        eventNameLabel.setText("");
        numOfVolunteersLabel.setText("");
        eventLocationLabel.setText("");
        eventStartDateLabel.setText("");
        eventEndDateLabel.setText("");
        eventStartTimeLabel.setText("");
        eventEndTimeLabel.setText("");
        eventDescriptionLabel.setText("");

        tags.getChildren().clear();
    }

}
