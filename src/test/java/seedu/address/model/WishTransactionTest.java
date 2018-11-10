package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalWishes.getTypicalWishBook;
import static seedu.address.testutil.TypicalWishes.getTypicalWishTransaction;
import static seedu.address.testutil.TypicalWishes.getTypicalWishes;

import java.util.HashMap;
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

    private WishTransaction emptyWishTransaction;
    private WishTransaction populatedWishTransaction;
    private Wish wish1;
    private Wish wish2;

    @Before
    public void init() {
        emptyWishTransaction = new WishTransaction();
        populatedWishTransaction = getTypicalWishTransaction();
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
    public void constructorShouldCreateCorrectObject() {
        assertEquals(emptyWishTransaction.getWishMap(), new HashMap<>());
        assertEquals(populatedWishTransaction, getPopulatedWishTransaction());
        assertEquals(new WishTransaction(populatedWishTransaction), populatedWishTransaction);
        assertEquals(populatedWishTransaction, new WishTransaction(getTypicalWishBook()));
        assertEquals(populatedWishTransaction, new WishTransaction(populatedWishTransaction.getWishMap()));
    }

    private WishTransaction getPopulatedWishTransaction() {
        WishTransaction wishTransaction = new WishTransaction();
        getTypicalWishes().stream()
                .forEach(wish -> wishTransaction.addWish(wish));
        return wishTransaction;
    }

    @Test
    public void shouldHaveExactCopy() {
        assertEquals(emptyWishTransaction, emptyWishTransaction.getCopy(emptyWishTransaction));
        assertEquals(populatedWishTransaction, populatedWishTransaction.getCopy(populatedWishTransaction));
    }

    @Test
    public void addWishShouldBeSuccessful() {
        emptyWishTransaction.addWish(wish1);
        assertTrue(isSameSize(wish1, 1));
        assertTrue(wishmapContainsKey(emptyWishTransaction, wish1));
        assertTrue(wishmapContainsWish(emptyWishTransaction, wish1));
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
        emptyWishTransaction.addWish(wish1);
        emptyWishTransaction.addWish(wish2);
        // should not be mapped to same key
        assertFalse(isSameSize(wish1, 2));

        // should contain 2 new distinct wishes
        assertEquals(emptyWishTransaction.getWishMap().size(), 2);
        assertTrue(wishmapContainsKey(emptyWishTransaction, wish1));
        assertTrue(wishmapContainsKey(emptyWishTransaction, wish2));
        assertTrue(wishmapContainsWish(emptyWishTransaction, wish1));
        assertTrue(wishmapContainsWish(emptyWishTransaction, wish2));
    }

    @Test
    public void removeWishShouldBeSuccessful() {
        emptyWishTransaction.addWish(wish1);
        emptyWishTransaction.removeWish(wish1);
        assertFalse(isFound(wish1));
    }

    @Test
    public void removeNonExistentialWishShouldFail() {
        assertFalse(wishmapContainsKey(emptyWishTransaction, wish1));
        int prevSize = emptyWishTransaction.wishMap.size();
        emptyWishTransaction.removeWish(wish1);
        assertEquals(prevSize, emptyWishTransaction.wishMap.size());
    }

    @Test
    public void updateWishShouldBeSuccessful() {
        emptyWishTransaction.addWish(wish1);
        emptyWishTransaction.updateWish(wish1, wish2);
        assertTrue(emptyWishTransaction.getWishMap().get(getKey(wish1)).peekLast().isSameWish(wish2));
    }

    @Test
    public void resetDataShouldBeSuccessful() {
        emptyWishTransaction.addWish(wish1);
        assertTrue(isFound(wish1));
        emptyWishTransaction.resetData();
        assertFalse(isFound(wish1));
        assertTrue(emptyWishTransaction.wishMap.isEmpty());
    }

    @Test
    public void removeTagFromAllShouldBeSuccessful() {
        Set<Tag> tags = getTypicalWishes().get(0).getTags();
        tags.forEach(tag -> populatedWishTransaction.removeTagFromAll(tag));
        populatedWishTransaction.getWishMap().entrySet()
                .forEach(entry -> {
                    assertFalse(entry.getValue().peekLast().getTags().contains(tags));
                });
    }

    @Test
    public void wishMapShouldBeSetSuccessfully() {
        emptyWishTransaction.setWishMap(populatedWishTransaction.getWishMap());
        assertEquals(emptyWishTransaction.getWishMap(), populatedWishTransaction.getWishMap());
    }

    @Test
    public void wishMapIsEmptyShouldTestCorrectly() {
        assertTrue(emptyWishTransaction.isEmpty());
        assertFalse(populatedWishTransaction.isEmpty());
    }

    @Test
    public void shouldBeEqual() {
        assertTrue(populatedWishTransaction.equals(getTypicalWishTransaction()));
        assertTrue(emptyWishTransaction.equals(new WishTransaction()));
    }

    /**
     * Checks if emptyWishTransaction contains the given wish.
     * @param wish wish to check if is contained within emptyWishTransaction.
     * @return true if wish is found, false otherwise.
     */
    private boolean isFound(Wish wish) {
        return emptyWishTransaction.getWishMap().containsKey(getKey(wish));
    }

    /**
     * Checks if size of list is equal to {@code size}.
     * @param size expected size of list.
     * @return true if size of list is expected, false otherwise.
     */
    private boolean isSameSize(Wish queried, int size) {
        List<Wish> wishes = emptyWishTransaction.getWishMap().get(getKey(queried));
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
