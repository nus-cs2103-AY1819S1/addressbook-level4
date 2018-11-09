package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.filereader.FileReader;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Returns true if a clashing event with {@code event} exists in the address book.
     */
    boolean hasClashingEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event into the address book.
     * {@code event} must not already exist in the address book and must not clash with any of the existing events in
     * the address book.
     */
    void addEvent(Event event);

    /**
     * Reads contacts info in the given file reader.
     * {@code fileReader} must be have a valid file.
     */
    void importContacts(FileReader fileReader);

    /**
     * Returns true if an event tag with the same identity as {@code eventTag} exists in the address book.
     */
    boolean hasEventTag(Tag eventTag);

    /**
     * Adds the given event tag into the address book.
     * {@code eventTag} must not already exist in the address book.
     */
    void addEventTag(Tag eventTag);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the unfiltered person list */
    ObservableList<Person> getUnfilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /** Returns an unmodifiable view of the filtered list of lists of events (grouped by date) */
    ObservableList<List<Event>> getFilteredEventListByDate();

    /** Returns an unmodifiable view of the unfiltered event tag list */
    ObservableList<Tag> getEventTagList();

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Updates the notification preference.
     */
    void updateNotificationPref(boolean set);

    /**
     * Updates the favourite event.
     */
    void updateFavourite(String favourite);

    void updateFavourite(Event favourite);

    /**
     * Returns notification preference.
     */
    boolean getNotificationPref();

    /**
     * Returns favourite event String.
     */
    String getFavourite();

    /**
     * Checks if the event is the favourite.
     */
    boolean isFavourite(Event event);
}
