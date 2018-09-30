package seedu.address.model.VersionedModels;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.WishTransaction;

/**
 * This class keeps track of the saving history for each wish across each executed command.
 */
public class VersionedWishTransaction extends WishTransaction implements VersionedModel {

    /**
     * Stores the log of wish histories for each state.
     */
    private final List<WishTransaction> wishStateList;

    /**
     * Index to the current referenced state.
     */
    private int referencePointer;

    /**
     * The initial state of this wishStateList will always be empty.
     */
    public VersionedWishTransaction() {
        wishStateList = new ArrayList<>();
        wishStateList.add(this);
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
        wishStateList.add(wishTransaction);
        referencePointer = 0;
    }

    @Override
    public void commit() {
        if (!hasNothingToRemove()) {
            removeStatesAfterCurrentPointer();
        }
        wishStateList.add(this);
        referencePointer++;
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

}
