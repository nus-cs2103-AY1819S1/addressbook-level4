package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all budget data at the budget-book level
 * Duplicates in CCA tags are not allowed (by .isSameCCA comparison)
 */
public class BudgetBook implements ReadOnlyBudgetBook {
    private final UniqueTagList tags;

    {
        tags = new UniqueTagList();
    }

    public BudgetBook() {
    }

    /**
     * Creates a Budget Book using the Tags in the {@code toBeCopied}
     */
    public BudgetBook(ReadOnlyBudgetBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }
    //// list overwrite operations

    /**
     * Replaces the contents of the tag list with {@code tags}.
     * {@code tags} must not contain duplicate tags.
     */
    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code BudgetBook} with {@code newData}.
     */
    public void resetData(ReadOnlyBudgetBook newData) {
        requireNonNull(newData);

        setTags(newData.getCcaTagList());
    }
    //// person-level operations

    /**
     * Returns true if a tag {@code tag} exists in the address book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }

    /**
     * Adds a tag to the budget book.
     * The tag must not already exist in the address book.
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     * Replaces the given tag {@code target} in the list with {@code editedTag}.
     * {@code target} must exist in the address book.
     * The tag {@code editedTag} must not be the same as another existing tag in the budget book.
     */
    public void updatePerson(Tag target, Tag editedTag) {
        requireNonNull(editedTag);

        tags.setTag(target, editedTag);
    }

    /**
     * Removes {@code key} from this {@code BudgetBook}.
     * {@code key} must exist in the budget book.
     */
    public void removeTag(Tag key) {
        tags.remove(key);
    }

    //// util methods
    @Override
    public String toString() {
        return tags.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Tag> getCcaTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetBook // instanceof handles nulls
            && tags.equals(((BudgetBook) other).tags));
    }

    @Override
    public int hashCode() {
        return tags.hashCode();
    }
}
