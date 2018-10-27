package seedu.clinicio.model.receptionist;

import static java.util.Objects.requireNonNull;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.clinicio.model.receptionist.exceptions.DuplicateReceptionistException;
import seedu.clinicio.model.receptionist.exceptions.ReceptionistNotFoundException;

//@@author jjlee050
/**
 * A list of receptionists that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Receptionist#isSameReceptionist(Receptionist)}.
 * As such, adding and updating of receptionists uses Receptionist#isSameReceptionist(Receptionist) for equality
 * so as to ensure that the receptionist being added or updated is unique in terms of identity
 * in the UniqueReceptionistList. However, the removal of a receptionist uses Receptionist#equals(Object) so as to
 * ensure that the receptionist with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Receptionist#isSameReceptionist(Receptionist)
 */
public class UniqueReceptionistList implements Iterable<Receptionist> {

    private final ObservableList<Receptionist> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent receptionist as the given argument.
     */
    public boolean contains(Receptionist toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReceptionist);
    }

    /**
     * Adds a receptionist to the list.
     * The receptionist must not already exist in the list.
     */
    public void add(Receptionist toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReceptionistException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the receptionist {@code target} in the list with {@code editedReceptionist}.
     * {@code target} must exist in the list.
     * The receptionist identity of {@code editedReceptionist} must not be the same as another existing
     * receptionist in the list.
     */
    public void setReceptionist(Receptionist target, Receptionist editedReceptionist) {
        requireAllNonNull(target, editedReceptionist);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReceptionistNotFoundException();
        }

        if (!target.isSameReceptionist(editedReceptionist) && contains(editedReceptionist)) {
            throw new DuplicateReceptionistException();
        }

        internalList.set(index, editedReceptionist);
    }

    /**
     * Removes the equivalent receptionist from the list.
     * The receptionist must exist in the list.
     */
    public void remove(Receptionist toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReceptionistNotFoundException();
        }
    }

    public void setReceptionists(UniqueReceptionistList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code receptionists}.
     * {@code receptionists} must not contain duplicate receptionists.
     */
    public void setReceptionists(List<Receptionist> receptionists) {
        requireAllNonNull(receptionists);
        if (!receptionistsAreUnique(receptionists)) {
            throw new DuplicateReceptionistException();
        }

        internalList.setAll(receptionists);
    }

    /**
     * Retrieve the doctor from the list
     * {@code doctor} must be inside the ClinicIO record.
     */
    public Receptionist getReceptionist(Receptionist receptionist) {
        requireNonNull(receptionist);

        if (!contains(receptionist)) {
            throw new ReceptionistNotFoundException();
        }

        return internalList.stream()
                .filter(x -> x.isSameReceptionist(receptionist))
                .findFirst().orElse(null);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Receptionist> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Receptionist> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReceptionistList // instanceof handles nulls
                && internalList.equals(((UniqueReceptionistList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code receptionists} contains only unique receptionists.
     */
    private boolean receptionistsAreUnique(List<Receptionist> receptionists) {
        for (int i = 0; i < receptionists.size() - 1; i++) {
            for (int j = i + 1; j < receptionists.size(); j++) {
                if (receptionists.get(i).isSameReceptionist(receptionists.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
