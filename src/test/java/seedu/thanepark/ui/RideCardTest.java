package seedu.thanepark.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.ui.testutil.GuiTestAssert.assertCardDisplaysRide;

import org.junit.Test;

import guitests.guihandles.RideCardHandle;

import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.ui.exceptions.AccessibilityException;

public class RideCardTest extends GuiUnitTest {

    @Test
    public void display() throws AccessibilityException {
        // no tags
        Ride rideWithNoTags = new RideBuilder().withTags(new String[0]).build();
        RideCard rideCard = new RideCard(rideWithNoTags, 1);
        uiPartRule.setUiPart(rideCard);
        assertCardDisplay(rideCard, rideWithNoTags, 1);

        // with tags
        Ride rideWithTags = new RideBuilder().build();
        rideCard = new RideCard(rideWithTags, 2);
        uiPartRule.setUiPart(rideCard);
        assertCardDisplay(rideCard, rideWithTags, 2);
    }

    @Test
    public void equals() throws AccessibilityException {
        Ride ride = new RideBuilder().build();
        RideCard rideCard = new RideCard(ride, 0);

        // same ride, same index -> returns true
        RideCard copy = new RideCard(ride, 0);
        assertTrue(rideCard.equals(copy));

        // same object -> returns true
        assertTrue(rideCard.equals(rideCard));

        // null -> returns false
        assertFalse(rideCard.equals(null));

        // different types -> returns false
        assertFalse(rideCard.equals(0));

        // different ride, same index -> returns false
        Ride differentRide = new RideBuilder().withName("differentName").build();
        assertFalse(rideCard.equals(new RideCard(differentRide, 0)));

        // same ride, different index -> returns false
        assertFalse(rideCard.equals(new RideCard(ride, 1)));
    }

    /**
     * Asserts that {@code rideCard} displays the details of {@code expectedRide} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RideCard rideCard, Ride expectedRide, int expectedId) {
        guiRobot.pauseForHuman();

        RideCardHandle rideCardHandle = new RideCardHandle(rideCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", rideCardHandle.getId());

        // verify ride details are displayed correctly
        assertCardDisplaysRide(expectedRide, rideCardHandle);
    }
}
