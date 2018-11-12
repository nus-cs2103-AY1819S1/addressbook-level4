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
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "Volunteers list contains duplicate volunteer(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";
    public static final String MESSAGE_DUPLICATE_RECORD = "Record list contains duplicate record(s).";

    @XmlElement
    private List<XmlAdaptedVolunteer> volunteers;
    @XmlElement
    private List<XmlAdaptedEvent> events;
    @XmlElement
    private List<XmlAdaptedRecord> records;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        volunteers = new ArrayList<>();
        events = new ArrayList<>();
        records = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        volunteers.addAll(src.getVolunteerList().stream().map(XmlAdaptedVolunteer::new).collect(Collectors.toList()));
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
        records.addAll(src.getRecordList().stream().map(XmlAdaptedRecord::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates
     *                               in the {@code XmlAdaptedPerson} or {@code XmlAdaptedRecord}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedVolunteer v : volunteers) {
            Volunteer volunteer = v.toModelType();
            if (addressBook.hasVolunteer(volunteer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VOLUNTEER);
            }
            addressBook.addVolunteer(volunteer);
        }
        for (XmlAdaptedEvent e : events) {
            Event event = e.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }
        for (XmlAdaptedRecord r : records) {
            Record record = r.toModelType();
            if (addressBook.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            addressBook.addRecord(record);
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
        return volunteers.equals(((XmlSerializableAddressBook) other).volunteers)
                && events.equals(((XmlSerializableAddressBook) other).events)
                && records.equals(((XmlSerializableAddressBook) other).records);
    }
}
