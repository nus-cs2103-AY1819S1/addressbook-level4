package seedu.address.model;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Address;
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Phone;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.Wish;

public class VersionedWishTransactionTest {

    private VersionedWishTransaction versionedWishTransaction;
    private Wish wish;

    @Before
    public void init() {
        this.versionedWishTransaction = new VersionedWishTransaction();
        Set<Tag> tagSet = new HashSet<>();
        ((HashSet) tagSet).add(new Tag("wish1"));
        this.wish = new Wish(new Name("wish1"),
                new Phone("81320902"),
                new Email("wish1@gmail.com"),
                new Address("Blk 102 Simei Avenue"),
                new Remark("e"),
                tagSet);
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
    }
    @Test
    public void addWishWithCommit_success() {
        addWishWithCommit();
        assertTrue(isSameSize(2));
    }

    @Test
    public void removeWish_success() {
        addWishWithCommit();
        removeWishWithCommit();
        assertTrue(isSameSize(3));
    }

    @Test
    public void undo_shouldSucceed() {
        addWishWithCommit();
        versionedWishTransaction.undo();

        assertTrue(isSameSize(2));
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
