package seedu.parking.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.ui.testutil.GuiTestAssert.assertCardDisplaysCarpark;

import org.junit.Test;

import guitests.guihandles.CarparkCardHandle;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.testutil.CarparkBuilder;

public class CarparkCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Carpark carparkWithNoTags = new CarparkBuilder().withTags(new String[0]).build();
        CarparkCard carparkCard = new CarparkCard(carparkWithNoTags, 1);
        uiPartRule.setUiPart(carparkCard);
        assertCardDisplay(carparkCard, carparkWithNoTags, 1);

        // with tags
        Carpark carparkWithTags = new CarparkBuilder().build();
        carparkCard = new CarparkCard(carparkWithTags, 2);
        uiPartRule.setUiPart(carparkCard);
        assertCardDisplay(carparkCard, carparkWithTags, 2);
    }

    @Test
    public void equals() {
        Carpark carpark = new CarparkBuilder().build();
        CarparkCard carparkCard = new CarparkCard(carpark, 0);

        // same carpark, same index -> returns true
        CarparkCard copy = new CarparkCard(carpark, 0);
        assertTrue(carparkCard.equals(copy));

        // same object -> returns true
        assertTrue(carparkCard.equals(carparkCard));

        // null -> returns false
        assertFalse(carparkCard.equals(null));

        // different types -> returns false
        assertFalse(carparkCard.equals(0));

        // different carpark, same index -> returns false
        Carpark differentCarpark = new CarparkBuilder().withCarparkNumber("differentName").build();
        assertFalse(carparkCard.equals(new CarparkCard(differentCarpark, 0)));

        // same carpark, different index -> returns false
        assertFalse(carparkCard.equals(new CarparkCard(carpark, 1)));
    }

    /**
     * Asserts that {@code carparkCard} displays the details of {@code expectedCarpark} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CarparkCard carparkCard, Carpark expectedCarpark, int expectedId) {
        guiRobot.pauseForHuman();

        CarparkCardHandle carparkCardHandle = new CarparkCardHandle (carparkCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", carparkCardHandle.getId());

        // verify carpark details are displayed correctly
        assertCardDisplaysCarpark(expectedCarpark, carparkCardHandle);
    }
}
