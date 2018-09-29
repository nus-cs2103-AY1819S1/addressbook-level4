package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wish.Wish;

/**
 * An Immutable wish transaction log that is serializable to XML format.
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlWishTransactions {
    private Map<String, List<XmlAdaptedWish>> wishMap;

    /**
     * Creates an empty XmlSerializableWishTransactionMap.
     * This empty constructor is required for marshalling.
     */
    public XmlWishTransactions() {
        this.wishMap = new HashMap<>();
    }

    public Map<String, List<XmlAdaptedWish>> getWishMap() {
        return wishMap;
    }

    /**
     * Adds a wish to {@code wishMap} using {@code wish} full name as key.
     * @param wish
     */
    public void addWish(Wish wish) {
        String wishName = wish.getName().fullName;
        List<XmlAdaptedWish> wishList = getWishList(wishName);
        wishList.add(new XmlAdaptedWish(wish));
        wishMap.put(wishName, wishList);
    }

    /**
     * Retrieves the wishlist stored at {@code key}.
     * @param key name of the wish.
     * @return wishlist stored at {@code key}.
     */
    private List<XmlAdaptedWish> getWishList(String key) {
        return wishMap.getOrDefault(key, new ArrayList<>());
    }

    /**
     * @see XmlWishTransactions#remove(String)
     */
    public void remove(Wish wish) throws NoSuchElementException {
        remove(wish.getName().fullName);
    }

    /**
     * Removes the wish specified by {@code key}.
     *
     * @param key name of wish to remove.
     * @throws NoSuchElementException if key does not exist.
     */
    private void remove(String key) throws NoSuchElementException {
        if (!wishMap.containsKey(key)) {
            throw new NoSuchElementException(key);
        }
        wishMap.remove(key);
    }

    /**
     * Replaces the given wish {@code target} in the list with {@code editedWish}.
     * {@code target} must exist in the wish book.
     * The wish identity of {@code editedWish} must not be the same as another existing wish in the wish book.
     */
    public void updateWish(Wish target, Wish editedWish) {
        List<XmlAdaptedWish> wishes = wishMap.get(getKey(target));
        updateWish(wishes, new XmlAdaptedWish(target), new XmlAdaptedWish(editedWish));
    }

    /**
     * @see XmlWishTransactions#updateWish(Wish, Wish)
     *
     */
    private void updateWish(List<XmlAdaptedWish> wishes, XmlAdaptedWish target, XmlAdaptedWish editedWish) {
        if (wishes.contains(target)) {
            wishes.set(wishes.indexOf(target), editedWish);
        }
    }

    /**
     * Returns the key corresponding to this wish.
     * @param wish
     * @return
     */
    public String getKey(Wish wish) {
        return wish.getName().fullName;
    }

    /**
     * Unmarshalls the xml content in {@code wishMap} and returns a list of current state wishes.
     * @return list of current state wishes.
     */
    public List<Wish> toCurrentStateWishTransactionList() throws IllegalValueException {
        List<Wish> wishes = new ArrayList<>();
        for (Map.Entry<String, List<XmlAdaptedWish>> entries : wishMap.entrySet()) {
            wishes.addAll(toWishList(entries.getValue()));
        }
        return wishes;
    }

    /**
     * Converts a list of {@code XmlAdaptedWish} to a list of {@code Wish}.
     * @param wishList list of xml adapted wishes.
     * @return a list of wishes.
     * @throws IllegalValueException if {@code xmlAdaptedWish} is of incorrect model type.
     */
    private List<Wish> toWishList(List<XmlAdaptedWish> wishList) throws IllegalValueException {
        List<Wish> wishes = new ArrayList<>();
        for (XmlAdaptedWish xmlAdaptedWish : wishList) {
            wishes.add(xmlAdaptedWish.toModelType());
        }
        return wishes;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedWish)) {
            return false;
        }

        XmlWishTransactions otherMap = (XmlWishTransactions) other;
        return Objects.equals(wishMap, otherMap);
    }
}
