package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.UserLoginStatusChangedEvent;
import seedu.address.logic.commands.exceptions.NoEventSelectedException;
import seedu.address.logic.commands.exceptions.NoUserLoggedInException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.NotEventOrganiserException;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the event organiser data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> filteredEvents;
    private Person currentUser;
    private Event currentEvent;
    private boolean clearIsEnabled;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredEvents = new FilteredList<>(versionedAddressBook.getEventList());
        currentUser = null;
        clearIsEnabled = false;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Sets the current user in the model.
     * @param person the User to be set as the current user.
     */
    public void setCurrentUser(Person person) {
        assert person != null;
        this.currentUser = person;
        indicateUserLoginStatusChanged();
    }

    /**
     * Finds the user within the filteredPersons list(if the user exists) and logs the user.
     * @param person the Person to be authenticated.
     */
    public Person authenticateUser(Person person) {
        for (Person p: filteredPersons) {
            if (person.isSamePerson(p)) {
                p.login();
                return p;
            }
        }
        return person;
    }

    /**
     * Logs out the user and set the current user to null.
     */
    public void removeCurrentUser() {
        currentUser.logout();
        currentUser = null;
        indicateUserLoginStatusChanged();
    }

    public boolean hasSetCurrentUser() {
        return (currentUser != null);
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    /**
     * Enable clear command, only for testing purposes.
     */
    public void setClearEnabled() {
        clearIsEnabled = true;
    }

    public boolean getClearIsEnabled() {
        return clearIsEnabled;
    }

    public void setSelectedEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Event getSelectedEvent() throws NoEventSelectedException {
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        return currentEvent;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate that a user has logged in */
    private void indicateUserLoginStatusChanged() {
        raise(new UserLoginStatusChangedEvent(null));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        if (target.equals(currentUser)) {
            currentUser = null;
        }
        deleteEventsWithUser(target);
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author theJrLinguist
    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        updateUserInEvents(target, editedPerson);
        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    public Person getPerson(Index targetIndex) {
        return filteredPersons.get(targetIndex.getZeroBased());
    }

    //===========Events ======================================================================================
    @Override
    public void addEvent(Event event) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        event.setOrganiser(currentUser);
        event.addParticipant(currentUser);

        assert(!hasEvent(event));
        versionedAddressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteEvent(Event target) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (target.equals(currentEvent)) {
            currentEvent = null;
        }
        if (!target.getOrganiser().equals(currentUser)) {
            throw new NotEventOrganiserException();
        }

        assert(hasEvent(target));
        currentEvent = null;
        versionedAddressBook.removeEvent(target);
        indicateAddressBookChanged();
    }

    @Override
    public void editEvent(Optional<EventName> name, Optional<Address> location, Optional<Set<Tag>> tags) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentEvent.getOrganiser().equals(currentUser)) {
            throw new NotEventOrganiserException();
        }

        Event editedEvent = createEditedEvent(name, location, tags);
        if (hasEvent(editedEvent)) {
            throw new DuplicateEventException();
        }
        updateEvent(currentEvent, editedEvent);
        currentEvent = editedEvent;
        indicateAddressBookChanged();
    }

    /**
     * Creates a new edited event based on the provided edit parameters.
     */
    private Event createEditedEvent(Optional<EventName> name, Optional<Address> location, Optional<Set<Tag>> tags) {
        EventName eventName = name.orElse(currentEvent.getName());
        Address eventLocation = location.orElse(currentEvent.getLocation());
        Set<Tag> eventTags = tags.orElse(currentEvent.getTags());
        Event editedEvent = new Event(eventName, eventLocation, eventTags);

        if (currentEvent.getDate().isPresent()) {
            editedEvent.setDate(currentEvent.getDate().get());
        }
        if (currentEvent.getStartTime().isPresent() && currentEvent.getEndTime().isPresent()) {
            editedEvent.setTime(currentEvent.getStartTime().get(), currentEvent.getEndTime().get());
        }
        editedEvent.setOrganiser(currentEvent.getOrganiser());
        editedEvent.setPolls(currentEvent.getPolls());
        editedEvent.setParticipantList(currentEvent.getParticipantList());
        return editedEvent;
    }

    /**
     * Deletes the events that are organised by a deleted user.
     */
    private void deleteEventsWithUser(Person userToDelete) {
        ArrayList<Event> deletedEvents = new ArrayList<>();
        for (Event event : versionedAddressBook.getEventList()) {
            if (event.getOrganiser().equals(userToDelete)) {
                deletedEvents.add(event);
            }
            event.deletePerson(userToDelete);
        }
        for (Event event : deletedEvents) {
            versionedAddressBook.removeEvent(event);
        }
    }

    /**
     * Updates the Persons in every event's participant list and polls.
     */
    private void updateUserInEvents(Person target, Person editedPerson) {
        for (Event event : versionedAddressBook.getEventList()) {
            boolean changed = event.updatePerson(target, editedPerson);
            if (changed) {
                versionedAddressBook.updateEvent(event, event);
            }
        }
    }

    /**
     * Check if an event already exists in a versionedAddressBook
     * @param event
     * @return true if versionAddressBook already has this event.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedAddressBook.hasEvent(event);
    }

    @Override
    public int getNumEvents() {
        return filteredEvents.size();
    }

    public Event getEvent(Index targetIndex) {
        return filteredEvents.get(targetIndex.getZeroBased());
    }

    @Override
    public String addPoll(String pollName) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        String pollDetails = currentEvent.addPoll(pollName);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public String addTimePoll(LocalDate startDate, LocalDate endDate) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        String pollDetails = currentEvent.addTimePoll(startDate, endDate);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public String addPollOption(Index index, String option) {
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        String pollDetails = currentEvent.addOptionToPoll(index, option);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public String voteOption(Index index, String optionName) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        String pollDetails = currentEvent.addVoteToPoll(index, currentUser, optionName);
        updateEvent(currentEvent, currentEvent);
        return pollDetails;
    }

    @Override
    public void setDate(LocalDate date) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        currentEvent.setDate(date);
        updateEvent(currentEvent, currentEvent);
    }

    @Override
    public void setTime(LocalTime startTime, LocalTime endTime) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        if (currentEvent == null) {
            throw new NoEventSelectedException();
        }
        if (!currentUser.equals(currentEvent.getOrganiser())) {
            throw new NotEventOrganiserException();
        }
        currentEvent.setTime(startTime, endTime);
        updateEvent(currentEvent, currentEvent);
    }


    @Override
    public void joinEvent(Index index) {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        Event event = getEvent(index);
        event.addParticipant(currentUser);
        updateEvent(event, event);
    }

    @Override
    public void updateEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);
        if (currentEvent.equals(target)) {
            currentEvent = editedEvent;
        }

        versionedAddressBook.updateEvent(target, editedEvent);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredEvents.equals(other.filteredEvents);
    }

}
