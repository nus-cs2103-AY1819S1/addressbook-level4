package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.GuestCardHandle;
import guitests.guihandles.GuestDetailedCardHandle;
import guitests.guihandles.GuestListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.RoomCardHandle;
import guitests.guihandles.RoomDetailedCardHandle;
import guitests.guihandles.RoomListPanelHandle;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;
import seedu.address.ui.MainWindow;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard} for guests.
     */
    public static void assertCardEquals(GuestCardHandle expectedCard, GuestCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard} for rooms.
     */
    public static void assertCardEquals(RoomCardHandle expectedCard, RoomCardHandle actualCard) {
        assertEquals(expectedCard.getRoomNumber(), actualCard.getRoomNumber());
        assertEquals(expectedCard.getCapacity(), actualCard.getCapacity());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedGuest}.
     */
    public static void assertCardDisplaysGuest(Guest expectedGuest, GuestCardHandle actualCard) {
        assertEquals(expectedGuest.getName().fullName, actualCard.getName());
        assertEquals(expectedGuest.getPhone().value, actualCard.getPhone());
        assertEquals(expectedGuest.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedGuest}.
     */
    public static void assertCardDisplaysDetailedGuest(Guest expectedGuest, GuestDetailedCardHandle actualCard) {
        assertEquals(expectedGuest.getName().fullName, actualCard.getName());
        assertEquals(expectedGuest.getPhone().value, actualCard.getPhone());
        assertEquals(expectedGuest.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRoom}.
     */
    public static void assertCardDisplaysRoom(Room expectedRoom, RoomCardHandle actualCard) {
        assertEquals("Room: " + expectedRoom.getRoomNumber(), actualCard.getRoomNumber());
        assertEquals("Capacity: " + expectedRoom.getCapacity(), actualCard.getCapacity());
        assertEquals(expectedRoom.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRoom}.
     */
    public static void assertCardDisplaysDetailedRoom(Room expectedRoom, RoomDetailedCardHandle actualCard) {
        assertEquals("Room: " + expectedRoom.getRoomNumber(), actualCard.getRoomNumber());
        assertEquals("Capacity: " + expectedRoom.getCapacity(), actualCard.getCapacity());
        assertEquals("Expenses:\n" + expectedRoom.getExpenses().toStringSummary(),
                actualCard.getExpenses());
        assertEquals("Active booking:\n" + expectedRoom.getBookings().toStringActiveBooking(),
                actualCard.getActiveBooking());
        assertEquals("All other bookings:\n" + expectedRoom.getBookings().toStringAllOtherBookings(),
                actualCard.getAllOtherBookings());
        assertEquals(expectedRoom.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code mainWindow} guestlist is visible.
     */
    public static void assertMainWindowDisplaysGuestList(MainWindow mainWindow) {
        assertEquals(mainWindow.isGuestListVisible(), true);
        assertEquals(mainWindow.isRoomListVisible(), false);
    }

    /**
     * Asserts that {@code mainWindow} roomlist is visible.
     */
    public static void assertMainWindowDisplaysRoomList(MainWindow mainWindow) {
        assertEquals(mainWindow.isGuestListVisible(), false);
        assertEquals(mainWindow.isRoomListVisible(), true);
    }

    /**
     * Asserts that the list in {@code guestListPanelHandle} displays the details of {@code guests} correctly and
     * in the correct order.
     */
    public static void assertGuestListMatching(GuestListPanelHandle guestListPanelHandle, Guest... guests) {
        for (int i = 0; i < guests.length; i++) {
            guestListPanelHandle.navigateToCard(i);
            assertCardDisplaysGuest(guests[i], guestListPanelHandle.getGuestCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code guestListPanelHandle} displays the details of {@code guests} correctly and
     * in the correct order.
     */
    public static void assertGuestListMatching(GuestListPanelHandle guestListPanelHandle, List<Guest> guests) {
        assertGuestListMatching(guestListPanelHandle, guests.toArray(new Guest[0]));
    }

    /**
     * Asserts that the list in {@code roomListPanelHandle} displays the details
     * of {@code rooms} correctly and in the correct order.
     */
    public static void assertRoomListMatching(RoomListPanelHandle roomListPanelHandle, Room... rooms) {
        for (int i = 0; i < rooms.length; i++) {
            roomListPanelHandle.navigateToCard(i);
            assertCardDisplaysRoom(rooms[i], roomListPanelHandle.getRoomCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code roomListPanelHandle} displays the details
     * of {@code roooms} correctly and in the correct order.
     */
    public static void assertRoomListMatching(RoomListPanelHandle roomListPanelHandle, List<Room> rooms) {
        assertRoomListMatching(roomListPanelHandle, rooms.toArray(new Room[0]));
    }

    /**
     * Asserts the size of the list in {@code guestListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(GuestListPanelHandle guestListPanelHandle, int size) {
        int numberOfPeople = guestListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
