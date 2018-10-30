package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of an {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label eventLocation;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().fullName);
        eventLocation.setText(event.getLocation().value);
        startDate.setText(DateTimeUtil.getFriendlyDateFromEventDate(event.getStartDate()));

        if (!event.getStartDate().equals(event.getEndDate())) {
            endDate.setText(" - " + DateTimeUtil.getFriendlyDateFromEventDate(event.getEndDate()));
        } else {
            endDate.setText("");
        }

        String friendlyStartTime = DateTimeUtil.getFriendlyTimeFromEventTime(event.getStartTime());
        String friendlyEndTime = DateTimeUtil.getFriendlyTimeFromEventTime(event.getEndTime());

        startTime.setText(friendlyStartTime);
        if (!friendlyStartTime.equals(friendlyEndTime)) {
            endTime.setText(" - " + friendlyEndTime);
        } else {
            endTime.setText("");
        }

        int status = DateTimeUtil.getEventStatus(event.getStartDate(), event.getStartTime(),
                                                                        event.getEndDate(), event.getEndTime());
        if (status != DateTimeUtil.INVALID_STATUS) {
            Label statusLabel = new Label(DateTimeUtil.STATUS[status]);
            statusLabel.getStyleClass().add(DateTimeUtil.STATUS[status]);
            tags.getChildren().add(statusLabel);
        }

        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
