package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_H2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_R2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRIVER;
import static seedu.address.testutil.TypicalEvents.BLOOD;
import static seedu.address.testutil.TypicalRecords.R1;
import static seedu.address.testutil.TypicalVolunteers.BENSON;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.exceptions.DuplicateVolunteerException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.RecordBuilder;
import seedu.address.testutil.VolunteerBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getVolunteerList());
        assertEquals(Collections.emptyList(), addressBook.getRecordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalVolunteerAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }
    //// Volunteer Tests
    @Test
    public void resetData_withDuplicateVolunteers_throwsDuplicatePersonException() {
        // Two volunteers with the same identity fields
        Volunteer editedBenson = new VolunteerBuilder(BENSON).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_DRIVER).build();
        List<Volunteer> newVolunteers = Arrays.asList(BENSON, editedBenson);
        AddressBookStub newData = new AddressBookStub(newVolunteers, null, null);

        thrown.expect(DuplicateVolunteerException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasVolunteer_nullVolunteer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasVolunteer(null);
    }

    @Test
    public void hasVolunteer_volunteerNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasVolunteer(BENSON));
    }

    @Test
    public void hasVolunteer_volunteerInAddressBook_returnsTrue() {
        addressBook.addVolunteer(BENSON);
        assertTrue(addressBook.hasVolunteer(BENSON));
    }

    @Test
    public void hasVolunteer_volunteerWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addVolunteer(BENSON);
        Volunteer editedBenson = new VolunteerBuilder(BENSON).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_DRIVER).build();
        assertTrue(addressBook.hasVolunteer(editedBenson));
    }

    @Test
    public void getVolunteerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getVolunteerList().remove(0);
    }

    //// Event Tests
    @Test
    public void resetData_withDuplicateEvents_throwsDuplicateEventsException() {
        // Two events with the same identity fields
        Event editedEvent = new EventBuilder(BLOOD).withDescription(VALID_DESCRIPTION_YOUTH).build();
        List<Event> newEvents = Arrays.asList(BLOOD, editedEvent);
        AddressBookStub newData = new AddressBookStub(null, newEvents, null);

        thrown.expect(DuplicateEventException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEvent(BLOOD));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        addressBook.addEvent(BLOOD);
        assertTrue(addressBook.hasEvent(BLOOD));
    }

    @Test
    public void hasEvent_eventWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEvent(BLOOD);
        Event editedEvent = new EventBuilder(BLOOD).withDescription(VALID_DESCRIPTION_YOUTH).build();
        assertTrue(addressBook.hasEvent(editedEvent));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().remove(0);
    }

    //// Record Tests
    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same identity fields
        Record editedRecord = new RecordBuilder(R1).withHour(VALID_HOUR_H2).withRemark(VALID_REMARK_R2)
                .build();
        List<Record> newRecords = Arrays.asList(R1, editedRecord);
        AddressBookStub newData = new AddressBookStub(null, null, newRecords);

        thrown.expect(DuplicateRecordException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasRecord(R1));
    }

    @Test
    public void hasRecord_recordInAddressBook_returnsTrue() {
        addressBook.addRecord(R1);
        assertTrue(addressBook.hasRecord(R1));
    }

    @Test
    public void hasRecord_recordWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addRecord(R1);
        Record editedRecord = new RecordBuilder(R1).withHour(VALID_HOUR_H2).withRemark(VALID_REMARK_R2)
                .build();
        assertTrue(addressBook.hasRecord(editedRecord));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getRecordList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose volunteers list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Volunteer> volunteers = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final ObservableList<Record> records = FXCollections.observableArrayList();

        AddressBookStub(Collection<Volunteer> volunteers, Collection<Event> events,
                        Collection<Record> records) {
            if (volunteers != null) {
                this.volunteers.setAll(volunteers);
            }
            if (events != null) {
                this.events.setAll(events);
            }
            if (records != null) {
                this.records.setAll(records);
            }
        }

        @Override
        public ObservableList<Volunteer> getVolunteerList() { return volunteers; }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return records;
        }
    }
}
