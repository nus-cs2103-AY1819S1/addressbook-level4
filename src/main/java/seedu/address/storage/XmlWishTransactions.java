package seedu.address.storage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WishTransaction;
import seedu.address.model.wish.Wish;

/**
 * An Immutable wish transaction log that is serializable to XML format.
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlWishTransactions {

    @XmlElementWrapper
    private Map<String, XmlAdaptedWishWrapper> wishMap;

    /**
     * Creates an empty XmlSerializableWishTransactionMap.
     * This empty constructor is required for marshalling.
     */
    public XmlWishTransactions() {
        this.wishMap = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlWishTransactions(WishTransaction wishTransaction) {
        this();
        for (Map.Entry<String, LinkedList<Wish>> entries : wishTransaction.getWishMap().entrySet()) {
            this.wishMap.put(entries.getKey(), new XmlAdaptedWishWrapper(toXmlWishList(entries.getValue())));
        }

        //todo
        System.out.println("Converting from wishTransaction to XmlWishTransaction");
        System.out.println(this);
    }

    public Map<String, XmlAdaptedWishWrapper> getWishMap() {
        return wishMap;
    }

    /**
     * Adds a wish to {@code wishMap} using {@code wish} full name as key.
     * @param wish
     */
    public void addWish(Wish wish) {
        String wishName = getKey(wish);
        XmlAdaptedWishWrapper xmlAdaptedWishes = getWishList(wishName);
        setValueOfKey(wish, updateWishes(xmlAdaptedWishes, wish));
    }

    /**
     * Retrieves the wishlist stored at {@code key}.
     * @param key name of the wish.
     * @return wishlist stored at {@code key}.
     */
    private XmlAdaptedWishWrapper getWishList(String key) {
        return wishMap.getOrDefault(key, new XmlAdaptedWishWrapper());
    }

    /**
     * @see XmlWishTransactions#remove(String)
     */
    public void remove(Wish wish) throws NoSuchElementException {
        remove(getKey(wish));
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
        // get a reference to the stored wishes
        XmlAdaptedWishWrapper xmlAdaptedWishes = wishMap.get(getKey(target));
        // change the key of the target wish
        changeKey(target, editedWish);
        // update the stored wishes
        setValueOfKey(editedWish, updateWishes(xmlAdaptedWishes, editedWish));
    }

    /**
     * Appends the updated wish to the log of saving history for this wish.
     * @param existingWishes log of saving history.
     * @param editedWish wish to be updated to.
     * @return an updated log of saving history.
     */
    private XmlAdaptedWishWrapper updateWishes(XmlAdaptedWishWrapper existingWishes, Wish editedWish) {
        existingWishes.getXmlAdaptedWishes().add(new XmlAdaptedWish(editedWish));
        return existingWishes;
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
     * Sets the value of an existing wish to {@code wishes}.
     * @param wish an existing wish.
     * @param wishes value to be changed to.
     */
    private void setValueOfKey(Wish wish, XmlAdaptedWishWrapper wishes) {
        wishMap.put(getKey(wish), wishes);
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
     * Converts this JAXB friendly object into a WishTransaction object.
     * @return associated WishTransaction object for this XmlWishTransactions object.
     * @throws IllegalValueException if model is not correctly formatted.
     */
    public WishTransaction toModelType() throws IllegalValueException {
        return new WishTransaction(toWishMap());
    }

    /**
     * Unmarshalls {@code xmlAdaptedWish} and returns a hashmap of wishes.
     * @return hashmap of wishes.
     * @throws IllegalValueException if {@code xmlAdaptedWish} is not correctly formatted.
     */
    private HashMap<String, LinkedList<Wish>> toWishMap() throws IllegalValueException {
        HashMap<String, LinkedList<Wish>> convertedMap = new HashMap<>();
        for (Map.Entry<String, XmlAdaptedWishWrapper> entries : wishMap.entrySet()) {
            convertedMap.put(entries.getKey(), toWishList(entries.getValue()));
        }
        return convertedMap;
    }

    /**
     * Unmarshalls {@code xmlAdaptedWish} and returns a list of wishes.
     * @return list of wishes.
     * @throws IllegalValueException if {@code xmlAdaptedWish} is not correctly formatted.
     */
    public LinkedList<Wish> toCurrentStateWishTransactionList() throws IllegalValueException {
        LinkedList<Wish> wishes = new LinkedList<>();
        for (Map.Entry<String, XmlAdaptedWishWrapper> entries : wishMap.entrySet()) {
            wishes.addAll(toWishList(entries.getValue()));
        }
        return wishes;
    }

    /**
     * Converts a list of {@code Wish} to a list of {@code XmlAdaptedWish}.
     * @param wishList list of wishes.
     * @return a list of XmlAdapted wishes.
     */
    private LinkedList<XmlAdaptedWish> toXmlWishList(LinkedList<Wish> wishList) {
        LinkedList<XmlAdaptedWish> wishes = new LinkedList<>();
        for (Wish wish : wishList) {
            wishes.add(new XmlAdaptedWish(wish));
        }
        return wishes;
    }

    /**
     * Converts a list of {@code XmlAdaptedWish} to a list of {@code Wish}.
     * @param wishList list of xml adapted wishes.
     * @return a list of wishes.
     * @throws IllegalValueException if {@code xmlAdaptedWish} is not correctly formatted.
     */
    private LinkedList<Wish> toWishList(XmlAdaptedWishWrapper wishList) throws IllegalValueException {
        LinkedList<Wish> wishes = new LinkedList<>();
        for (XmlAdaptedWish xmlAdaptedWish : wishList.getXmlAdaptedWishes()) {
            wishes.add(xmlAdaptedWish.toModelType());
        }
        return wishes;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlWishTransactions)) {
            return false;
        }

        XmlWishTransactions otherMap = (XmlWishTransactions) other;
        return Objects.equals(wishMap, otherMap);
    }

}
