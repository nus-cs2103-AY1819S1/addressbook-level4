package seedu.address.model.encryption;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.encryption.EncryptedExpense;
import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;

/**
 * A list of encrypted expenses that enforces uniqueness between its elements and does not allow nulls.
 * An encrypted expense is considered unique by comparing using {EncryptedExpense#isSameExpense(EncryptedExpense)}.
 * As such, adding and updating of expenses uses EncryptedExpense#isSameExpense(EncryptedExpense)
 * for equality so as to ensure that the expense being added or updated is
 * unique in terms of identity in the UniqueEncryptedExpenseList.
 * However, the removal of a expense uses EncryptedExpense#equals(Object) so as to ensure that the expense
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see EncryptedExpense#isSameExpense(EncryptedExpense)
 */
public class UniqueEncryptedExpenseList implements Iterable<EncryptedExpense> {

    private final List<EncryptedExpense> internalList = new LinkedList<>();

    /**
     * Returns true if the list contains an equivalent expense as the given argument.
     */
    public boolean contains(EncryptedExpense toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameExpense);
    }

    /**
     * Adds a expense to the list.
     * The expense must not already exist in the list.
     */
    public void add(EncryptedExpense toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExpenseException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<EncryptedExpense> iterator() {
        return internalList.iterator();
    }

    public Stream<EncryptedExpense> stream() {
        return internalList.stream();
    }

    public int size() {
        return internalList.size();
    }
}