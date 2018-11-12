package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.semester.SemesterList;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.AdminBuilder;
import seedu.modsuni.testutil.ModuleBuilder;

public class AddModuleToDatabaseCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddModuleToDatabaseCommand(null);
    }

    @Test
    public void notLoggedIn_throwsCommandException() throws Exception {
        AddModuleToDatabaseCommand addModuleToDatabaseCommand =
                new AddModuleToDatabaseCommand(new ModuleBuilder().build());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToDatabaseCommand.MESSAGE_NOT_LOGGED_IN);
        Model model = new ModelManager();

        addModuleToDatabaseCommand.execute(model, commandHistory);
    }

    @Test
    public void notAdmin_throwsCommandException() throws Exception {
        AddModuleToDatabaseCommand addModuleToDatabaseCommand =
                new AddModuleToDatabaseCommand(new ModuleBuilder().build());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToDatabaseCommand.MESSAGE_NOT_ADMIN);
        Model model = new ModelManager();
        User fakeAdmin = new AdminBuilder().withRole(Role.STUDENT).build();
        model.setCurrentUser(fakeAdmin);

        addModuleToDatabaseCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleToDatabaseCommand(validModule).execute(modelStub, commandHistory);

        assertEquals(String.format(AddModuleToDatabaseCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() throws Exception {
        Module validModule = new ModuleBuilder().build();
        AddModuleToDatabaseCommand addModuleToDatabaseCommand = new AddModuleToDatabaseCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToDatabaseCommand.MESSAGE_DUPLICATE_MODULE);
        addModuleToDatabaseCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Module module1 = new ModuleBuilder().withCode(new Code("CS1000")).build();
        Module module2 = new ModuleBuilder().withCode(new Code("CS2000")).build();
        AddModuleToDatabaseCommand addModule1Command = new AddModuleToDatabaseCommand(module1);
        AddModuleToDatabaseCommand addModule2Command = new AddModuleToDatabaseCommand(module2);

        // same object -> returns true
        assertTrue(addModule1Command.equals(addModule1Command));

        // same values -> returns true
        AddModuleToDatabaseCommand addModule1CommandCopy = new AddModuleToDatabaseCommand(module1);
        assertTrue(addModule1Command.equals(addModule1CommandCopy));

        // different types -> returns false
        assertFalse(addModule1Command.equals(1));

        // null -> returns false
        assertFalse(addModule1Command.equals(null));

        // different module -> returns false
        assertFalse(addModule1Command.equals(addModule2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public boolean hasModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModuleList getModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getObservableModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredStagedModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredTakenModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredDatabaseModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDatabaseModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Credential getCredential(Username username) {
            throw new AssertionError("THis method should not be called.");
        }

        @Override
        public ObservableList<Username> getUsernames() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAdmin(Admin admin, Path savePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleToDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleFromDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleInDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isVerifiedCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Password getCredentialPassword(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAdmin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isStudent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCredentialStore getCredentialStore() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public User getCurrentUser() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void resetCurrentUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentUser(User user) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Optional<Module> searchCodeInDatabase(Code code) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Index searchForIndexInDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserFile(User user, Path savePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<User> readUserFile(Path filePath, String password) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<List<Code>> canGenerate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SemesterList generateSchedule() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;
        private User currentUser = new AdminBuilder().build();

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModuleInDatabase(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        @Override
        public boolean isAdmin() {
            return true;
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();
        private User currentUser = new AdminBuilder().build();

        @Override
        public boolean hasModuleInDatabase(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModuleToDatabase(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyModuleList getModuleList() {
            return new ModuleList();
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        @Override
        public boolean isAdmin() {
            return true;
        }
    }
}
