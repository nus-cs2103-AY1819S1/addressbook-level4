package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.TypicalBookingPeriods;
import seedu.address.testutil.TypicalRoomNumbers;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        new AddCommand(null, validRoomNumber, validBookingPeriod);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGuestAdded modelStub = new ModelStubAcceptingGuestAdded();
        Guest validGuest = new GuestBuilder().build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        CommandResult commandResult =
                new AddCommand(validGuest, validRoomNumber, validBookingPeriod)
                        .execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validGuest,
                validRoomNumber, validBookingPeriod), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGuest), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Guest validGuest = new GuestBuilder().build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        AddCommand addCommand = new AddCommand(validGuest, validRoomNumber, validBookingPeriod);
        ModelStub modelStub = new ModelStubWithPerson(validGuest);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Guest alice = new GuestBuilder().withName("Alice").build();
        Guest bob = new GuestBuilder().withName("Bob").build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        AddCommand addAliceCommand = new AddCommand(alice, validRoomNumber, validBookingPeriod);
        AddCommand addBobCommand = new AddCommand(bob, validRoomNumber, validBookingPeriod);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice, validRoomNumber, validBookingPeriod);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different guest -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Guest target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Guest target, Guest editedGuest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Guest> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Guest> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Room> getFilteredRoomList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkinRoom(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkoutRoom(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isRoomCheckedIn(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean roomHasBooking(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean roomHasActiveBooking(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean roomHasActiveOrExpiredBooking(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBooking(RoomNumber roomNumber, Booking booking) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single guest.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Guest guest;

        ModelStubWithPerson(Guest guest) {
            requireNonNull(guest);
            this.guest = guest;
        }

        @Override
        public boolean hasPerson(Guest guest) {
            requireNonNull(guest);
            return this.guest.isSameGuest(guest);
        }
    }

    /**
     * A Model stub that always accept the guest and assigns a room to him.
     */
    private class ModelStubAcceptingGuestAdded extends ModelStub {
        final ArrayList<Guest> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Guest guest) {
            requireNonNull(guest);
            return personsAdded.stream().anyMatch(guest::isSameGuest);
        }

        @Override
        public void addPerson(Guest guest) {
            requireNonNull(guest);
            personsAdded.add(guest);
        }

        @Override
        public void addBooking(RoomNumber roomNumber, Booking booking) {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
