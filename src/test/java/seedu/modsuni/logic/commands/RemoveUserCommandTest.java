package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.CredentialStore;
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
import seedu.modsuni.testutil.CredentialBuilder;

public class RemoveUserCommandTest {

    private static final String ABSENT_USERNAME = "absent";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveUserCommand(null);
    }

    @Test
    public void notLoggedIn_throwsCommandException() throws Exception {
        RemoveUserCommand removeUserCommand =
            new RemoveUserCommand(new Username("dummy"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_NOT_LOGGED_IN);
        Model model = new ModelManager();

        removeUserCommand.execute(model, commandHistory);
    }

    @Test
    public void notAdmin_throwsCommandException() throws Exception {
        RemoveUserCommand removeUserCommand =
            new RemoveUserCommand(new Username("dummy"));

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_NOT_ADMIN);
        Model model = new ModelManager();
        User fakeAdmin = new AdminBuilder().withRole(Role.STUDENT).build();
        model.setCurrentUser(fakeAdmin);

        removeUserCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_credentialExist_removeSuccessful() throws Exception {
        Credential validCredential = new CredentialBuilder().build();
        ModelStub modelStub = new ModelStubWithCredential(validCredential);

        CommandResult commandResult =
            new RemoveUserCommand(validCredential.getUsername()).execute(modelStub,
                commandHistory);

        assertEquals(String.format(RemoveUserCommand.MESSAGE_DELETE_USER_SUCCESS,
            validCredential.getUsername()), commandResult.feedbackToUser);
        assertFalse(modelStub.hasCredential(validCredential));
    }

    @Test
    public void execute_credentialDoesNotExist_throwsCommandException() throws Exception {
        Credential validCredential = new CredentialBuilder().build();
        ModelStub modelStub = new ModelStubWithCredential(validCredential);

        RemoveUserCommand removeUserCommand =
            new RemoveUserCommand(new CredentialBuilder().withUsername(ABSENT_USERNAME).build().getUsername());

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(RemoveUserCommand.MESSAGE_USER_NOT_FOUND, ABSENT_USERNAME));

        removeUserCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Username username1 = new Username("user1");
        Username username2 = new Username("user2");
        RemoveUserCommand removeUser1Command =
            new RemoveUserCommand(username1);
        RemoveUserCommand removeUser2Command =
            new RemoveUserCommand(username2);


        // same object -> returns true
        assertTrue(removeUser1Command.equals(removeUser1Command));

        // same values -> returns true
        RemoveUserCommand removeUser1CommandCopy =
            new RemoveUserCommand(username1);
        assertTrue(removeUser1Command.equals(removeUser1CommandCopy));

        // different types -> returns false
        assertFalse(removeUser1Command.equals(1));

        // null -> returns false
        assertFalse(removeUser1Command.equals(null));

        // different module -> returns false
        assertFalse(removeUser1Command.equals(removeUser2Command));
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
        public ObservableList<Module> getFilteredDatabaseModuleList() {
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
            throw new AssertionError("This method should not be called.");
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
            return false;
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
        public Optional<List<Code>> canGenerate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SemesterList generateSchedule() {
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
    }

    private class ModelStubWithCredential extends ModelStub {

        private CredentialStore credentialStore = new CredentialStore();
        private User currentUser = new AdminBuilder().build();

        ModelStubWithCredential(Credential credential) {
            requireNonNull(credential);
            credentialStore.addCredential(credential);
        }

        @Override
        public void removeCredential(Credential credential) {
            requireNonNull(credential);
            credentialStore.removeCredential(credential);
        }

        @Override
        public boolean hasCredential(Credential credential) {
            requireNonNull(credential);
            return credentialStore.hasCredential(credential);
        }

        @Override
        public Credential getCredential(Username username) {
            return credentialStore.getCredential(username);
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        @Override
        public void setCurrentUser(User user) {
            currentUser = user;
        }

        @Override
        public ObservableList<Username> getUsernames() {
            return FXCollections.observableArrayList();
        }

        @Override
        public boolean isAdmin() {
            return true;
        }
    }

}
