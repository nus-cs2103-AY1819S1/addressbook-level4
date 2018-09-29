package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wish.Wish;
import seedu.address.storage.XmlWishTransactions;

/**
 * Each {@code WishTransaction} represents a state of the WishBook.
 * A state contains logs of undeleted wish histories.
 */
public class WishTransaction extends WishBook {

    /**
     * Stores a log of wish histories for this current state.
     */
    private List<Wish> wishes;

    /**
     * Logger associated with this class.
     */
    private final Logger logger;

    /**
     * Wrapper for log of wish histories in xml format for this current state.
     */
    private XmlWishTransactions xmlWishTransactions;

    public WishTransaction() {
        this.wishes = new ArrayList<>();
        this.xmlWishTransactions = new XmlWishTransactions();
        this.logger = LogsCenter.getLogger(WishTransaction.class);
    }

    /**
     * Creates a WishTransaction using a saved copy of {@code WishTransaction}.
     *
     * @param savedCopy saved copy of {@code WishTransaction} stored in file.
     */
    public WishTransaction(WishTransaction savedCopy) {
        this.logger = savedCopy.logger;
        resetData(savedCopy);
    }

    @Override
    public void addWish(Wish p) {
        xmlWishTransactions.addWish(p);
        updateWishes();
    }

    @Override
    public void updateWish(Wish target, Wish editedWish) {
        xmlWishTransactions.updateWish(target, editedWish);
        updateWishes();
    }

    @Override
    public void removeWish(Wish key) {
        xmlWishTransactions.remove(key);
        updateWishes();
    }

    /**
     * Handles exceptions with unmarshalling {@code xmlWishTransactions} to {@code wishes}
     */
    private void updateWishes() {
        try {
            this.wishes = xmlWishTransactions.toCurrentStateWishTransactionList();
        } catch (IllegalValueException e) {
            logger.fine("Error in unmarshalling xmlWishTransactions. \n" +
                    e.getMessage());
        }
    }

    /**
     * Changes recorded log of wish histories for the current state to the {@code newData}.
     *
     * @param newData Revisioned log of wish histories.
     */
    public void resetData(WishTransaction newData) {
        setWishes(newData.wishes);
        setXmlWishTransactions(newData.xmlWishTransactions);
    }

    @Override
    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    private void setXmlWishTransactions(XmlWishTransactions xmlWishTransactions) {
        this.xmlWishTransactions = xmlWishTransactions;
    }

}