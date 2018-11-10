package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.OccasionBuilder;
import seedu.address.testutil.PersonBuilder;

public class RemovePersonCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPersonNullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemovePersonCommand(null, null, TypeUtil.OCCASION);
    }

    @Test
    public void constructor_nullPersonNullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemovePersonCommand(null, null, TypeUtil.MODULE);
    }

    @Test
    public void constructor_nullPersonNonNullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemovePersonCommand(null, Index.fromZeroBased(1), TypeUtil.OCCASION);
    }

    @Test
    public void constructor_nullPersonNonNullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemovePersonCommand(null, Index.fromZeroBased(1), TypeUtil.MODULE);
    }

    @Test
    public void constructor_nonNullPersonNullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemovePersonCommand(null, Index.fromZeroBased(1), TypeUtil.OCCASION);
    }

    @Test
    public void constructor_nonNullPersonNullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemovePersonCommand(Index.fromZeroBased(1), null, TypeUtil.MODULE);
    }

    @Test
    public void execute_personRemovedFromModule_success() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Module validModule = new ModuleBuilder().build();
        Occasion validOccasion = new OccasionBuilder().build();

        ModelStubInsertingPersons stub = new ModelStubInsertingPersons(validPerson, validOccasion, validModule);

        new InsertPersonCommand(Index.fromZeroBased(0), Index.fromZeroBased(0),
                new Module(new ModuleDescriptor())).execute(stub, commandHistory);

        CommandResult commandResult = new RemovePersonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(0),
                TypeUtil.MODULE).execute(stub, commandHistory);

        assertEquals(RemovePersonCommand.MESSAGE_SUCCESS_REMOVE_FROM_MODULE, commandResult.feedbackToUser);
        List<Module> moduleList = stub.getFilteredPersonList()
                                      .get(0).getModuleList().asNormalList();
        // A shallow equals to check if the names of the occasions match.
        assertTrue(moduleList.isEmpty());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_personRemovedFromOccasion_success() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Module validModule = new ModuleBuilder().build();
        Occasion validOccasion = new OccasionBuilder().build();

        ModelStubInsertingPersons stub = new ModelStubInsertingPersons(validPerson, validOccasion, validModule);

        new InsertPersonCommand(Index.fromZeroBased(0), Index.fromZeroBased(0),
                new Occasion(new OccasionDescriptor())).execute(stub, commandHistory);

        CommandResult commandResult = new RemovePersonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(0),
                TypeUtil.OCCASION).execute(stub, commandHistory);

        assertEquals(RemovePersonCommand.MESSAGE_SUCCESS_REMOVE_FROM_OCCASION, commandResult.feedbackToUser);
        List<Occasion> occasionList = stub.getFilteredPersonList()
                                          .get(0).getOccasionList().asNormalList();
        // A shallow equals to check if the names of the occasions match.
        assertTrue(occasionList.isEmpty());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_removingPersonNotInModule() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Module validModule = new ModuleBuilder().build();
        Occasion validOccasion = new OccasionBuilder().build();
        RemovePersonCommand removePerson = new RemovePersonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(0),
                TypeUtil.MODULE);

        ModelStubInsertingPersons stub = new ModelStubInsertingPersons(validPerson, validOccasion, validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePersonCommand.MESSAGE_FAILURE);
        removePerson.execute(stub, commandHistory);
    }

    @Test
    public void execute_removingPersonNotInOccasion() throws Exception {
        Person validPerson = new PersonBuilder().build();
        Module validModule = new ModuleBuilder().build();
        Occasion validOccasion = new OccasionBuilder().build();
        RemovePersonCommand removePerson = new RemovePersonCommand(Index.fromZeroBased(0),
                Index.fromZeroBased(0),
                TypeUtil.OCCASION);

        ModelStubInsertingPersons stub = new ModelStubInsertingPersons(validPerson, validOccasion, validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemovePersonCommand.MESSAGE_FAILURE);
        removePerson.execute(stub, commandHistory);
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
        public void deletePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOccasion(Occasion occasion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person person, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateModule(Module module, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOccasion(Occasion occasion, Occasion editedOccasion) {
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
        public void insertPerson(Person personDeep, Module moduleDeep,
                                 Person personShallow, Module moduleShallow,
                                 Person personToInsert, Module moduleToInsert) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void insertPerson(Person personDeep, Occasion occasionDeep,
                                 Person personShallow, Occasion occasionShallow,
                                 Person personToInsert, Occasion moduleToInsert) {
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
        public TypeUtil getActiveType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setActiveType(TypeUtil typeToSet) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A stub that always inserts persons into one of either
     * module or occasion.
     */
    private class ModelStubInsertingPersons extends ModelStub {
        private UniquePersonList personList = new UniquePersonList();
        private UniqueOccasionList occasionList = new UniqueOccasionList();
        private UniqueModuleList moduleList = new UniqueModuleList();

        ModelStubInsertingPersons(Person person, Occasion occasion, Module module) {
            requireAllNonNull(person, occasion, module);
            this.personList.add(person);
            this.occasionList.add(occasion);
            this.moduleList.add(module);
        }

        @Override
        public void insertPerson(Person personToInsertDeep, Occasion occasionToInsertDeep,
                                        Person personToInsertShallow, Occasion occasionToInsertShallow,
                                        Person personToReplace, Occasion occasionToReplace) {
            personToInsertDeep.getOccasionList().add(occasionToInsertShallow);
            occasionToInsertDeep.getAttendanceList().add(personToInsertShallow);
            updatePerson(personToReplace, personToInsertDeep);
            updateOccasion(occasionToReplace, occasionToInsertDeep);
        }

        @Override
        public void insertPerson(Person personToInsertDeep, Module moduleToInsertDeep,
                                        Person personToInsertShallow, Module moduleToInsertShallow,
                                        Person personToReplace, Module moduleToReplace) {
            personToInsertDeep.getModuleList().add(moduleToInsertShallow);
            moduleToInsertDeep.getStudents().add(personToInsertShallow);
            updatePerson(personToReplace, personToInsertDeep);
            updateModule(moduleToReplace, moduleToInsertDeep);
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            requireNonNull(editedPerson);

            personList.setPerson(target, editedPerson);
        }

        @Override
        public void updateModule(Module target, Module editedModule) {
            requireNonNull(editedModule);

            moduleList.setModule(target, editedModule);
        }

        @Override
        public void updateOccasion(Occasion target, Occasion editedOccasion) {
            requireNonNull(editedOccasion);

            occasionList.setOccasion(target, editedOccasion);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return this.personList.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            return this.moduleList.asUnmodifiableObservableList();
        }

        @Override
        public ObservableList<Occasion> getFilteredOccasionList() {
            return this.occasionList.asUnmodifiableObservableList();
        }

        /**
         * An empty implementation as it need not be tested for unit testing.
         */
        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {

        }

        /**
         * An empty implementation as it need not be tested for unit testing.
         */
        @Override
        public void updateFilteredOccasionList(Predicate<Occasion> predicate) {

        }

        /**
         * An empty implementation as it need not be tested for unit testing.
         */
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddPersonCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
