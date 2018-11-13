package seedu.address.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Wish;

/**
 * Each {@code WishTransaction} represents a state of the WishBook.
 * A state contains logs of undeleted wish histories.
 */
public class WishTransaction implements ActionCommandListener<WishTransaction> {

    /**
     * Stores a log of wish histories for this current state.
     */
    protected HashMap<UUID, LinkedList<Wish>> wishMap;

    /**
     * Logger associated with this class.
     */
    private final Logger logger;

    public WishTransaction() {
        this.logger = getLogger();
        this.wishMap = new HashMap<>();
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
     * Creates a WishTransaction using a {@code ReadOnlyWishBook}.
     *
     * @param readOnlyWishBook referenced data-containing {@code ReadOnlyWishBook}.
     */
    public WishTransaction(ReadOnlyWishBook readOnlyWishBook) {
        this();
        extractData(readOnlyWishBook);
    }

    /**
     * Constructor to be called when converting XmlWishTransactions object to a WishTransaction object.
     */
    public WishTransaction(HashMap<UUID, LinkedList<Wish>> wishMap) {
        this.logger = getLogger();
        this.wishMap = wishMap;
    }

    /**
     * Returns a deep copy of the the given {@code wishTransaction} object.
     */
    public WishTransaction getCopy(WishTransaction wishTransaction) {
        WishTransaction copy = new WishTransaction();
        wishTransaction.getWishMap().entrySet().forEach(entry -> {
            copy.wishMap.put(entry.getKey(), getCopy(entry.getValue()));
        });
        return copy;
    }

    private LinkedList<Wish> getCopy(LinkedList<Wish> wishList) {
        LinkedList<Wish> copy = new LinkedList<>();
        for (Wish wish : wishList) {
            copy.add(new Wish(wish));
        }
        return copy;
    }

    private Logger getLogger() {
        return LogsCenter.getLogger(WishTransaction.class);
    }

    /**
     * Extracts data from a {@code ReadOnlyWishBook} object and seeds that data into {@code wishMap}.
     * @param wishBook object containing data to seed this object with.
     */
    protected void extractData(ReadOnlyWishBook wishBook) {
        logger.info("Extracting data from ReadOnlyWishBook");
        for (Wish wish : wishBook.getWishList()) {
            addWish(wish);
        }
    }

    /**
     * Adds a wish to {@code wishMap} using {@code wish} full name as key.
     * @param wish
     */
    @Override
    public void addWish(Wish wish) {
        UUID key = getKey(wish);
        LinkedList<Wish> wishList = updateWishes(getWishList(key), wish);
        setValueOfKey(wish, wishList);
    }

    /**
     * Returns the key corresponding to this wish.
     * @param wish queried wish.
     * @return key for the corresponding wish.
     */
    private UUID getKey(Wish wish) {
        return wish.getId();
    }

    /**
     * Retrieves the wishlist stored at {@code key}.
     * @param key name of the wish.
     * @return wishlist stored at {@code key}.
     */
    private LinkedList<Wish> getWishList(UUID key) {
        return this.wishMap.getOrDefault(key, new LinkedList<>());
    }

    /**
     * Sets the value of an existing wish to {@code wishes}.
     * @param existing an existing wish.
     * @param wishes value to be changed to.
     */
    private void setValueOfKey(Wish existing, LinkedList<Wish> wishes) {
        this.wishMap.put(getKey(existing), wishes);
    }

    /**
     * Appends the updated wish to the log of saving history for this wish.
     * @param existingWishes log of saving history.
     * @param editedWish wish to be updated to.
     * @return an updated log of saving history.
     */
    private LinkedList<Wish> updateWishes(LinkedList<Wish> existingWishes, Wish editedWish) {
        existingWishes.add(editedWish);
        return existingWishes;
    }

    /**
     * Replaces the given wish {@code target} in the list with {@code editedWish}.
     * {@code target} must exist in the wish book.
     * The wish identity of {@code editedWish} must not be the same as another existing wish in the wish book.
     */
    @Override
    public void updateWish(Wish target, Wish editedWish) {
        // get a reference to the stored wishes
        LinkedList<Wish> wishes = getWishList(getKey(target));
        // update the stored wishes
        setValueOfKey(target, updateWishes(wishes, editedWish));
    }

    /**
     * @see WishTransaction#removeWish(UUID).
     */
    @Override
    public void removeWish(Wish wish) {
        removeWish(getKey(wish));
    }

    /**
     * Removes the wish specified by {@code key}.
     *
     * @param key name of wish to remove.
     * @throws NoSuchElementException if key does not exist.
     */
    private void removeWish(UUID key) throws NoSuchElementException {
        wishMap.remove(key);
    }

    /**
     * Changes recorded log of wish histories for the current state to the {@code newData}.
     *
     * @param newData Revisioned log of wish histories.
     */
    @Override
    public void resetData(WishTransaction newData) {
        setWishMap(newData.wishMap);
    }

    /**
     * @see WishTransaction#resetData(WishTransaction)
     */
    @Override
    public void resetData() {
        wishMap.clear();
    }

    /**
     * Responds to request to remove tag from all wishes.
     * @param tag tag to be removed.
     */
    @Override
    public void removeTagFromAll(Tag tag) {
        HashMap<UUID, LinkedList<Wish>> copiedWishMap = new HashMap<>();
        for (Map.Entry<UUID, LinkedList<Wish>> entries : wishMap.entrySet()) {
            LinkedList<Wish> wishes = entries.getValue();
            // associated wish has a recorded history
            if (wishes != null) {
                copiedWishMap = getModifiedMap(tag, wishes, copiedWishMap);
            }
        }
        setWishMap(copiedWishMap);
    }

    /**
     * Removes the given {@code tag} from the given list of {@code wish}es if the tag is present.
     * @param tag tag to be removed.
     * @param wishes list of wishes to be searched for tag.
     */
    private HashMap<UUID, LinkedList<Wish>> getModifiedMap(Tag tag, LinkedList<Wish> wishes,
                                                           HashMap<UUID, LinkedList<Wish>> map) {
        Wish mostRecent = getMostRecentWish(wishes);
        if (hasTag(tag, mostRecent)) {
            return getModifiedMap(tag, mostRecent, map);
        } else {
            map.put(getKey(mostRecent), wishes);
        }
        return map;
    }

    /**
     * Removes the given tag from the given wish.
     * @param tag tag to be removed.
     * @param mostRecent wish to remove the tag from.
     */
    private HashMap<UUID, LinkedList<Wish>> getModifiedMap(Tag tag, Wish mostRecent, HashMap<UUID,
            LinkedList<Wish>> map) {
        Set<Tag> updatedTags = getUpdatedTags(mostRecent, tag);
        Wish updatedWish = getUpdatedWish(updatedTags, mostRecent);
        LinkedList<Wish> wishList = getWishList(getKey(mostRecent));
        map.put(getKey(mostRecent), updateWishes(wishList, updatedWish));
        return map;
    }

    /**
     * Returns the most recent wish recorded.
     * @param wishes list to source for the most recent wish.
     * @return the most recent wish in {@code wishes}.
     */
    private Wish getMostRecentWish(LinkedList<Wish> wishes) {
        return wishes.get(wishes.size() - 1);
    }

    /**
     * Checks if the given wish contains the given tag.
     * @param tag to be checked in the wish.
     * @param mostRecent wish to be checked for tag.
     * @return true if the tag is found in the wish, false otherwise.
     */
    private boolean hasTag(Tag tag, Wish mostRecent) {
        return mostRecent.getTags().contains(tag);
    }

    /**
     * Retrieves the updated tag set belonging to a given wish.
     * @param mostRecent the given wish.
     * @param tag the tag to be removed from the old tag set.
     * @return the new tag set without the given {@code tag}.
     */
    private Set<Tag> getUpdatedTags(Wish mostRecent, Tag tag) {
        Set<Tag> oldTags = mostRecent.getTags();
        return oldTags.stream().filter(t->(!t.equals(tag))).collect(Collectors.toSet());
    }

    /**
     * Returns a new wish with the tags changed to {@code updatedTags}.
     * @param updatedTags field to update.
     * @param target wish to be updated.
     * @return a new updated wish.
     */
    private Wish getUpdatedWish(Set<Tag> updatedTags, Wish target) {
        return new Wish(target.getName(), target.getPrice(), target.getDate(),
                target.getUrl(), target.getSavedAmount(), target.getRemark(), updatedTags, target.getId());
    }

    /**
     * Sets the current state's wish histories to {@code wishMap}.
     * @param wishMap updated wish history log.
     */
    public void setWishMap(HashMap<UUID, LinkedList<Wish>> wishMap) {
        this.wishMap = wishMap;
    }

    public HashMap<UUID, LinkedList<Wish>> getWishMap() {
        return wishMap;
    }

    /**
     * Checks if this object has any values stored in it.
     */
    public boolean isEmpty() {
        return wishMap.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WishTransaction) {
            for (Map.Entry<UUID, LinkedList<Wish>> entries : ((WishTransaction) obj).wishMap.entrySet()) {
                if (!wishMap.containsKey(entries.getKey()) || !wishMap.containsValue(entries.getValue())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        wishMap.entrySet().forEach(entry -> {
            System.out.println(entry.getKey());
            entry.getValue().forEach(System.out::println);
        });
        return null;
    }
}
