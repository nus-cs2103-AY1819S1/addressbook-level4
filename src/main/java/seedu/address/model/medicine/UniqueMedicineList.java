package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;

/**
 * A list of medicines that enforces uniqueness between its elements and does not allow nulls.
 * A medicine is considered unique by comparing using {@code Medicine#isSameMedicine(Medicine)}.
 * As such, adding and updating of medicines uses Medicine#isSameMedicine(Medicine) for equality so as to ensure
 * that the medicine being added or updated is unique in terms of identity in the UniqueMedicineList.
 * However, the removal of a medicine uses Medicine#equals(Object) so as to ensure that the medicine
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Medicine#isSameMedicine(Medicine)
 */
public class UniqueMedicineList implements Iterable<Medicine> {

    private final ObservableList<Medicine> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent medicine as the given argument.
     */
    public boolean contains(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMedicine);
    }

    /**
     * Adds a medicine to the list.
     * The medicine must not already exist in the list.
     */
    public void add(Medicine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMedicineException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the medicine {@code target} in the list with {@code editedMedicine}.
     * {@code target} must exist in the list.
     * The medicine identity of {@code editedMedicine} must not be the same as another existing medicine in the list.
     */
    public void setPerson(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MedicineNotFoundException();
        }

        if (!target.isSameMedicine(editedMedicine) && contains(editedMedicine)) {
            throw new DuplicateMedicineException();
        }

        internalList.set(index, editedMedicine);
    }

    /**
     * Removes the equivalent medicine from the list.
     * The medicine must exist in the list.
     */
    public void remove(Medicine toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MedicineNotFoundException();
        }
    }

    public void setPersons(UniqueMedicineList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPersons(List<Medicine> patients) {
        requireAllNonNull(patients);
        if (!personsAreUnique(patients)) {
            throw new DuplicateMedicineException();
        }

        internalList.setAll(patients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Medicine> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Medicine> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMedicineList // instanceof handles nulls
                && internalList.equals(((UniqueMedicineList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code patients} contains only unique patients.
     */
    private boolean personsAreUnique(List<Medicine> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(i).isSameMedicine(patients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
