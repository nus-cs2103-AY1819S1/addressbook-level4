package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

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


    public VersionedWishTransaction() {
        wishStateList = new ArrayList<>();
        referencePointer = -1;
    }

    public VersionedWishTransaction(WishTransaction wishTransaction) {
        super(wishTransaction);
        wishStateList = new ArrayList<>();
        wishStateList.add(wishTransaction);
        referencePointer = 0;
    }

    @Override
    public void commit() {
        removeStatesAfterCurrentPointer();
        wishStateList.add(this);
        referencePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        wishStateList.subList(referencePointer + 1, wishStateList.size()).clear();
    }

    @Override
    public void undo() {
        referencePointer--;
        resetData(wishStateList.get(referencePointer));
    }

    @Override
    public void redo() {
        referencePointer++;
        resetData(wishStateList.get(referencePointer));
    }
}
