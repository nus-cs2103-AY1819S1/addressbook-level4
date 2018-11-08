package ssp.scheduleplanner.model.tag;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ssp.scheduleplanner.model.tag.exceptions.DuplicateTagException;

/**
 * A list of tags that enforces uniqueness between its elements and does not allow nulls.
 * A tags is considered unique by comparing using {@code Tag#isSameTag(Tag)}. As such, adding and updating of
 * tags uses Tag#isSameTag(Tag) for equality so as to ensure that the tag being added is
 * unique in terms of identity in the UniqueTagList.
 *
 * Supports a minimal set of list operations.
 */

public class UniqueTagList implements Iterable<Tag> {
    private ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Adds a tag to the list.
     * The tag must not already exist in the list.
     */
    public void add(Tag toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTag);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof UniqueTagList) {
            if (internalList.equals(((UniqueTagList) other).internalList)) {
                return true;
            } else {
                Object[] tags = (((UniqueTagList) other).internalList).toArray();
                boolean isSameTagList = true;
                int i = 0;
                while (isSameTagList && i < tags.length) {
                    isSameTagList = isSameTagList
                            && internalList.contains(tags[i]);
                    i++;
                }
                return isSameTagList;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public ObservableList<Tag> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTags(List<Tag> tags) {
        requireAllNonNull(tags);
        if (!tagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }
        internalList.setAll(tags);
    }

    /**
     * Returns true if {@code tags} contains only unique tags.
     */
    private boolean tagsAreUnique(List<Tag> tags) {
        for (int i = 0; i < tags.size() - 1; i++) {
            for (int j = i + 1; j < tags.size(); j++) {
                if (tags.get(i).isSameTag(tags.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Replaces the contents of this list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTag(List<Tag> tags) {
        requireAllNonNull(tags);
        if (!tagsAreUnique(tags)) {
            throw new DuplicateTagException();
        }

        internalList.setAll(tags);
    }

}
