package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WISHES;
import static seedu.address.testutil.TypicalWishes.ALICE;
import static seedu.address.testutil.TypicalWishes.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.Tag;
import seedu.address.model.wish.WishContainsKeywordsPredicate;
import seedu.address.testutil.WishBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasWish_nullWish_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasWish(null);
    }

    @Test
    public void hasWish_wishNotInWishBook_returnsFalse() {
        assertFalse(modelManager.hasWish(ALICE));
    }

    @Test
    public void hasWish_wishInWishBook_returnsTrue() {
        modelManager.addWish(ALICE);
        assertTrue(modelManager.hasWish(ALICE));
    }

    @Test
    public void hasTagAfterRemoveTag() {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        modelManager.addWish(BENSON);
        modelManager.deleteTag(tag);
        assertFalse(modelManager
                .getWishBook()
                .getWishList()
                .filtered(wish ->
                        wish.getTags().contains(tag)).size() > 0);
    }

    @Test
    public void getFilteredWishList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredSortedWishList().remove(0);
    }

    @Test
    public void equals() {
        WishBook wishBook = new WishBookBuilder().withWish(ALICE).withWish(BENSON).build();
        WishTransaction wishTransaction = new WishTransaction(wishBook);
        WishBook differentWishBook = new WishBook();
        WishTransaction differentWishTransaction = new WishTransaction();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(wishBook, wishTransaction, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(wishBook, wishTransaction, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different wishBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentWishBook, differentWishTransaction, userPrefs)));

        // different filteredList -> returns false
        String[] nameKeywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredWishList(new WishContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                Arrays.asList(), Arrays.asList(), true));
        assertFalse(modelManager.equals(new ModelManager(wishBook, wishTransaction, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredWishList(PREDICATE_SHOW_ALL_WISHES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setWishBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(wishBook, wishTransaction, differentUserPrefs)));
    }
}
