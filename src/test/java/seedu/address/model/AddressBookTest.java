package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_YOUTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUR_H2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_R2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEvents.BLOOD;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecords.R1;

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
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.DuplicateRecordException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RecordBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getRecordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    //// Person Tests
    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, null, null);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
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
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final ObservableList<Record> records = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Event> events, Collection<Record> records) {
            if (persons != null) {
                this.persons.setAll(persons);
            }
            if (events != null) {
                this.events.setAll(events);
            }
            if (records != null) {
                this.records.setAll(records);
            }
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

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
