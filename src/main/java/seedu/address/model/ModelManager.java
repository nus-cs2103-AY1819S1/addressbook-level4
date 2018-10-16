package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.Comparator;
import java.util.List;
import java.util.Map;
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
<<<<<<< HEAD
import seedu.address.model.event.Event;
=======
import seedu.address.commons.events.model.AddressBookEventChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> filteredEvents;

    private static boolean notificationPref = GuiSettings.getNotificationIsEnabled();

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
        addListenerToBaseEventList();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public static boolean getNotificationPref() { return notificationPref; }

    public static void updateNotificationPref(boolean set) { notificationPref = set; }

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

    /** Raises an event to indicate the events list in the model has changed */
    private void indicateAddressBookEventChanged() {
        raise(new AddressBookEventChangedEvent(filteredEvents));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

<<<<<<< HEAD
    @Override
    public boolean hasEvent(Event event) {
        return false;
=======
    //=========== Event methods ==============================================================================
    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedAddressBook.hasEvent(event);
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
    }

    @Override
    public boolean hasClashingEvent(Event event) {
<<<<<<< HEAD
        return false;
=======
        requireNonNull(event);
        return versionedAddressBook.hasClashingEvent(event);
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
    }

    @Override
    public void addEvent(Event event) {
<<<<<<< HEAD

=======
        versionedAddressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
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
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

<<<<<<< HEAD
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return null;
=======
    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
<<<<<<< HEAD

    }

    @Override
    public ObservableList<List<Event>> getFilteredEventListByDate() {
        return null;
=======
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    /**
     * Adds a listener on the base event list {@code filteredEvents} to detect changes in the base list
     * and indicate the change to the address book.
     */
    public void addListenerToBaseEventList() {
        filteredEvents.addListener((ListChangeListener.Change<? extends Event> change) -> {
            if (change.next()) {
                indicateAddressBookEventChanged();
            }
        });
    }

    /**
     * Returns an unmodifiable view of the list of lists of {@code Event} (grouped by date)  backed by the internal
     * list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<List<Event>> getFilteredEventListByDate() {
        Map<EventDate, List<Event>> filteredEventsByDateMap = filteredEvents.stream()
                .collect(Collectors.groupingBy(Event::getEventDate));

        // convert the map to a FilteredList
        ObservableList<List<Event>> filteredEventsByDateList = FXCollections.observableArrayList();
        filteredEventsByDateList.addAll(filteredEventsByDateMap.values());

        Comparator<List<Event>> eventListComparator =
                Comparator.comparing(eventList -> eventList.get(0).getEventDate());
        filteredEventsByDateList.sort(eventListComparator.reversed());
        FilteredList<List<Event>> filteredEventsByDate = new FilteredList<>(filteredEventsByDateList);

        return FXCollections.unmodifiableObservableList(filteredEventsByDate);
>>>>>>> 2ac1e1ce3fe0b7f8dfbe1e5b1910b9e1a4998c15
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