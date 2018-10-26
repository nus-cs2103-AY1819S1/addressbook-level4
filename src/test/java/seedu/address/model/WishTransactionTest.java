package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Date;
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
        this.wish1 = Wish.createWish(new Name("wish1"),
                new Price("81320902"),
                new Date("29/11/2019"),
                new Url("https://redmart.com/marketplace/lw-roasted-meat"),
                new SavedAmount("0"),
                new Remark("e"),
                tagSet);
        this.wish2 = Wish.createWish(new Name("wish1"),
                new Price("81320902"),
                new Date("29/11/2019"),
                new Url("https://redmart.com/marketplace/lw-roasted-meat"),
                new SavedAmount("0"),
                new Remark("f"),
                tagSet);
    }

    @Test
    public void addWish_success() {
        wishTransaction.addWish(wish1);
        assertTrue(isSameSize(wish1, 1));
        assertTrue(wishmapContainsKey(wishTransaction, wish1));
        assertTrue(wishmapContainsWish(wishTransaction, wish1));
    }

    private boolean wishmapContainsKey(WishTransaction wishTransaction, Wish wish) {
        return wishTransaction.wishMap.containsKey(wish.getId());
    }

    private boolean wishmapContainsWish(WishTransaction wishTransaction, Wish wish) {
        return wishTransaction.wishMap.get(wish.getId()).peekLast().isSameWish(wish);
    }

    @Test
    public void allowMultipleWishesOfSameName() {
        assertNotEquals(wish1.getId(), wish2.getId());
        wishTransaction.addWish(wish1);
        wishTransaction.addWish(wish2);
        // should not be mapped to same key
        assertFalse(isSameSize(wish1, 2));

        // should contain 2 new distinct wishes
        assertEquals(wishTransaction.getWishMap().size(), 2);
        assertTrue(wishmapContainsKey(wishTransaction, wish1));
        assertTrue(wishmapContainsKey(wishTransaction, wish2));
        assertTrue(wishmapContainsWish(wishTransaction, wish1));
        assertTrue(wishmapContainsWish(wishTransaction, wish2));
    }

    @Test
    public void removeWish_success() {
        wishTransaction.addWish(wish1);
        wishTransaction.removeWish(wish1);
        assertFalse(isFound(wish1));
    }

    @Test
    public void removeNonExistentialWish_shouldFail() {
        assertFalse(wishmapContainsKey(wishTransaction, wish1));
        int prevSize = wishTransaction.wishMap.size();
        wishTransaction.removeWish(wish1);
        assertEquals(prevSize, wishTransaction.wishMap.size());
    }

    @Test
    public void updateWish_success() {
        wishTransaction.addWish(wish1);
        wishTransaction.updateWish(wish1, wish2);
        assertTrue(wishmapContainsKey(wishTransaction, wish2));
        assertTrue(wishmapContainsWish(wishTransaction, wish2));
    }

    @Test
    public void resetData_success() {
        wishTransaction.addWish(wish1);
        assertTrue(isFound(wish1));
        wishTransaction.resetData();
        assertFalse(isFound(wish1));
        assertTrue(wishTransaction.wishMap.isEmpty());
    }

    /**
     * Checks if wishTransaction contains the given wish.
     * @param wish wish to check if is contained within wishTransaction.
     * @return true if wish is found, false otherwise.
     */
    private boolean isFound(Wish wish) {
        return wishTransaction.getWishMap().containsKey(getKey(wish));
    }

    /**
     * Checks if size of list is equal to {@code size}.
     * @param size expected size of list.
     * @return true if size of list is expected, false otherwise.
     */
    private boolean isSameSize(Wish queried, int size) {
        List<Wish> wishes = wishTransaction.getWishMap().get(getKey(queried));
        return wishes.size() == size;
    }

    /**
     * Returns the key corresponding to this wish.
     * @param wish queried wish.
     * @return key for the corresponding wish.
     */
    private UUID getKey(Wish wish) {
        return wish.getId();
    }

}
