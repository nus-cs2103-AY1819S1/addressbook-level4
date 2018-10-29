package seedu.address.model.wish;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.address.testutil.TypicalWishes.ALICE;
import static seedu.address.testutil.TypicalWishes.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.WishBuilder;

public class WishTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Wish wish = new WishBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        wish.getTags().remove(0);
    }

    @Test
    public void isSameWish() {
        // same object -> returns true
        assertTrue(ALICE.isSameWish(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameWish(null));

        // different price and email, same id -> returns true
        Wish editedAlice = new WishBuilder(ALICE).withPrice(VALID_PRICE_BOB).withDate(VALID_DATE_1).build();
        assertTrue(ALICE.isSameWish(editedAlice));

        // different name, same id -> returns true
        editedAlice = new WishBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameWish(editedAlice));

        // same name, same price, different attributes, same id -> returns true
        editedAlice = new WishBuilder(ALICE).withDate(VALID_DATE_2).withUrl(VALID_URL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWish(editedAlice));

        // same name, same email, different attributes, same id -> returns true
        editedAlice = new WishBuilder(ALICE).withPrice(VALID_PRICE_BOB).withUrl(VALID_URL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWish(editedAlice));

        // same name, same price, same email, different attributes, same id -> returns true
        editedAlice = new WishBuilder(ALICE).withUrl(VALID_URL_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWish(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Wish aliceCopy = new WishBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different wish -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Wish editedAlice = new WishBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different price -> returns false
        editedAlice = new WishBuilder(ALICE).withPrice(VALID_PRICE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new WishBuilder(ALICE).withDate(VALID_DATE_2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new WishBuilder(ALICE).withUrl(VALID_URL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new WishBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
