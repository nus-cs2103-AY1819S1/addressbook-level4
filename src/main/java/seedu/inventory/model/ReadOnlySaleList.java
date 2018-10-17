package seedu.inventory.model;

import javafx.collections.ObservableList;
import seedu.inventory.model.sale.Sale;

/**
 * Unmodifiable view of the sale orders
 */
public interface ReadOnlySaleList {

    /**
     * Returns an unmodifiable view of the sale orders list.
     */
    ObservableList<Sale> getSaleList();

    /**
     * Returns the next available sale ID.
     */
    String getNextSaleId();

}
