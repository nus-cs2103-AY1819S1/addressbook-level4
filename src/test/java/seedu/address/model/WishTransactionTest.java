package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

public class WishTransactionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private WishTransaction wishTransaction;
    private Wish wish1;
    private Wish wish2;

    @Before
    public void init() {
        wishTransaction = new WishTransaction();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("wish1"));
        this.wish1 = new Wish(new Name("wish1"),
                new Price("81320902"),
                new Email("wish1@gmail.com"),
                new Url("https://redmart.com/marketplace/lw-roasted-meat"),
                new SavedAmount("0"),
                new Remark("e"),
                tagSet);
        this.wish2 = new Wish(new Name("wish1"),
                new Price("81320902"),
                new Email("wish1@gmail.com"),
                new Url("https://redmart.com/marketplace/lw-roasted-meat"),
                new SavedAmount("0"),
                new Remark("f"),
                tagSet);
    }

    @Test
    public void addWish_success() {
        wishTransaction.addWish(wish1);
        assertTrue(isSameSize(1));
    }

    @Test
    public void allowMultipleWishesOfSameName() {
        wishTransaction.addWish(wish1);
        wishTransaction.addWish(wish2);
        assertTrue(isSameSize(2));
    }

    @Test
    public void removeWish_success() {
        wishTransaction.addWish(wish1);
        wishTransaction.removeWish(wish1);
        assertTrue(isSameSize(0));
    }

    @Test
    public void removeNonExistentialWish_shouldFail() {
        wishTransaction.addWish(wish1);
    }

    @Test
    public void updateWish_success() {
        wishTransaction.addWish(wish1);
        wishTransaction.updateWish(wish1, wish2);
        assertEquals(wish1, wish2);
    }

    @Test
    public void resetData_success() {
        wishTransaction.addWish(wish1);
        assertTrue(isSameSize(1));
        wishTransaction.resetData(new WishTransaction());
        assertTrue(isSameSize(0));
    }

    /**
     * Checks if size of list is equal to {@code size}.
     * @param size expected size of list.
     * @return true if size of list is expected, false otherwise.
     */
    private boolean isSameSize(int size) {
        return wishTransaction.getWishes().size() == size;
    }

}
