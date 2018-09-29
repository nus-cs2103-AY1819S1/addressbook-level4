package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wish.Wish;
import seedu.address.storage.XmlWishTransactions;

/**
 * Each {@code WishTransaction} represents a state of the WishBook.
 * A state contains logs of undeleted wish histories.
 */
public class WishTransaction extends WishBook {

    private List<Wish> wishes;
    private final XmlWishTransactions xmlWishTransactions;

    public WishTransaction() {
        this.wishes = new ArrayList<>();
        this.xmlWishTransactions = new XmlWishTransactions();
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
            // log
        }
    }

    public void resetData(WishTransaction newData) {
        setWishes(newData.wishes);
    }

    @Override
    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }
}
