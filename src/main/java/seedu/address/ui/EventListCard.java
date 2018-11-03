package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a list of {@code Event} for a particular {@code EventDate}.
 */
public class EventListCard extends UiPart<Region> {
    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final List<Event> eventList;

    @FXML
    private HBox cardPane;
    @FXML
    private Label day;
    @FXML
    private Label date;
    @FXML
    private VBox events;

    public EventListCard(List<Event> eventList, List<Person> personList) {
        super(FXML);
        assert !eventList.isEmpty();

        this.eventList = eventList;
        Event firstEvent = eventList.get(0);

        day.setText(firstEvent.getEventDay().toString());
        date.setText(firstEvent.getEventDate().toString());
        for (int eventIdx = 0; eventIdx < eventList.size(); eventIdx++) {
            if (eventList.get(eventIdx) != null) {
                events.getChildren().add(
                        new EventCard(eventList.get(eventIdx), eventIdx + 1, personList)
                        .getRoot());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventListCard)) {
            return false;
        }

        // state check
        EventListCard card = (EventListCard) other;
        return eventList.equals(card.eventList);
    }
}
