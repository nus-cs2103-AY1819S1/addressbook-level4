package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";
    public static final String MESSAGE_CLASHING_EVENT = "Events list contains clashing event(s).";
    public static final String MESSAGE_DUPLICATE_TAG = "Event tags list contains duplicate event tag(s).";
    public static final String MESSAGE_NONEXISTENT_TAG = "One or more event tag(s) does not exist in the address book.";

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<XmlAdaptedTag> eventTags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        events = new ArrayList<>();
        eventTags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
        eventTags.addAll(src.getEventTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or in the {@code XmlAdaptedEvent}
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (XmlAdaptedTag t : eventTags) {
            Tag tag = t.toModelType();
            if (addressBook.hasEventTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            addressBook.addEventTag(tag);
        }

        for (XmlAdaptedEvent e : events) {
            Event event = e.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            } else if (addressBook.hasClashingEvent(event)) {
                throw new IllegalValueException(MESSAGE_CLASHING_EVENT);
            } else if (!event.getEventTags().stream().allMatch(addressBook::hasEventTag)) {
                throw new IllegalValueException(MESSAGE_NONEXISTENT_TAG);
            }
            addressBook.addEvent(event);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableAddressBook) other).persons)
                && events.equals(((XmlSerializableAddressBook) other).events)
                && eventTags.equals(((XmlSerializableAddressBook) other).eventTags);
    }
}
