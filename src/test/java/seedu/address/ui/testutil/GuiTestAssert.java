package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.event.Event;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EventCardHandle expectedCard, EventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEventName(), actualCard.getEventName());
        assertEquals(expectedCard.getStartDateTime(), actualCard.getStartDateTime());
        assertEquals(expectedCard.getEndDateTime(), actualCard.getEndDateTime());
        assertEquals(expectedCard.getVenue(), actualCard.getVenue());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(Event expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getEventName().value, actualCard.getEventName());
        assertEquals(expectedEvent.getStartDateTime().getPrettyString(), actualCard.getStartDateTime());
        assertEquals(expectedEvent.getEndDateTime().getPrettyString(), actualCard.getEndDateTime());
        assertEquals(expectedEvent.getVenue().value, actualCard.getVenue());
        assertEquals(expectedEvent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code events} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, Event... events) {
        for (int i = 0; i < events.length; i++) {
            eventListPanelHandle.navigateToCard(i);
            assertCardDisplaysEvent(events[i], eventListPanelHandle.getEventCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code events} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, List<Event> events) {
        assertListMatching(eventListPanelHandle, events.toArray(new Event[0]));
    }

    /**
     * Asserts the size of the list in {@code eventListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(EventListPanelHandle eventListPanelHandle, int size) {
        int numberOfEvent = eventListPanelHandle.getListSize();
        assertEquals(size, numberOfEvent);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
