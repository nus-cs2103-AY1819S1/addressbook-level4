package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
//import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.carpark.Carpark;
//import seedu.address.testutil.PersonBuilder;

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
        //ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        //Person validPerson = new PersonBuilder().build();
        //
        //CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);
        //
        //assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        //assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        //assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        //Person validPerson = new PersonBuilder().build();
        //AddCommand addCommand = new AddCommand(validPerson);
        //ModelStub modelStub = new ModelStubWithPerson(validPerson);
        //
        //thrown.expect(CommandException.class);
        //thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        //addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        //Person alice = new PersonBuilder().withName("Alice").build();
        //Person bob = new PersonBuilder().withName("Bob").build();
        //AddCommand addAliceCommand = new AddCommand(alice);
        //AddCommand addBobCommand = new AddCommand(bob);
        //
        //// same object -> returns true
        //assertTrue(addAliceCommand.equals(addAliceCommand));
        //
        //// same values -> returns true
        //AddCommand addAliceCommandCopy = new AddCommand(alice);
        //assertTrue(addAliceCommand.equals(addAliceCommandCopy));
        //
        //// different types -> returns false
        //assertFalse(addAliceCommand.equals(1));
        //
        //// null -> returns false
        //assertFalse(addAliceCommand.equals(null));
        //
        //// different carpark -> returns false
        //assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addCarpark(Carpark carpark) {
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
        public boolean hasCarpark(Carpark carpark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCarpark(Carpark carpark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCarpark(Carpark target, Carpark editedCarpark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void loadCarpark(List<Carpark> listCarpark) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Carpark> getFilteredCarparkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCarparkList(Predicate<Carpark> predicate) {
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
    }

    /**
     * A Model stub that contains a single carpark.
     */
    private class ModelStubWithCarpark extends ModelStub {
        private final Carpark carpark;

        ModelStubWithCarpark(Carpark carpark) {
            requireNonNull(carpark);
            this.carpark = carpark;
        }

        @Override
        public boolean hasCarpark(Carpark carpark) {
            requireNonNull(carpark);
            return this.carpark.isSameCarpark(carpark);
        }
    }

    /**
     * A Model stub that always accept the carpark being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Carpark> carparksAdded = new ArrayList<>();

        @Override
        public boolean hasCarpark(Carpark carpark) {
            requireNonNull(carpark);
            return carparksAdded.stream().anyMatch(carpark::isSameCarpark);
        }

        @Override
        public void addCarpark(Carpark carpark) {
            requireNonNull(carpark);
            carparksAdded.add(carpark);
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
