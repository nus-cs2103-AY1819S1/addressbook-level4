package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 * A group is considered unique by comparing using tagName. As such, adding and updating of
 * groups uses tagName for equality so as to ensure that the group being added or updated is
 * unique in terms of identity in the UniqueGroupList. However, the removal of a group uses equals so
 * as to ensure that the group with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * {@author jeffreyooi}
 */
public class UniqueGroupList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent group as the given argument
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        // TODO: change to internalList stream when group is implemented properly
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).tagName.equals(toCheck.tagName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a group to the list.
     * The group must not already exist in the list.
     */
    public void add(Tag toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            return;
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the group {@code target} in the list with the {@code editedTag}.
     * {@code target} must exist in the list.
     * The group identity of {@code editedTag} must not be the same as another existing group in the list.
     */
    public void setGroup(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // TODO throw exception
            return;
        }

        if (!target.tagName.equals(editedTag.tagName) && contains(editedTag)) {
            // TODO throw exception
            return;
        }

        internalList.set(index, editedTag);
    }

    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     */
    public void remove(Tag toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO throw exception
            return;
        }
    }

    public void setGroups(UniqueGroupList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Tag> groups) {
        requireAllNonNull(groups);
        if (!groupsAreUnique(groups)) {
            // TODO throw exception??
            return;
        }

        internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList) // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code groups} contain only unique groups.
     */
    private boolean groupsAreUnique(List<Tag> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).tagName.equals(groups.get(j).tagName)) {
                    return false;
                }
            }
        }
        return true;
    }
}
