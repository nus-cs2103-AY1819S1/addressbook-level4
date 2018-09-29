package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of the saving history for each wish across each executed command.
 */
public class VersionedWishTransactions extends WishTransaction implements VersionedModel {

    private final List<WishTransaction> wishStateList;
    private int referencePointer;

    public VersionedWishTransactions() {
        wishStateList = new ArrayList<>();
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
