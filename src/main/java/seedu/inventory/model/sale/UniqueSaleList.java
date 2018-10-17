package seedu.inventory.model.sale;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.model.sale.exceptions.DuplicateSaleException;
import seedu.inventory.model.sale.exceptions.SaleNotFoundException;

/**
 * A list of sales for all the sales created.
 * Ensures sale ID is unique for all added sales.
 */
public class UniqueSaleList implements Iterable<Sale> {

    private final ObservableList<Sale> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains this sale ID
     */
    public boolean contains(Sale toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().map(sales -> sales.getSaleId()).collect(Collectors.toList())
                .contains(toCheck.getSaleId());
    }

    /**
     * Adds a sale to the list.
     */
    public void add(Sale toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateSaleException();
        }

        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent sale from the list.
     * The sale must exist in the list.
     */
    public void remove(Sale toRemove) {
        requireNonNull(toRemove);

        if (!internalList.remove(toRemove)) {
            throw new SaleNotFoundException();
        }
    }

    /**
     * Replace the whole Sale List with new Sale List
     */
    public void setSales(UniqueSaleList replacement) {
        requireNonNull(replacement);

        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code sales}.
     * {@code sales} can contain any number of valid sales
     */
    public void setSales(List<Sale> sales) {
        requireAllNonNull(sales);

        if (!salesAreUnique(sales)) {
            throw new DuplicateSaleException();
        }

        internalList.setAll(sales);
    }

    /**
     * Get next available sale ID
     */
    public String getNextSaleId() {
        if (internalList.isEmpty()) {
            return "1";
        }

        int currentSaleId = internalList.stream().map(sale -> Integer.parseInt(sale.getSaleId().toString()))
                .max(Comparator.comparing(i -> i)).orElse(0);

        int nextSaleId = currentSaleId + 1;

        return Integer.toString(nextSaleId);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Sale> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Sale> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueSaleList // instanceof handles nulls
                && internalList.equals(((UniqueSaleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code sales} contains only unique sales.
     */
    private boolean salesAreUnique(List<Sale> sales) {
        for (int i = 0; i < sales.size() - 1; i++) {
            for (int j = i + 1; j < sales.size(); j++) {
                if (sales.get(i).getSaleId().toString().equals(sales.get(j).getSaleId().toString())) {
                    return false;
                }
            }
        }

        return true;
    }
}
