package seedu.inventory.model.purchaseorder;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.purchaseorder.exceptions.PurchaseOrderNotFoundException;

/**
 *
 */
public class NonUniquePurchaseOrderList implements Iterable<PurchaseOrder> {

    private final ObservableList<PurchaseOrder> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent purchase order as the given argument.
     */
    public boolean contains(PurchaseOrder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Adds a purchase order to the list.
     */
    public void add(PurchaseOrder toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the purchase order {@code target} in the list with {@code editedPo}.
     * {@code target} must exist in the list.
     * The purchase order identity of {@code editedPo} must not be the same as another
     * existing purchase order in the list.
     */
    public void setPurchaseOrder(PurchaseOrder target, PurchaseOrder editedPo) {
        requireAllNonNull(target, editedPo);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PurchaseOrderNotFoundException();
        }

        internalList.set(index, editedPo);
    }

    /**
     * Removes the equivalent purchase order from the list.
     * The purchase order must exist in the list.
     */
    public void remove(PurchaseOrder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PurchaseOrderNotFoundException();
        }
    }

    public void setPurchaseOrders(NonUniquePurchaseOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }


    /**
     * Replaces the contents of this list with {@code PurchaseOrders}.
     * {@code PurchaseOrders} must not contain duplicate PurchaseOrders.
     */
    public void setPurchaseOrders(List<PurchaseOrder> po) {
        requireAllNonNull(po);
        internalList.setAll(po);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PurchaseOrder> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }


    @Override
    public Iterator<PurchaseOrder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NonUniquePurchaseOrderList // instanceof handles nulls
                && internalList.equals(((NonUniquePurchaseOrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}
