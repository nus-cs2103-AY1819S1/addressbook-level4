package seedu.modsuni.model;

import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.crypto.NoSuchPaddingException;

import javafx.collections.ObservableList;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.semester.SemesterList;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.User;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /** Returns the ModuleList */
    ReadOnlyModuleList getModuleList();

    /** Returns a ObservableList of modules */
    ObservableList<Module> getObservableModuleList();

    /**
     * Check if the user is a student.
     */
    boolean isStudent();

    /**
     * Adds the given admin.
     * {@code admin} must not already exist.
     */
    void addAdmin(Admin admin, Path savePath);

    /**
     * Adds the given module to the database.
     * @param module
     */
    void addModuleToDatabase(Module module);

    /**
     * Removes a existing module from the database.
     * @param module
     */
    void removeModuleFromDatabase(Module module);

    /**
     * Returns true if the given {@code module} already exists in the database.
     */
    boolean hasModuleInDatabase(Module module);

    /**
     * Replace the (@code target} module with {@code editModule}.
     */
    void updateModule(Module target, Module editedModule);

    /**
     * Check if the user is a admin.
     */
    boolean isAdmin();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the taken module list.
     */
    boolean hasModuleTaken(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the taken module list.
     */
    void removeModuleTaken(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the taken module list.
     */
    void addModuleTaken(Module module);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the staged module list.
     */
    boolean hasModuleStaged(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the staged module list.
     */
    void removeModuleStaged(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the staged module list.
     */
    void addModuleStaged(Module module);

    /**
     * Returns an unmodifiable view of the filtered database  module list
     */
    ObservableList<Module> getFilteredDatabaseModuleList();

    /**
     * Returns an unmodifiable view of the filtered staged module list
     */
    ObservableList<Module> getFilteredStagedModuleList();

    /**
     * Returns an unmodifiable view of the filtered taken module list
     */
    ObservableList<Module> getFilteredTakenModuleList();

    /**
     * Updates the filter of the filtered database module list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDatabaseModuleList(Predicate<Module> predicate);

    /**
     * Adds the given credential.
     * {@code credential} must not already exist in the credential store.
     */
    void addCredential(Credential credential);

    /**
     * Removes the crdential with given username.
     * Credential with username {@code username} should already exist in the credential store.
     */
    void removeCredential(Credential credential);
    /**
     * Returns true if credential with the same username already exists in
     * the credential store.
     */
    boolean hasCredential(Credential credential);

    /**
     * Gets the credential with given username.
     */
    Credential getCredential(Username username);

    /**
     * Returns the CredentialStore
     */
    ReadOnlyCredentialStore getCredentialStore();

    /**
     * Returns true if credential is verified within the CredentialStore
     */
    boolean isVerifiedCredential(Credential credential);

    /**
     * Returns the Password of the given user
     */
    Password getCredentialPassword(User user);

    /**
     * Returns a list of usernames.
     */
    ObservableList<Username> getUsernames();

    /**
     * Sets the given user as the currentUser.
     */
    void setCurrentUser(User user);

    /**
     * Returns the currentUser.
     */
    User getCurrentUser();

    /**
     * Reset user details
     */
    void resetCurrentUser();

    /**
     * Saves the current user.
     */
    void saveUserFile(User user, Path savePath);

    /**
     * Read a user with the given file path.
     */
    Optional<User> readUserFile(Path filePath, String password) throws IOException, DataConversionException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidPasswordException,
            CorruptedFileException, NoSuchPaddingException;

    /**
     * Returns the optional of the module in the database.
     */
    Optional<Module> searchCodeInDatabase(Code code);

    /**
     * Returns the Index of the module in the database.
     */
    Index searchForIndexInDatabase(Module module);

    /**
     * Returns the optional of a list of codes if unable to generate.
     */
    Optional<List<Code>> canGenerate();

    /**
     * Returns a semester list from the generated schedule.
     * @return
     */
    SemesterList generateSchedule();

}
