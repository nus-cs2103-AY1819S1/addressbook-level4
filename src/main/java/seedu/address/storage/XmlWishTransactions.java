package seedu.address.storage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WishTransaction;
import seedu.address.model.wish.Wish;

/**
 * An Immutable wish transaction log that is serializable to XML FORMAT.
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlWishTransactions {

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
        for (Map.Entry<UUID, LinkedList<Wish>> entries : wishTransaction.getWishMap().entrySet()) {
            this.wishMap.put(IdMarshaller.toWishmapKey(entries.getKey()),
                    new XmlAdaptedWishWrapper(toXmlWishList(entries.getValue())));
        }
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
    private HashMap<UUID, LinkedList<Wish>> toWishMap() throws IllegalValueException {
        HashMap<UUID, LinkedList<Wish>> convertedMap = new HashMap<>();
        for (Map.Entry<String, XmlAdaptedWishWrapper> entries : wishMap.entrySet()) {
            convertedMap.put(IdMarshaller.toUuid(entries.getKey()), toWishList(entries.getValue()));
        }
        return convertedMap;
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
        return Objects.equals(wishMap.keySet(), otherMap.wishMap.keySet());
    }

    /**
     * This class contains methods that converts non xml-compatible key (UUID) to xml-compatible key (String).
     */
    private static class IdMarshaller {
        static String toWishmapKey(UUID uuid) {
            return uuid.toString();
        }

        static UUID toUuid(String key) {
            return UUID.fromString(key);
        }
    }
}
