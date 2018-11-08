package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookEventTagChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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

    private static final String PERSON_DELETED_MESSAGE = "This person no longer exists in the addressbook.";
    public final Event event;
    private final List<Person> personList;

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
    @FXML
    private FlowPane eventTags;

    public EventCard(Event event, int displayedIndex, List<Person> personList) {
        super(FXML);
        assert event != null;

        this.event = event;
        this.personList = personList;

        setEventCardAttributes(event, displayedIndex);
        registerAsAnEventHandler(this);

        for (Person contact : event.getEventContacts()) {
            Label contactLabel = new Label(contact.getName().fullName);

            setLabelUserData(contact, contactLabel);
            setToolTip(contact, contactLabel);

            contacts.getChildren().add(contactLabel);
        }
    }

    private void setLabelUserData(Person contact, Label contactLabel) {
        // never changed, to retain data integrity since Events are designed to be immutable
        contactLabel.setUserData(contact);
    }

    private void setToolTip(Person contact, Label contactLabel) {
        String contactDisplayText = getTooltipDisplayText(contact);

        Tooltip contactDisplayTooltip = new Tooltip(contactDisplayText);
        setToolTipPreferences(contactDisplayTooltip);
        contactLabel.setTooltip(contactDisplayTooltip);
    }

    private String getTooltipDisplayText(Person contact) {
        List<Person> existingMatchingContacts = personList.stream()
                .filter(person -> person.isSamePerson(contact))
                .collect(Collectors.toList());

        if (existingMatchingContacts.isEmpty()) {
            // person no longer exists in the person list
            return PERSON_DELETED_MESSAGE;
        }

        // there should only be one matching contact in the address book person list
        assert existingMatchingContacts.size() == 1;
        // use the latest information for matching person in address book
        return getContactDisplayText(existingMatchingContacts.get(0));
    }

    private void setToolTipPreferences(Tooltip contactDisplay) {
        contactDisplay.setShowDelay(Duration.ONE);
        contactDisplay.setShowDuration(Duration.INDEFINITE);
        contactDisplay.setHideDelay(Duration.ONE);
    }

    private void setEventCardAttributes(Event event, int displayedIndex) {
        id.setText("[" + displayedIndex + "] ");
        // use time representation with colon from LocalTime
        startTime.setText(event.getEventStartTime().eventTime.toString());
        endTime.setText(event.getEventEndTime().eventTime.toString());
        name.setText(event.getEventName().eventName);
        address.setText(event.getEventAddress().eventAddress);
        description.setText(event.getEventDescription().eventDescription);
        event.getEventTags().forEach(eventTag -> eventTags.getChildren()
                .add(new Label(eventTag.getLowerCaseTagName())));
    }

    private String getContactDisplayText(Person contact) {
        return contact.getName() + "\n"
                        + contact.getPhone() + "\n"
                        + contact.getEmail() + "\n"
                        + contact.getAddress();
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

    /**
     * Updates the event card with updated {@code Person} information when the address book
     * person data changes, if any.
     * @param addressBookChangedEvent
     */
    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent addressBookChangedEvent) {
        for (Node node : contacts.getChildren()) {
            assert node instanceof Label;
            assert node.getUserData() instanceof Person;

            Label contactLabel = (Label) node;
            Person contact = (Person) contactLabel.getUserData();

            String contactDisplayText = getTooltipDisplayText(contact);
            contactLabel.getTooltip().setText(contactDisplayText);
        }
    }

    /**
     * Updates the event card with updated Event {@code Tag} information when the address book
     * event tag data changes, if any. This is required to keep the event tags synced with the
     * updated event tag list in the address book.
     * @param addressBookEventTagChangedEvent
     */
    @Subscribe
    private void handleAddressBookEventTagChangedEvent(AddressBookEventTagChangedEvent
                                                                   addressBookEventTagChangedEvent) {
        List<Tag> updatedEventTagList = addressBookEventTagChangedEvent.data;
        for (Node node : eventTags.getChildren()) {
            assert node instanceof Label;

            Label eventTag = (Label) node;
            // use Tag#isSameTag for comparison
            if (updatedEventTagList.stream().noneMatch(tag -> tag.isSameTag(new Tag(eventTag.getText())))) {
                eventTags.getChildren().remove(eventTag);
            }
        }
    }
}
