package seedu.clinicio.model.consultation;

//@@author arsalanc-v2

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.consultation.exceptions.ConsultationNotFoundException;
import seedu.clinicio.model.consultation.exceptions.DuplicateConsultationException;

/**
 * A list of consultations that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Consultation#isSameConsultation(Consultation)}. As such,
 * adding and updating of consultations uses Consultation#isSameConsultation(Consultation) for equality so as to
 * ensure that the consultation being added or updated is unique in terms of identity in the UniqueStaffList. However,
 * the removal of a consultation uses Consultation#equals(Object) so as to ensure that the consultation with exactly
 * the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see Consultation#isSameConsultation(Consultation)
 */
public class UniqueConsultationList {

    private final ObservableList<Consultation> internalList = FXCollections.observableArrayList();

    /**
     * @return {@code true} if the list contains an equivalent consultation given as argument.
     */
    public boolean contains(Consultation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameConsultation);
    }

    /**
     * Adds a consultation to the list.
     * The consultation must not already exist in the list.
     */
    public void add(Consultation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateConsultationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the consultation {@code target} in the list with {@code editedConsultation}.
     * {@code target} must exist in the list.
     * The consultation {@code editedConsultation} must not be the same as another existing consultation in the list.
     */
    public void setConsultation(Consultation target, Consultation editedConsultation) {
        requireAllNonNull(target, editedConsultation);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ConsultationNotFoundException();
        }

        if (!target.isSameConsultation(editedConsultation) && contains(editedConsultation)) {
            throw new DuplicateConsultationException();
        }

        internalList.set(index, editedConsultation);
    }

    /**
     * Removes the equivalent consultation from the list.
     * The consultation must exist in the list.
     */
    public void remove(Consultation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ConsultationNotFoundException();
        }
    }

    /**
     * Replaces this {@code UniqueConsultationList} with another.
     */
    public void setConsultations(UniqueConsultationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setConsultations(List<Consultation> consultations) {
        requireNonNull(consultations);
        if (!consultationsAreUnique(consultations)) {
            throw new DuplicateConsultationException();
        }
        internalList.setAll(consultations);
    }

    /**
     * @return the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Consultation> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * @return {@code true} if all consultations in the argument list are unique. {@code false} otherwise.
     */
    private boolean consultationsAreUnique(List<Consultation> consultations) {
        for (int i = 0; i < consultations.size() - 1; i++) {
            for (int j = i + 1; j < consultations.size(); j++) {
                if (consultations.get(i).isSameConsultation(consultations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueConsultationList // instanceof handles nulls
            && internalList.equals(((UniqueConsultationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
