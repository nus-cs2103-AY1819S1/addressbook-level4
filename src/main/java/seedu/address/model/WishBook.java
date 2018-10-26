package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.amount.Amount;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.UniqueWishList;
import seedu.address.model.wish.Wish;
import seedu.address.model.wish.exceptions.DuplicateWishException;


/**
 * Wraps all data at the wish book level
 * Duplicates are not allowed (by .isSameWish comparison)
 */
public class WishBook implements ReadOnlyWishBook {

    private final UniqueWishList wishes;
    private SavedAmount unusedFunds;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        wishes = new UniqueWishList();
        unusedFunds = new SavedAmount("" + 0.0);
    }

    public WishBook() {}

    /**
     * Creates an WishBook using the Wishes in the {@code toBeCopied}
     */
    public WishBook(ReadOnlyWishBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the wish list with {@code wishes}.
     * {@code wishes} must not contain duplicate wishes.
     */
    public void setWishes(List<Wish> wishes) {
        this.wishes.setWishes(wishes);
    }

    /**
     * Resets the existing data of this {@code WishBook} with {@code newData}.
     */
    public void resetData(ReadOnlyWishBook newData) {
        requireNonNull(newData);

        setWishes(newData.getWishList());
        setUnusedFunds(newData.getUnusedFunds());
    }

    /**
     * Update the value of unused funds.
     * @param change The amount to increment/decrease by.
     */
    public void updateUnusedFunds(Amount change) {
        this.unusedFunds = this.unusedFunds.incrementSavedAmount(change);
    }

    /**
     * Sets the value of the unused funds.
     * @param amount The amount to set.
     */
    public void setUnusedFunds(SavedAmount amount) {
        this.unusedFunds = amount;
    }

    @Override
    public SavedAmount getUnusedFunds() {
        return this.unusedFunds;
    }

    //// wish-level operations

    /**
     * Returns true if a wish with the same identity as {@code wish} exists in the wish book.
     */
    public boolean hasWish(Wish wish) {
        requireNonNull(wish);
        return wishes.contains(wish);
    }

    /**
     * Adds a wish to the wish book.
     * The wish must not already exist in the wish book.
     */
    public void addWish(Wish p) {
        wishes.add(p);
    }

    /**
     * Replaces the given wish {@code target} in the list with {@code editedWish}.
     * {@code target} must exist in the wish book.
     * The wish identity of {@code editedWish} must not be the same as another existing wish in the wish book.
     */
    public void updateWish(Wish target, Wish editedWish) {
        requireNonNull(editedWish);

        wishes.setWish(target, editedWish);
    }

    /**
     * Removes {@code key} from this {@code WishBook}.
     * {@code key} must exist in the wish book.
     */
    public void removeWish(Wish key) {
        if (!key.isFulfilled()) {
            updateUnusedFunds(key.getSavedAmount().getAmount());
        }
        wishes.remove(key);
    }

    /**
     * Removes {@code tag} from all {@code wish}es in this {@code WishBook}.
     * @throws DuplicateWishException if there's a duplicate {@code Wish} in this {@code WishBook}.
     */
    public void removeTagFromAll(Tag tag) throws DuplicateWishException {
        ArrayList<Wish> modifiedWishes = new ArrayList<>();

        for (Wish wish : wishes.asUnmodifiableObservableList()) {
            Set<Tag> modifiedTags = wish.getTags();
            modifiedTags.removeIf(t -> t == tag);

            Wish modifiedWish = new Wish(wish.getName(),
                    wish.getPrice(),
                    wish.getDate(),
                    wish.getUrl(),
                    wish.getSavedAmount(),
                    wish.getRemark(), modifiedTags, wish.getId());

            modifiedWishes.add(modifiedWish);
        }
        wishes.setWishes(modifiedWishes);
    }


    //// util methods

    @Override
    public String toString() {
        return wishes.asUnmodifiableObservableList().size() + " wishes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Wish> getWishList() {
        return wishes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WishBook // instanceof handles nulls
                && wishes.equals(((WishBook) other).wishes)
                && unusedFunds.equals(((WishBook) other).unusedFunds));
    }

    @Override
    public int hashCode() {
        return wishes.hashCode();
    }
}
