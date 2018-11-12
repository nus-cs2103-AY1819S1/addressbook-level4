package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the volunteers list.
     * This list will not contain any duplicate volunteers.
     */
    ObservableList<Volunteer> getVolunteerList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns an unmodifiable view of the records list.
     * This list will not contain any duplicate records.
     */
    ObservableList<Record> getRecordList();
}
