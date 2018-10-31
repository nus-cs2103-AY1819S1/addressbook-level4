package seedu.thanepark.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.RideCardHandle;
import guitests.guihandles.RideListPanelHandle;
import seedu.thanepark.model.ride.Ride;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RideCardHandle expectedCard, RideCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getStatusString(), actualCard.getStatusString());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        assertEquals(expectedCard.getInfo(), actualCard.getInfo());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRide}.
     */
    public static void assertCardDisplaysRide(Ride expectedRide, RideCardHandle actualCard) {
        assertEquals(expectedRide.getName().fullName, actualCard.getName());
        assertEquals(expectedRide.getStatus().name(), actualCard.getStatusString());
        assertEquals(expectedRide.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(expectedRide.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code rideListPanelHandle} displays the details of {@code rides} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RideListPanelHandle rideListPanelHandle, Ride... rides) {
        for (int i = 0; i < rides.length; i++) {
            rideListPanelHandle.navigateToCard(i);
            assertCardDisplaysRide(rides[i], rideListPanelHandle.getRideCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code rideListPanelHandle} displays the details of {@code rides} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RideListPanelHandle rideListPanelHandle, List<Ride> rides) {
        assertListMatching(rideListPanelHandle, rides.toArray(new Ride[0]));
    }

    /**
     * Asserts the size of the list in {@code rideListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RideListPanelHandle rideListPanelHandle, int size) {
        int numberOfPeople = rideListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
