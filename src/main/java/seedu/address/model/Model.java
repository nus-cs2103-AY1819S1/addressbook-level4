package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Volunteer> PREDICATE_SHOW_ALL_VOLUNTEERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    //===========  Context Switching Methods =============================================================

    /**
     * Sets the context
     */
    void setCurrentContext(String contextId);

    /***
     * Switches to record context
     */
    void switchToRecordContext();

    /***
     * Returns the context Id
     */
    String getContextId();

    /**
     * Returns context name
     */
    String getContextName();

    /**
     * Sets the currently selected event
     */
    void setSelectedEvent(Event eventId);

    /**
     * Returns the event of the selected event.
     */
    Event getSelectedEvent();

    //===========  Volunteer Methods =============================================================

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the address book.
     */
    boolean hasVolunteer(Volunteer volunteer);

    /**
     * Deletes the given volunteer.
     * The volunteer must exist in the address book.
     */
    void deleteVolunteer(Volunteer target);

    /**
     * Adds the given volunteer.
     * {@code volunteer} must not already exist in the address book.
     */
    void addVolunteer(Volunteer volunteer);

    /**
     * Replaces the given volunteer {@code target} with {@code editedVolunteer}.
     * {@code target} must exist in the address book.
     * The volunteer identity of {@code editedVolunteer} must not be the same as another existing volunteer in
     * the address book.
     */
    void updateVolunteer(Volunteer target, Volunteer editedVolunteer);

    /**
     * Returns an unmodifiable view of the filtered volunteer list
     */
    ObservableList<Volunteer> getFilteredVolunteerList();

    /**
     * Updates the filter of the filtered volunteer list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVolunteerList(Predicate<Volunteer> predicate);

    //===========  Event Methods =============================================================

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the address book.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    void updateEvent(Event target, Event editedEvent);

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    //===========  Record Methods =============================================================

    /**
     * Returns true if a record with the same identity as {@code record} exists in the database.
     */
    boolean hasRecord(Record record);

    /**
     * Deletes the given record.
     * The record must exist in the database.
     */
    void deleteRecord(Record target);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the database.
     */
    void addRecord(Record record);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the database.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the database.
     */
    void updateRecord(Record target, Record editedRecord);

    /**
     * Returns an unmodifiable view of the filtered record list
     */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    //===========  Undo/Redo Methods =============================================================
    /**
     * Restore the model's address book to the initial state.
     */
    void resetStatePointer();

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
}
