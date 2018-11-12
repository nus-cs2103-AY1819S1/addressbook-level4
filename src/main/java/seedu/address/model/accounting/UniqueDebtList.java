package seedu.address.model.accounting;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.accounting.exception.DebtNotFoundException;
import seedu.address.model.accounting.exception.DuplicateDebtException;

/**
 * A list of debts that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 *
 */


public class UniqueDebtList implements Iterable<Debt> {

    private final ObservableList<Debt> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent debt as the given argument.
     */
    public boolean contains(Debt toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a debt to the list.
     * The debt must not already exist in the list.
     */
    public void add(Debt toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDebtException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent debt from the list.
     * The debt must exist in the list.
     */
    public void remove(Debt toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DebtNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Debt> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@Override
    public Iterator<Debt> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDebtList // instanceof handles nulls
                && internalList.equals(((UniqueDebtList) other).internalList));
    }

}

