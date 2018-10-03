package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.occasion.exceptions.DuplicateOccasionException;

/**
 * A list of occasions that enforces uniqueness between its elements and does not allow nulls.
 * An occasion is considered unique by comparing using {@code Occasion#equals(Occasion)}. As such, adding and updating
 * of occasions uses Occasion#equals(Occasion) for equality so as to ensure that the occasion being added or updated
 * is unique in terms of identity in the UniqueOccasionList.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueOccasionList implements Iterable<Occasion> {
    private final ObservableList<Occasion> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent occasion as the given argument.
     */
    public boolean contains(Occasion occasionToCheck) {
        requireNonNull(occasionToCheck);
        return internalList.stream().anyMatch(occasionToCheck::equals);
    }

    /**
     * Adds the specified occasion to the list iff it is not originally contained
     * within it.
     */
    public void add(Occasion occasionToAdd) {
        requireNonNull(occasionToAdd);
        if (contains(occasionToAdd)) {
            throw new DuplicateOccasionException();
        }
        internalList.add(occasionToAdd);
    }

    /**
     * Replaces the occasion {@code target} in the list with {@code editedOccasion}.
     * {@code target} must exist in the list.
     * The occasion identity of {@code editedModule} must not be the same as another existing occasion in the list.
     */
    public void setOccasion(Occasion target, Occasion editedOccasion) {
        // TODO fill up implementation.
        // Will leave upto the implementer of the
        // update feature to do.
    }

    /**
     * Removes the designated occasions from the internal list.
     */
    public void remove(Occasion occasionToRemove) {
        requireNonNull(occasionToRemove);
        if (!internalList.remove(occasionToRemove)) {
            throw new DuplicateOccasionException();
        }
    }

    public void setOccasions(UniqueOccasionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setOccasions(List<Occasion> occasions) {
        requireNonNull(occasions);
        if (!occasionsAreUnique(occasions)) {
            throw new DuplicateOccasionException();
        }

        internalList.setAll(occasions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Occasion> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Occasion> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueOccasionList // instanceof handles nulls
                && internalList.equals(((UniqueOccasionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code occasions} contains only unique occasions.
     */
    private boolean occasionsAreUnique(List<Occasion> occasions) {
        for (int i = 0; i < occasions.size(); i++) {
            for (int j = 0; j < occasions.size(); j++) {
                if (occasions.get(i).equals(occasions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
