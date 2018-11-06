package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueEventList events;
    private final UniqueTagList eventTags;
    private boolean notificationPref;
    private String favourite;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        events = new UniqueEventList();
        eventTags = new UniqueTagList();
        notificationPref = true;
        favourite = null;
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
<<<<<<< HEAD
=======
     * Replaces the contents of the event tag list with {@code eventTags}.
     * {@code eventTags} must not contain duplicate event tags.
     */
    public void setEventTags(List<Tag> eventTags) {
        this.eventTags.setTags(eventTags);
    }

    /**
     * Updates the notification preference.
     */
    public void setNotificationPref(boolean set) {
        this.notificationPref = set;
    }

    /**
>>>>>>> f86c648336158f70e5a50e9ba57f4361d09d7778
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
        setEventTags(newData.getEventTagList());
        setNotificationPref(newData.getNotificationPref());
        updateFavourite(newData.getFavourite());
    }



    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from the person list in this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Returns true if a clashing event with {@code event} exists in the address book.
     */
    public boolean hasClashingEvent(Event event) {
        requireNonNull(event);
        return events.containsClashingEvent(event);
    }

    /**
     * Removes {@code key} from the events list in this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book, and must not clash any of the existing events in the
     * address book.
     */
    //TODO: decision to allow clashing events? If from xml, goes here directly. If from user, can do additional check to
    // ask the
    // user (by raising an event which brings up a message and prompts user for further input - enter to add anyway
    // or esc to delete) for confirmation before adding.
    public void addEvent(Event event) {
        assert !hasEvent(event);
        assert !hasClashingEvent(event);

        events.add(event);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons"
                + "\n" + events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getEventTagList() {
        return eventTags.asUnmodifiableObservableList();
    }

    @Override
    public boolean getNotificationPref() {
        return notificationPref;
    }

    @Override
    public void updateNotificationPref(boolean set) {
        this.notificationPref = set;
    }

    @Override
    public String getFavourite() {
        return favourite;
    }

    @Override
    public void updateFavourite(String favourite) {
        this.favourite = favourite;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && events.equals(((AddressBook) other).events));
    }

    @Override
    public int hashCode() {
        return persons.hashCode() + events.hashCode();
    }

    public String getFavouriteEvent() { return favourite; }

    public void updateFavouriteEvent(String newEvent) { favourite = newEvent; }

}
