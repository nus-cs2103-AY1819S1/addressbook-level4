package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWishes.getTypicalWishBook;
import static seedu.address.testutil.TypicalWishes.getTypicalWishTransaction;
import static seedu.address.testutil.TypicalWishes.getTypicalWishes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.versionedmodels.VersionedWishTransaction;
import seedu.address.model.wish.Date;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

public class VersionedWishTransactionTest {

    private VersionedWishTransaction versionedWishTransaction;
    private VersionedWishTransaction populatedVersionedWishTransaction;
    private VersionedWishTransaction fromWishBook;
    private Wish wish;

    @Before
    public void init() {
        this.versionedWishTransaction = new VersionedWishTransaction();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("wish1"));
        this.wish = Wish.createWish(new Name("wish1"),
                new Price("81320902"),
                new Date("22/09/2023"),
                new Url("https://redmart.com/marketplace/lw-roasted-meat"),
                new SavedAmount("0"),
                new Remark("e"),
                tagSet);

        this.fromWishBook = new VersionedWishTransaction(getTypicalWishBook());
        this.populatedVersionedWishTransaction = new VersionedWishTransaction(getTypicalWishTransaction());
    }

    @Test
    public void constructorShouldCreateCorrectly() {
        assertTrue(isSameSize(1));
        assertTrue(versionedWishTransaction.getReferencePointer() == 0);

        assertTrue(populatedVersionedWishTransaction.getWishStateList().size() == 1);
        assertTrue(populatedVersionedWishTransaction.getReferencePointer() == 0);

        assertTrue(fromWishBook.getWishStateList().size() == 1);
        assertTrue(fromWishBook.getReferencePointer() == 0);
    }

    @Test
    public void shouldBeAbleToUndoNotRedo() {
        assertFalse(versionedWishTransaction.canUndo());
        assertFalse(versionedWishTransaction.canRedo());
    }

    @Test
    public void shouldBeAbleToRedo() {
        addWishWithCommit(versionedWishTransaction, wish);
        versionedWishTransaction.undo();
        assertTrue(versionedWishTransaction.canRedo());
    }

    @Test
    public void shouldBeEqual() {
        assertTrue(versionedWishTransaction.equals(new VersionedWishTransaction()));
        assertTrue(populatedVersionedWishTransaction.equals(fromWishBook));
    }

    @Test
    public void undo_shouldFail() {
        assertThrows(VersionedWishTransaction.NoUndoableStateException.class, versionedWishTransaction::undo);
    }

    @Test
    public void redo_shouldFail() {
        assertThrows(VersionedWishTransaction.NoRedoableStateException.class, versionedWishTransaction::redo);
    }

    private boolean wishmapContainsWish(VersionedWishTransaction versionedWishTransaction, Wish wish) {
        return versionedWishTransaction.getWishMap().get(wish.getId()).peekLast().isSameWish(wish);
    }

    @Test
    public void addWishWithCommit_success() {
        int prevWishStateListSize = getSizeOfWishStateList(versionedWishTransaction);
        addWishWithCommit(versionedWishTransaction, wish);
        assertTrue(wishmapContainsKey(versionedWishTransaction, wish));
        assertTrue(isSameSize(prevWishStateListSize + 1));

        VersionedWishTransaction copy = new VersionedWishTransaction(getTypicalWishTransaction());
        int wishStateListSize = getSizeOfWishStateList(copy);
        addWishWithCommit(populatedVersionedWishTransaction, wish);
        assertTrue(wishmapContainsKey(populatedVersionedWishTransaction, wish));
        assertTrue(wishmapContainsWish(populatedVersionedWishTransaction, wish));
        assertNotEquals(populatedVersionedWishTransaction, copy);
        assertEquals(getSizeOfWishStateList(copy), wishStateListSize);
    }

    @Test
    public void addMultipleWishesWithCommit_success() {
        getTypicalWishes().stream().forEach(wish -> {
            int prevWishStateListSize = getSizeOfWishStateList(versionedWishTransaction);
            addWishWithCommit(versionedWishTransaction, wish);
            assertEquals(getSizeOfWishStateList(versionedWishTransaction), prevWishStateListSize + 1);
        });
        assertEquals(versionedWishTransaction.getWishMap(), populatedVersionedWishTransaction.getWishMap());
        assertNotEquals(versionedWishTransaction, populatedVersionedWishTransaction);
    }

    @Test
    public void removeWish_success() {
        int prevWishStateListSize = getSizeOfWishStateList(versionedWishTransaction);
        addWishWithCommit(versionedWishTransaction, wish);
        removeWishWithCommit(versionedWishTransaction, wish);
        assertFalse(wishmapContainsKey(versionedWishTransaction, wish));
        assertEquals(getSizeOfWishStateList(versionedWishTransaction), prevWishStateListSize + 2);
    }

    @Test
    public void removeAllWishes_success() {
        getTypicalWishes().stream().forEach(wish -> {
            int prevWishStateListSize = getSizeOfWishStateList(populatedVersionedWishTransaction);
            removeWishWithCommit(populatedVersionedWishTransaction, wish);
            assertEquals(getSizeOfWishStateList(populatedVersionedWishTransaction), prevWishStateListSize + 1);
        });
        assertEquals(versionedWishTransaction.getWishMap(), populatedVersionedWishTransaction.getWishMap());
    }

    @Test
    public void undo_shouldSucceed() {
        int prevWishStateListSize = getSizeOfWishStateList(versionedWishTransaction);
        addWishWithCommit(versionedWishTransaction, wish);

        Wish addedWish = getTypicalWishes().get(0);
        addWishWithCommit(versionedWishTransaction, addedWish);
        assertEquals(getSizeOfWishStateList(versionedWishTransaction), prevWishStateListSize + 2);

        int prevRefPtr = getIndexOfReferencePtr(versionedWishTransaction);
        versionedWishTransaction.undo();
        assertEquals(getIndexOfReferencePtr(versionedWishTransaction), prevRefPtr - 1);
        assertFalse(wishmapContainsKey(versionedWishTransaction.getWishStateList().get(prevRefPtr - 1), addedWish));
    }

    /**
     * Retrieves the index of the reference pointer for the wish state list.
     */
    private int getIndexOfReferencePtr(VersionedWishTransaction versionedWishTransaction) {
        return versionedWishTransaction.getReferencePointer();
    }

    @Test
    public void multipleUndos_shouldSucceed() {
        List<Wish> wishes = getTypicalWishes();
        int limit = wishes.size() / 2;
        VersionedWishTransaction blank = new VersionedWishTransaction();
        addWishWithCommit(versionedWishTransaction, wish);
        addWishWithCommit(versionedWishTransaction, getTypicalWishes().get(0));
        versionedWishTransaction.undo();
        assertFalse(wishmapContainsKey(versionedWishTransaction, getTypicalWishes().get(0)));

        versionedWishTransaction = new VersionedWishTransaction();
        for (int i = 0; i < limit; i++) {
            addWishWithCommit(versionedWishTransaction, wishes.get(i));
        }
        for (int i = 0; i < limit; i++) {
            versionedWishTransaction.undo();
        }
        assertEquals(versionedWishTransaction.wishMap, blank.wishMap);

        versionedWishTransaction = new VersionedWishTransaction();
        int prevSize = getSizeOfWishStateList(versionedWishTransaction);
        addWishWithCommit(versionedWishTransaction, wish);
        removeWishWithCommit(versionedWishTransaction, wish);
        versionedWishTransaction.undo();
        assertEquals(getSizeOfWishStateList(versionedWishTransaction), prevSize + 2);
        assertTrue(wishmapContainsKey(versionedWishTransaction.getWishStateList().get(prevSize), wish));
    }

    @Test
    public void redo_shouldSucceed() {
        addWishWithCommit(versionedWishTransaction, wish);
        versionedWishTransaction.undo();
        assertFalse(wishmapContainsKey(versionedWishTransaction, wish));
        versionedWishTransaction.redo();
        assertTrue(wishmapContainsKey(versionedWishTransaction, wish));

        Wish removed = getTypicalWishes().get(0);
        addWishWithCommit(versionedWishTransaction, removed);
        assertTrue(wishmapContainsKey(versionedWishTransaction, removed));
        removeWishWithCommit(versionedWishTransaction, removed);
        assertFalse(wishmapContainsKey(versionedWishTransaction, removed));
        versionedWishTransaction.undo();
        assertTrue(wishmapContainsKey(versionedWishTransaction, removed));
        versionedWishTransaction.redo();
        assertFalse(wishmapContainsKey(versionedWishTransaction, removed));

        populatedVersionedWishTransaction.resetData();
        assertTrue(populatedVersionedWishTransaction.wishMap.isEmpty());
        populatedVersionedWishTransaction.undo();
        assertFalse(populatedVersionedWishTransaction.wishMap.isEmpty());
        populatedVersionedWishTransaction.redo();
        assertTrue(populatedVersionedWishTransaction.wishMap.isEmpty());
    }

    @Test
    public void commit_shouldSucceed() {
        addWishWithCommit(versionedWishTransaction, wish);
        assertTrue(isSameSize(2));
    }

    private boolean isSameSize(int size) {
        return versionedWishTransaction.getWishStateList().size() == size;
    }

    private int getSizeOfWishStateList(VersionedWishTransaction versionedWishTransaction) {
        return versionedWishTransaction.getWishStateList().size();
    }

    /**
     * Checks if wishmap for this {@code wishTransaction} object contains the given {@code wish}.
     *
     * @return true if the given {@code wish} is found, false otherwise.
     */
    private boolean wishmapContainsKey(WishTransaction wishTransaction, Wish wish) {
        return wishTransaction.wishMap.containsKey(wish.getId());
    }

    /**
     * @see VersionedWishTransactionTest#wishmapContainsKey(WishTransaction, Wish)
     */
    private boolean wishmapContainsKey(VersionedWishTransaction versionedWishTransaction, Wish wish) {
        return versionedWishTransaction.getWishMap().containsKey(wish.getId());
    }

    /**
     * Commit to save new wish transaction state.
     */
    private void addWishWithCommit(VersionedWishTransaction versionedWishTransaction, Wish wish) {
        versionedWishTransaction.addWish(wish);
    }

    private void removeWishWithCommit(VersionedWishTransaction versionedWishTransaction, Wish wish) {
        versionedWishTransaction.removeWish(wish);
    }

}
