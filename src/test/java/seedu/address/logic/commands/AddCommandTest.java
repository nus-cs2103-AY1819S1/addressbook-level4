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
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Person;
import seedu.address.model.user.Username;
import seedu.address.testutil.PersonBuilder;

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
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_personAcceptedByModel_budgetExceed() throws Exception {
        ModelStubBudget modelStub = new ModelStubBudget(false);
        Person validPerson = new PersonBuilder().build();
        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(AddCommand.MESSAGE_BUDGET_EXCEED_WARNING, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_personAcceptedByModel_withinBudget() throws Exception {
        ModelStubBudget modelStub = new ModelStubBudget(true);
        Person validPerson = new PersonBuilder().build();
        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
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

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public boolean addPerson(Person person) {
            throw new AssertionError("addPerson method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("resetData method should not be called.");
        }

        @Override
        public void modifyMaximumBudget(Budget budget) {
            throw new AssertionError("modifyMaximumBudget method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("getAddressBook should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("hasPerson method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("deletePerson method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("updatePerson method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("getFilteredPersonList method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("updateFilteredPersonList method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("canUndoAddressBook method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("canRedoAddressBook method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("undoAddressBook method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("redoAddressBook method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("commitAddressBook method should not be called.");
        }

        @Override
        public void loadUserData(Username username) {
            throw new AssertionError("loadUserData method should not be called.");
        }

        @Override
        public void unloadUserData() {
            throw new AssertionError("unloadUserData method should not be called.");
        }

        @Override
        public boolean isUserExists(Username username) {
            throw new AssertionError("isUserExists method should not be called.");
        }

        @Override
        public void addUser(Username username) {
            throw new AssertionError("addUser method should not be called.");
        }

        @Override
        public boolean hasSelectedUser() {
            throw new AssertionError("hasSelectedUser method should not be called.");
        }

        @Override
        public Model copy(UserPrefs userPrefs) {
            throw new AssertionError("copy method should not be called.");
        }

        @Override
        public Budget getMaximumBudget() {
            throw new AssertionError("getMaximumBudget method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();


        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public boolean addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
            return true;
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook(new Username("aa"));
        }
    }

    /**
     * A Model stub that will always result in a successful add, but can be within or above the budget
     */
    private class ModelStubBudget extends ModelStub {
        private final boolean withinBudget;

        public ModelStubBudget(boolean withinBudget) {
            this.withinBudget = withinBudget;
        }
        @Override
        public boolean hasPerson(Person person) {
            return false;
        }
        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }
        @Override
        public boolean addPerson(Person person) {
            return this.withinBudget;
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook(new Username("aa"));
        }
    }


}
