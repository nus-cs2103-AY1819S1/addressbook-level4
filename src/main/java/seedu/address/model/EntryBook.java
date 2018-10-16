package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.entry.UniqueEntryList;

/**
 * Wraps all data at the entry-book level
 * Duplicates are not allowed (by .isSameEntry comparison)
 */
public class EntryBook implements ReadOnlyEntryBook {

    private final UniqueEntryList entries;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        entries = new UniqueEntryList();
    }

    public EntryBook() {}

    /**
     * Creates an EntryBook using the Entries in the {@code toBeCopied}
     */
    public EntryBook(ReadOnlyEntryBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the entry list with {@code entries}.
     * {@code entries} must not contain duplicate persons.
     */
    public void setEntries(List<ResumeEntry> entries) {
        this.entries.setEntries(entries);
    }

    /**
     * Resets the existing data of this {@code EntryBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEntryBook newData) {
        requireNonNull(newData);

        setEntries(newData.getEntryList());
    }

    //// person-level operations

    /**
     * Returns true if an entry with the same identity as {@code entry} exists in the entry book.
     */
    public boolean hasEntry(ResumeEntry entry) {
        requireNonNull(entry);
        return entries.contains(entry);
    }

    /**
     * Adds an entry to the address book.
     * The entry must not already exist in the entry book.
     */
    public void addEnty(ResumeEntry e) {
        entries.add(e);
    }

    /**
     * Replaces the given entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the address book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the entry book.
     */
    public void updateEntry(ResumeEntry target, ResumeEntry editedEntry) {
        requireNonNull(editedEntry);

        entries.setEntry(target, editedEntry);
    }

    /**
     * Removes {@code key} from this {@code EntryBook}.
     * {@code key} must exist in the Entry book.
     */
    public void removeEntry(ResumeEntry key) {
        entries.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return entries.asUnmodifiableObservableList().size() + " entries";
        // TODO: refine later
    }

    @Override
    public ObservableList<ResumeEntry> getEntryList() {
        return entries.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EntryBook // instanceof handles nulls
                && entries.equals(((EntryBook) other).entries));
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }
}
