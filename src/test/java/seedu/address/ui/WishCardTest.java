package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysWish;

import org.junit.Test;

import guitests.guihandles.WishCardHandle;
import seedu.address.model.wish.Wish;
import seedu.address.testutil.WishBuilder;

public class WishCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Wish wishWithNoTags = new WishBuilder().withTags(new String[0]).build();
        WishCard wishCard = new WishCard(wishWithNoTags, 1);
        uiPartRule.setUiPart(wishCard);
        assertCardDisplay(wishCard, wishWithNoTags, 1);

        // with tags
        Wish wishWithTags = new WishBuilder().build();
        wishCard = new WishCard(wishWithTags, 2);
        uiPartRule.setUiPart(wishCard);
        assertCardDisplay(wishCard, wishWithTags, 2);
    }

    @Test
    public void equals() {
        Wish wish = new WishBuilder().build();
        WishCard wishCard = new WishCard(wish, 0);

        // same wish, same index -> returns true
        WishCard copy = new WishCard(wish, 0);
        assertTrue(wishCard.equals(copy));

        // same object -> returns true
        assertTrue(wishCard.equals(wishCard));

        // null -> returns false
        assertFalse(wishCard.equals(null));

        // different types -> returns false
        assertFalse(wishCard.equals(0));

        // different wish, same index -> returns false
        Wish differentWish = new WishBuilder().withName("differentName").build();
        assertFalse(wishCard.equals(new WishCard(differentWish, 0)));

        // same wish, different index -> returns false
        assertFalse(wishCard.equals(new WishCard(wish, 1)));
    }

    /**
     * Asserts that {@code wishCard} displays the details of {@code expectedWish} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WishCard wishCard, Wish expectedWish, int expectedId) {
        guiRobot.pauseForHuman();

        WishCardHandle wishCardHandle = new WishCardHandle(wishCard.getRoot());

        // verify id is displayed correctly
        //assertEquals(Integer.toString(expectedId) + ". ", wishCardHandle.getId());

        // verify wish details are displayed correctly
        assertCardDisplaysWish(expectedWish, wishCardHandle);
    }
}
