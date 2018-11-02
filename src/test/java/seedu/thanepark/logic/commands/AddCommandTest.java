package seedu.thanepark.logic.commands;

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

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.testutil.RideBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRideAdded modelStub = new ModelStubAcceptingRideAdded();
        Ride validRide = new RideBuilder().build();

        CommandResult commandResult = new AddCommand(validRide).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRide), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRide), modelStub.ridesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Ride validRide = new RideBuilder().build();
        AddCommand addCommand = new AddCommand(validRide);
        ModelStub modelStub = new ModelStubWithRide(validRide);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_RIDE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Ride alice = new RideBuilder().withName("Alice").build();
        Ride bob = new RideBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different ride -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addRide(Ride ride) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyThanePark newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyThanePark getThanePark() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRide(Ride ride) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRide(Ride target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRide(Ride target, Ride editedRide) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Ride> getFilteredRideList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRideList(Predicate<Ride> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoThanePark() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoThanePark() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoThanePark() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoThanePark() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitThanePark() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single ride.
     */
    private class ModelStubWithRide extends ModelStub {
        private final Ride ride;

        ModelStubWithRide(Ride ride) {
            requireNonNull(ride);
            this.ride = ride;
        }

        @Override
        public boolean hasRide(Ride ride) {
            requireNonNull(ride);
            return this.ride.isSameRide(ride);
        }
    }

    /**
     * A Model stub that always accept the ride being added.
     */
    private class ModelStubAcceptingRideAdded extends ModelStub {
        final ArrayList<Ride> ridesAdded = new ArrayList<>();

        @Override
        public boolean hasRide(Ride ride) {
            requireNonNull(ride);
            return ridesAdded.stream().anyMatch(ride::isSameRide);
        }

        @Override
        public void addRide(Ride ride) {
            requireNonNull(ride);
            ridesAdded.add(ride);
        }

        @Override
        public void commitThanePark() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyThanePark getThanePark() {
            return new ThanePark();
        }
    }

}
