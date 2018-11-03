package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CLASHING_EVENT_END_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.CLASHING_EVENT_START_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.event.exceptions.EventClashException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ScheduledEventBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getEventList());
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

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);

        List<Event> newEvents = Arrays.asList(DOCTORAPPT);
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsDuplicateEventException() {

        List<Person> newPersons = Arrays.asList(ALICE);

        // Two events with the same identity fields
        Event duplicateEvent =
                new ScheduledEventBuilder(DOCTORAPPT).build();
        List<Event> newEvents = Arrays.asList(DOCTORAPPT, duplicateEvent);
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents);

        thrown.expect(DuplicateEventException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withClashingEvents_throwsDuplicateEventException() {

        List<Person> newPersons = Arrays.asList(ALICE);

        // Two events with the same identity fields
        Event clashingEvent =
                new ScheduledEventBuilder(DOCTORAPPT)
                        .withEventStartTime(CLASHING_EVENT_START_TIME_DOCTORAPPT)
                        .withEventEndTime(CLASHING_EVENT_END_TIME_DOCTORAPPT)
                        .build();
        List<Event> newEvents = Arrays.asList(DOCTORAPPT, clashingEvent);
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents);

        thrown.expect(EventClashException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasEvent(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEvent(DOCTORAPPT));
    }

    @Test
    public void hasClashingEvent_clashingEventNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasClashingEvent(DOCTORAPPT));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        addressBook.addEvent(DOCTORAPPT);
        assertTrue(addressBook.hasEvent(DOCTORAPPT));
    }

    @Test
    public void hasClashingEvent_clashingEventInAddressBook_returnsTrue() {
        addressBook.addEvent(DOCTORAPPT);
        Event clashingEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventStartTime(CLASHING_EVENT_START_TIME_DOCTORAPPT)
                .withEventEndTime(CLASHING_EVENT_END_TIME_DOCTORAPPT)
                .build();
        assertTrue(addressBook.hasClashingEvent(clashingEvent));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasEvent_eventWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEvent(DOCTORAPPT);
        Event duplicateEvent = new ScheduledEventBuilder(DOCTORAPPT).build();
        assertTrue(addressBook.hasEvent(duplicateEvent));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().add(new ScheduledEventBuilder().build());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list and events list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final boolean notificationPref = true;
        private final String favourite = null;

        AddressBookStub(Collection<Person> persons, Collection<Event> events) {
            this.persons.setAll(persons);
            this.events.setAll(events);
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
        public boolean getNotificationPref() {
            return notificationPref;
        };

        @Override
        public String getFavouriteEvent() {
            return favourite;
        };
    }

}
