package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
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

        this.populatedVersionedWishTransaction = new VersionedWishTransaction(getTypicalWishTransaction());
    }

    @Test
    public void undo_shouldFail() {
        assertThrows(VersionedWishTransaction.NoUndoableStateException.class, versionedWishTransaction::undo);
    }

    @Test
    public void redo_shouldFail() {
        assertThrows(VersionedWishTransaction.NoRedoableStateException.class, versionedWishTransaction::redo);
    }

    @Test
    public void addWishWithoutCommit_shouldFail() {
        versionedWishTransaction.addWish(wish);
        assertTrue(isSameSize(1));

        VersionedWishTransaction copy = new VersionedWishTransaction(getTypicalWishTransaction());
        populatedVersionedWishTransaction.addWish(wish);
        assertEquals(populatedVersionedWishTransaction, copy);
    }
    @Test
    public void addWishWithCommit_success() {
        addWishWithCommit();
        assertTrue(isSameSize(2));
    }

    @Test
    public void addMultipleWishesWithCommit_success() {
        getTypicalWishes().stream().forEach(wish -> {
            versionedWishTransaction.addWish(wish);
            versionedWishTransaction.commit();
        });
        assertEquals(versionedWishTransaction, populatedVersionedWishTransaction);
    }

    @Test
    public void removeWish_success() {
        addWishWithCommit();
        removeWishWithCommit();
        assertTrue(isSameSize(3));
    }

    @Test
    public void removeAllWishes_success() {
        getTypicalWishes().stream().forEach(wish -> {
            populatedVersionedWishTransaction.removeWish(wish);
            populatedVersionedWishTransaction.commit();
        });
        assertEquals(versionedWishTransaction, populatedVersionedWishTransaction);
    }

    @Test
    public void undo_shouldSucceed() {
        addWishWithCommit();
        versionedWishTransaction.undo();

        assertTrue(isSameSize(2));
    }

    @Test
    public void multipleUndos_shouldSucceed() {
        List<Wish> wishes = getTypicalWishes();
        int limit = wishes.size() / 2;
        VersionedWishTransaction blank = new VersionedWishTransaction();
        for (int i = 0; i < limit; i++) {
            versionedWishTransaction.addWish(wishes.get(i));
            versionedWishTransaction.commit();
            versionedWishTransaction.undo();
        }
        assertEquals(versionedWishTransaction, blank);

        versionedWishTransaction = new VersionedWishTransaction();
        assertTrue(isSameSize(1));

        for (int i = 0; i < limit; i++) {
            int prevSize = versionedWishTransaction.getWishStateList().size();
            versionedWishTransaction.addWish(wishes.get(i));
            versionedWishTransaction.commit();
            versionedWishTransaction.removeWish(wishes.get(i));
            versionedWishTransaction.commit();
            versionedWishTransaction.undo();
            int currSize = versionedWishTransaction.getWishStateList().size();
            if (i > 0) assertEquals(currSize - prevSize, 1);
        }

    }

    @Test
    public void redo_shouldSucceed() {
        addWishWithCommit();
        versionedWishTransaction.undo();
        versionedWishTransaction.redo();
        assertTrue(isSameSize(2));
    }

    @Test
    public void commit_shouldSucceed() {
        addWishWithCommit();
        assertTrue(isSameSize(2));
    }

    private boolean isSameSize(int size) {
        return versionedWishTransaction.getWishStateList().size() == size;
    }

    /**
     * Commit to save new wish transaction state.
     */
    private void addWishWithCommit() {
        versionedWishTransaction.addWish(wish);
        versionedWishTransaction.commit();
    }

    private void removeWishWithCommit() {
        versionedWishTransaction.removeWish(wish);
        versionedWishTransaction.commit();
    }

}
