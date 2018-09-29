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
public class WishTransaction {

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
        this.logger = getLogger();
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

    /**
     * Creates a WishTransaction using a saved copy of {@code unmarshalledSavedCopy}
     *
     * @param unmarshalledSavedCopy unmarshalled copy of saved xml wish histories.
     * @throws IllegalValueException
     */
    public WishTransaction(XmlWishTransactions unmarshalledSavedCopy) throws IllegalValueException {
        this.logger = getLogger();
        this.xmlWishTransactions = unmarshalledSavedCopy;
        unmarshalledSavedCopy.toCurrentStateWishTransactionList();
    }

    private Logger getLogger() {
        return LogsCenter.getLogger(WishTransaction.class);
    }

    /**
     * Adds a wish to history.
     * @param p wish to be added.
     */
    public void addWish(Wish p) {
        xmlWishTransactions.addWish(p);
        updateWishes();
    }

    /**
     * Updates {@code target} wish to {@code editedWish} in history.
     * @param target an existing wish in history.
     * @param editedWish wish to be changed to.
     */
    public void updateWish(Wish target, Wish editedWish) {
        xmlWishTransactions.updateWish(target, editedWish);
        updateWishes();
    }

    /**
     * Removes a wish from history.
     * @param key wish to be removed from history.
     */
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
            logger.fine("Error in unmarshalling xmlWishTransactions. \n"
                    + e.getMessage());
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

    /**
     * Sets the current state's wish histories to {@code wishes}.
     * @param wishes updated wish history log.
     */
    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    /**
     * Retrieves the current state's wish histories.
     * @return
     */
    public List<Wish> getWishes() {
        return wishes;
    }

    private void setXmlWishTransactions(XmlWishTransactions xmlWishTransactions) {
        this.xmlWishTransactions = xmlWishTransactions;
    }

}
