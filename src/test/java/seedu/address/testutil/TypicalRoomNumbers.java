package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;

import seedu.address.model.room.RoomNumber;

/**
 * A utility class containing a list of {@code RoomNumber} objects to be used in tests.
 */
public class TypicalRoomNumbers {
    // Reserved for testing with preset bookings
    public static final RoomNumber ROOM_NUMBER_001 = new RoomNumber("001");
    public static final RoomNumber ROOM_NUMBER_002 = new RoomNumber("002");
    public static final RoomNumber ROOM_NUMBER_010 = new RoomNumber("010");
    public static final RoomNumber ROOM_NUMBER_011 = new RoomNumber("011");
    public static final RoomNumber ROOM_NUMBER_012 = new RoomNumber("012");
    public static final RoomNumber ROOM_NUMBER_020 = new RoomNumber("020");
    public static final RoomNumber ROOM_NUMBER_021 = new RoomNumber("021");
    public static final RoomNumber ROOM_NUMBER_022 = new RoomNumber("022");
    public static final RoomNumber ROOM_NUMBER_030 = new RoomNumber("030");
    public static final RoomNumber ROOM_NUMBER_031 = new RoomNumber("031");
    public static final RoomNumber ROOM_NUMBER_099 = new RoomNumber("099");

    // Reserved for testing with clean rooms
    public static final RoomNumber ROOM_NUMBER_050 = new RoomNumber("050");
    public static final RoomNumber ROOM_NUMBER_051 = new RoomNumber("051");
    public static final RoomNumber ROOM_NUMBER_052 = new RoomNumber("052");
    public static final RoomNumber ROOM_NUMBER_053 = new RoomNumber("053");
    public static final RoomNumber ROOM_NUMBER_054 = new RoomNumber("054");
    public static final RoomNumber ROOM_NUMBER_055 = new RoomNumber("055");
    public static final RoomNumber ROOM_NUMBER_056 = new RoomNumber("056");
    public static final RoomNumber ROOM_NUMBER_057 = new RoomNumber("057");
    public static final RoomNumber ROOM_NUMBER_058 = new RoomNumber("058");
    public static final RoomNumber ROOM_NUMBER_059 = new RoomNumber("059");
    public static final RoomNumber ROOM_NUMBER_060 = new RoomNumber("060");

    // Manually added - RoomNumber found in {@code CommandTestUtil}
    public static final RoomNumber ROOM_NUMBER_AMY = new RoomNumber(VALID_ROOM_NUMBER_AMY);
    public static final RoomNumber ROOM_NUMBER_BOB = new RoomNumber(VALID_ROOM_NUMBER_BOB);
}
