package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.model.transaction.exceptions.DuplicateEntryException;
import seedu.address.model.transaction.exceptions.EntryNotFoundException;

//@@author ericyjw
/**
 * A list of unique Transaction Entries.
 *
 * @author ericyjw
 */
public class UniqueEntryList implements Iterable<Entry> {
    private final ObservableList<Entry> internalEntryList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent Transaction Entry as the given argument.
     */
    public boolean contains(Entry toCheck) {
        requireNonNull(toCheck);
        return internalEntryList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Adds a transaction entry to the unique entry list.
     * The transaction entry must not already exist in the list.
     */
    public void add(Entry toAdd) {
        if (contains(toAdd)) {
            throw new DuplicateEntryException();
        }
        internalEntryList.add(toAdd);
    }

    /**
     * Replaces the entry {@code target} in the unique entry list with {@code editedEntry}.
     * {@code target} must exist in the unique entry list.
     * The entry identity of {@code editedEntry} must not be the same as another existing tag in the list.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        int index = internalEntryList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }

        if (!target.isSameEntry(editedEntry) && contains(editedEntry)) {
            throw new DuplicateEntryException();
        }

        internalEntryList.set(index, editedEntry);
    }

    /**
     * Removes the equivalent entry from the unique entry list.
     * The entry must exist in the list.
     */
    public void remove(Entry toRemove) {
        requireNonNull(toRemove);
        if (!internalEntryList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    public void setEntries(UniqueEntryList replacement) {
        requireNonNull(replacement);
        internalEntryList.setAll(replacement.internalEntryList);
    }

    /**
     * Replaces the contents of this list with {@code entries}.
     * {@code entries} must not contain duplicate entries.
     */
    public void setEntry(List<Entry> entries) {
        requireAllNonNull(entries);
        if (!entriesAreUnique(entries)) {
            throw new DuplicateEntryException();
        }

        internalEntryList.setAll(entries);
    }

    /**
     * Returns true if {@code entries} contains only unique entries.
     */
    private boolean entriesAreUnique(List<Entry> entries) {
        for (int i = 0; i < entries.size() - 1; i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                if (entries.get(i).isSameEntry(entries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Entry> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalEntryList);
    }

    @Override
    public Iterator<Entry> iterator() {
        return internalEntryList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueEntryList // instanceof handles nulls
            && internalEntryList.equals(((UniqueEntryList) other).internalEntryList));
    }

    @Override
    public int hashCode() {
        return internalEntryList.hashCode();
    }


}
