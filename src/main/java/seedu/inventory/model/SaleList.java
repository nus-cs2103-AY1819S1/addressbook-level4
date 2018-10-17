package seedu.inventory.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.UniqueSaleList;

/**
 * Wraps all data at the sale-list level
 */
public class SaleList implements ReadOnlySaleList {

    private final UniqueSaleList uniqueSaleList = new UniqueSaleList();

    public SaleList() {}

    /**
     * Creates a UniqueSaleList using the sales in the {@code toBeCopied}
     */
    public SaleList(ReadOnlySaleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the sale list with {@code sales}.
     * {@code sales} can contain any number of valid sales
     */
    public void setSales(List<Sale> sales) {
        this.uniqueSaleList.setSales(sales);
    }

    /**
     * Resets the existing data of this {@code UniqueSaleList} with {@code newData}.
     */
    public void resetData(ReadOnlySaleList newData) {
        requireNonNull(newData);

        setSales(newData.getSaleList());
    }

    //// sale-level operations

    /**
     * Returns true if a sale with the same ID as {@code sale} exists in the list.
     */
    public boolean hasSale(Sale sale) {
        requireNonNull(sale);
        return uniqueSaleList.contains(sale);
    }

    /**
     * Adds a sale to the sale list.
     */
    public void addSale(Sale sale) {
        uniqueSaleList.add(sale);
    }

    /**
     * Removes {@code key} from this {@code UniqueSaleList}.
     * {@code key} must exist in the UniqueSaleList.
     */
    public void removeSale(Sale key) {
        uniqueSaleList.remove(key);
    }

    /**
     * Get next available sale ID
     */
    public String getNextSaleId() {
        return uniqueSaleList.getNextSaleId();
    }

    //// util methods

    @Override
    public String toString() {
        return uniqueSaleList.asUnmodifiableObservableList().size() + " sales.";
    }

    @Override
    public ObservableList<Sale> getSaleList() {
        return uniqueSaleList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaleList // instanceof handles nulls
                && uniqueSaleList.equals(((SaleList) other).uniqueSaleList));
    }

    @Override
    public int hashCode() {
        return uniqueSaleList.hashCode();
    }
}
