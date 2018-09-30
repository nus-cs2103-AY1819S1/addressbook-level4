package seedu.address.model;

import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Wish;

/**
 * This interface is implemented by classes listening on action commands.
 * An action command is defined to be a command that changes the state of the WishBook.
 * Examples of action commands include clear, delete, updateWish, add, save, removeTagFromAll, etc.
 */
public interface ActionCommandListener<T> {

    /**
     * Called when a command attempts to add a wish to the wish book.
     */
    void addWish(Wish wish);

    /**
     * Called when a command attempts to replace the given wish {@code target} with {@code editedWish}.
     * {@code target} must exist in the wish book.
     *
     */
    void updateWish(Wish target, Wish editedWish);

    /**
     * Called when a command attempts to remove {@code key} from this {@code WishBook}.
     * {@code key} must exist in the wish book.
     */
    void removeWish(Wish wish);

    /**
     * Called action command clear is executed.
     * @param newData newData to replace the model with.
     */
    void resetData(T newData);

    /**
     * Removes {@code tag} from all {@code wish}s in this {@code WishBook}.
     */
    void removeTagFromAll(Tag tag);
}
