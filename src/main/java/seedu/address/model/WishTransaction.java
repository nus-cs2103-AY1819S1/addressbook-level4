package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    private HashMap<String, List<Wish>> wishMap;

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
        this.wishMap = new HashMap<>();
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
     */
    public WishTransaction(XmlWishTransactions unmarshalledSavedCopy, HashMap<String, List<Wish>> wishMap) {
        this.logger = getLogger();
        this.xmlWishTransactions = unmarshalledSavedCopy;
        this.wishMap = wishMap;
    }

    private Logger getLogger() {
        return LogsCenter.getLogger(WishTransaction.class);
    }

    /**
     * Adds a wish to {@code wishMap} using {@code wish} full name as key.
     * @param wish
     */
    public void addWish(Wish wish) {
        String wishName = getKey(wish);
        List<Wish> wishList = getWishList(wishName);
        setValueOfKey(wish, updateWishes(wishList, wish));
    }

    /**
     * Returns the key corresponding to this wish.
     * @param wish
     * @return
     */
    private String getKey(Wish wish) {
        return wish.getName().fullName;
    }

    /**
     * Retrieves the wishlist stored at {@code key}.
     * @param key name of the wish.
     * @return wishlist stored at {@code key}.
     */
    private List<Wish> getWishList(String key) {
        return wishMap.getOrDefault(key, new ArrayList<>());
    }

    /**
     * Sets the value of an existing wish to {@code wishes}.
     * @param existing an existing wish.
     * @param wishes value to be changed to.
     */
    private void setValueOfKey(Wish existing, List<Wish> wishes) {
        wishMap.put(getKey(existing), wishes);
    }

    /**
     * Appends the updated wish to the log of saving history for this wish.
     * @param existingWishes log of saving history.
     * @param editedWish wish to be updated to.
     * @return an updated log of saving history.
     */
    private List<Wish> updateWishes(List<Wish> existingWishes, Wish editedWish) {
        existingWishes.add(editedWish);
        return existingWishes;
    }

    /**
     * Replaces the given wish {@code target} in the list with {@code editedWish}.
     * {@code target} must exist in the wish book.
     * The wish identity of {@code editedWish} must not be the same as another existing wish in the wish book.
     */
    public void updateWish(Wish target, Wish editedWish) {
        // get a reference to the stored wishes
        List<Wish> wishes = wishMap.get(getKey(target));
        // change the key of the target wish
        changeKey(target, editedWish);
        // update the stored wishes
        setValueOfKey(editedWish, updateWishes(wishes, editedWish));
    }

    /**
     * Changes the key for the entry of {@code existing} to key of {@code newWish}.
     * Assumption: {@code existing} must be an existing wish in the map.
     *
     * @param existing existing wish in the wishmap.
     * @param newWish wish to be changed to.
     */
    private void changeKey(Wish existing, Wish newWish) {
        wishMap.remove(getKey(existing));
        wishMap.put(getKey(newWish), null);
    }

    /**
     * @see WishTransaction#removeWish(String).
     */
    public void removeWish(Wish wish) {
        removeWish(getKey(wish));
    }

    /**
     * Removes the wish specified by {@code key}.
     *
     * @param key name of wish to remove.
     * @throws NoSuchElementException if key does not exist.
     */
    private void removeWish(String key) throws NoSuchElementException {
        if (!wishMap.containsKey(key)) {
            throw new NoSuchElementException(key);
        }
        wishMap.remove(key);
    }

    /**
     * Changes recorded log of wish histories for the current state to the {@code newData}.
     *
     * @param newData Revisioned log of wish histories.
     */
    public void resetData(WishTransaction newData) {
        setWishes(newData.wishes);
        setWishMap(newData.wishMap);
        setXmlWishTransactions(newData.xmlWishTransactions);
    }

    /**
     * Sets the current state's wish histories to {@code wishMap}.
     * @param wishMap updated wish history log.
     */
    public void setWishMap(HashMap<String, List<Wish>> wishMap) {
        this.wishMap = wishMap;
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

    public XmlWishTransactions getXmlWishTransactions() {
        return xmlWishTransactions;
    }

    private void setXmlWishTransactions(XmlWishTransactions xmlWishTransactions) {
        this.xmlWishTransactions = xmlWishTransactions;
    }

}
