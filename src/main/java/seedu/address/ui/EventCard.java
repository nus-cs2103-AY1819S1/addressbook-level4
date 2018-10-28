package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a single {@code Event}.
 */
public class EventCard extends UiPart<Region> {
    private static final String FXML = "EventCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label name;
    @FXML
    private Label address;
    @FXML
    private Label description;

    @FXML
    private FlowPane contacts;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        assert event != null;

        this.event = event;
        id.setText("[" + displayedIndex + "] ");
        // use time representation with colon from LocalTime
        startTime.setText(event.getEventStartTime().eventTime.toString());
        endTime.setText(event.getEventEndTime().eventTime.toString());
        name.setText(event.getEventName().eventName);
        address.setText(event.getEventAddress().eventAddress);
        description.setText(event.getEventDescription().eventDescription);

        for (Person contact : event.getEventContacts()) {
            Label contactLabel = new Label(contact.getName().fullName);
            contactLabel.setUserData(contact);

            String contactDisplayText = contact.getName() + "\n"
                    + contact.getPhone() + "\n"
                    + contact.getEmail() + "\n"
                    + contact.getAddress();

            Tooltip contactDisplay = new Tooltip(contactDisplayText);
            contactDisplay.setShowDelay(Duration.ONE);
            contactDisplay.setShowDuration(Duration.INDEFINITE);
            contactDisplay.setHideDelay(Duration.ONE);
            contactLabel.setTooltip(contactDisplay);

            contacts.getChildren().add(contactLabel);
        }
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
