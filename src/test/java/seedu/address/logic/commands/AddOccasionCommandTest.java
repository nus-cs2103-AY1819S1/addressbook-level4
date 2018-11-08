package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Person;
import seedu.address.testutil.OccasionBuilder;

public class AddOccasionCommandTest {


    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddOccasionCommand(null);
    }

    @Test
    public void execute_occasionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOccasionAdded modelStub = new ModelStubAcceptingOccasionAdded();
        Occasion validOccasion = new OccasionBuilder().build();

        CommandResult commandResult = new AddOccasionCommand(validOccasion).execute(modelStub, commandHistory);

        assertEquals(String.format(AddOccasionCommand.MESSAGE_SUCCESS, validOccasion), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validOccasion), modelStub.occasionssAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateOccasion_throwsCommandException() throws Exception {
        Occasion validOccasion = new OccasionBuilder().build();
        AddOccasionCommand addOccasionCommand = new AddOccasionCommand(validOccasion);
        ModelStub modelStub = new ModelStubWithOccasion(validOccasion);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddOccasionCommand.MESSAGE_DUPLICATE_OCCASION);
        addOccasionCommand.execute(modelStub, commandHistory);
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
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOccasion(Occasion occasion) {
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
        public TypeUtil getActiveType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActiveType(TypeUtil newActiveType) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOccasion(Occasion occasion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOccasion(Occasion target, Occasion editedOccasion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person personToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module moduleToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOccasion(Occasion occasionToDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Occasion> getFilteredOccasionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOccasionList(Predicate<Occasion> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void insertPerson(Person person, Module module, Person personToInsert,
                                 Module moduleToInsert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void insertPerson(Person person, Occasion occasion, Person personToInsert,
                                 Occasion moduleToInsert) {
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
     */
    private class ModelStubWithOccasion extends ModelStub {
        private final Occasion occasion;

        ModelStubWithOccasion(Occasion occasion) {
            requireNonNull(occasion);
            this.occasion = occasion;
        }

        @Override
        public boolean hasOccasion(Occasion occasionToAdd) {
            requireNonNull(occasionToAdd);
            return this.occasion.equals(occasionToAdd);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingOccasionAdded extends ModelStub {
        final ArrayList<Occasion> occasionssAdded = new ArrayList<>();

        @Override
        public boolean hasOccasion(Occasion occasionToAdd) {
            requireNonNull(occasionToAdd);
            return occasionssAdded.stream().anyMatch(occasionToAdd::equals);
        }


        @Override
        public void addOccasion(Occasion occasionToAdd) {
            requireNonNull(occasionToAdd);
            occasionssAdded.add(occasionToAdd);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddOccasionCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
