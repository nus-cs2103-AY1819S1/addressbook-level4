package seedu.address.model.versionedmodels;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.ReadOnlyWishBook;
import seedu.address.model.WishTransaction;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Wish;

/**
 * This class keeps track of the saving history for each wish across each executed command.
 */
public class VersionedWishTransaction extends WishTransaction implements VersionedModel {

    /**
     * Stores the log of wish histories for each state.
     */
    private List<WishTransaction> wishStateList;

    /**
     * Index to the current referenced state.
     */
    private int referencePointer;

    /**
     * The initial state of this wishStateList will always be empty.
     */
    public VersionedWishTransaction() {
        wishStateList = new ArrayList<>();
        wishStateList.add(new WishTransaction());
        referencePointer = 0;
    }

    /**
     * The initial state of this wishStateList will always be {@code wishTransaction}
     * @param wishTransaction saved copy of {@code WishTransaction}.
     * Note: {@code wishTransaction} can either be of the parent or child class ({@code VersionedWishTransaction}).
     */
    public VersionedWishTransaction(WishTransaction wishTransaction) {
        super(wishTransaction);
        wishStateList = new ArrayList<>();
        wishStateList.add(getCopy(wishTransaction));
        referencePointer = 0;
    }

    /**
     * Creates a VersionedWishTransaction object from a ReadOnlyWishBook object.
     * @param wishBook object containing data to seed this object with.
     */
    public VersionedWishTransaction(ReadOnlyWishBook wishBook) {
        extractData(wishBook);
        wishStateList = new ArrayList<>();
        wishStateList.add(getCopy(this));
        referencePointer = 0;
    }

    @Override
    protected void extractData(ReadOnlyWishBook wishBook) {
        for (Wish wish : wishBook.getWishList()) {
            super.addWish(wish);
        }
    }

    @Override
    public void commit() {
        if (!hasNothingToRemove()) {
            removeStatesAfterCurrentPointer();
        }
        wishStateList.add(getCopy(this));
        referencePointer++;
    }

    @Override
    public void removeWish(Wish wish) {
        getCurrentState();
        super.removeWish(wish);
    }

    @Override
    public void removeTagFromAll(Tag tag) {
        getCurrentState();
        super.removeTagFromAll(tag);
    }

    @Override
    public void addWish(Wish wish) {
        getCurrentState();
        super.addWish(wish);
    }

    @Override
    public void updateWish(Wish target, Wish editedWish) {
        getCurrentState();
        super.updateWish(target, editedWish);
    }

    @Override
    public void resetData() {
        getCurrentState();
        super.resetData();
    }

    /**
     * Commits the previous state of the {@code wishmap} and sets the current state.
     */
    private void getCurrentState() {
        commit();
        resetData(wishStateList.get(referencePointer));
    }

    private void removeStatesAfterCurrentPointer() {
        wishStateList.subList(referencePointer + 1, wishStateList.size()).clear();
    }

    private boolean hasNothingToRemove() {
        return referencePointer >= wishStateList.size();
    }

    @Override
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        referencePointer--;
        resetData(wishStateList.get(referencePointer));
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        referencePointer++;
        resetData(wishStateList.get(referencePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return referencePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return referencePointer < wishStateList.size() - 1;
    }

    public List<WishTransaction> getWishStateList() {
        return wishStateList;
    }

    public int getReferencePointer() {
        return referencePointer;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VersionedWishTransaction)) {
            return false;
        }

        return ((VersionedWishTransaction) obj).getWishStateList().size() == wishStateList.size()
                && ((VersionedWishTransaction) obj).getWishMap().equals(wishMap);
    }
}
