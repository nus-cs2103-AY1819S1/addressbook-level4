package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.person.Person;
import seedu.address.testutil.EntryBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddEntryCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEntryCommand(null);
    }

    @Test
    public void execute_entryAcceptedByModel_addSuccessful() throws Exception {
        AddEntryCommandTest.ModelStubAcceptingEntryAdded modelStub =
                new AddEntryCommandTest.ModelStubAcceptingEntryAdded();
        ResumeEntry entry = new EntryBuilder().build();

        CommandResult commandResult = new AddEntryCommand(entry).execute(modelStub, commandHistory);

        assertEquals(String.format(AddEntryCommand.MESSAGE_SUCCESS, entry), commandResult.feedbackToUser);
        //to be implemented later
        // assertEquals(Arrays.asList(entry), modelStub.entriesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    /*@Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        AddCommandTest.ModelStub modelStub = new AddCommandTest.ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }*/

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        ResumeEntry sourceAcademy = new EntryBuilder().withTitle("SOURCE ACADEMY").build();
        ResumeEntry facebook = new EntryBuilder().withTitle("Facebook").build();

        // same object -> returns true
        assertTrue(sourceAcademy.equals(sourceAcademy));

        // same values -> returns true
        AddEntryCommand copied = new AddEntryCommand(sourceAcademy);
        assertTrue(new AddEntryCommand(sourceAcademy).equals(copied));

        // different types -> returns false
        assertFalse(sourceAcademy.equals(1));

        // null -> returns false
        assertFalse(sourceAcademy.equals(null));

        // different person -> returns false
        assertFalse(sourceAcademy.equals(facebook));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEntry(ResumeEntry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEntry(ResumeEntry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void loadTemplate(String filepath) {
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
     * A Model stub that contains a single person.
     */ /*
    private class ModelStubWithPerson extends AddCommandTest.ModelStub {
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

    }*/

    /**
     * A Model stub that always accept the person being added.
     */

    private class ModelStubAcceptingEntryAdded extends AddEntryCommandTest.ModelStub {
        final ArrayList<ResumeEntry> entriesAdded = new ArrayList<>();

        @Override
        public boolean hasEntry(ResumeEntry entry) {
            requireNonNull(entry);
            return entriesAdded.stream().anyMatch(entry::isSameEntry);
        }

        @Override
        public void addEntry(ResumeEntry entry) {
            requireNonNull(entry);
            entriesAdded.add(entry);
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
