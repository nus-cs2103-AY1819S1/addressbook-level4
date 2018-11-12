package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.account.Account;
import seedu.address.model.contact.Contact;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Contact> filteredContacts;
    private Account userAccount;
    private AutoMatchResult autoMatchResult = null;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with Heart²: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredContacts = new FilteredList<>(versionedAddressBook.getContactList());
        // initial: agreed to show client list
        updateFilteredContactList(ContactType.CLIENT.getFilter());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(Account userAccount) {
        this(new AddressBook(), new UserPrefs());
        this.userAccount = userAccount;
    }

    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, Account userAccount) {
        this(addressBook, userPrefs);
        this.userAccount = userAccount;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return versionedAddressBook.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        versionedAddressBook.removeContact(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addContact(Contact contact) {
        versionedAddressBook.addContact(contact);
        updateFilteredContactList(contact.getType().getFilter());
        indicateAddressBookChanged();
    }

    @Override
    public void updateContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        versionedAddressBook.updateContact(target, editedContact);
        indicateAddressBookChanged();
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return FXCollections.unmodifiableObservableList(filteredContacts);
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
    }

    //=========== Auto-matching Accessors ===================================================================

    @Override
    public void updateAutoMatchResult(AutoMatchResult newResults) {
        autoMatchResult = newResults;
    }

    public AutoMatchResult getAutoMatchResult() {
        return autoMatchResult;
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void commitUserLoggedInSuccessfully(Account userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public Account getUserAccount() {
        requireNonNull(userAccount);
        return userAccount;
    }

    @Override
    public boolean isUserLoggedIn() {
        return userAccount != null;
    }

    @Override
    public void commitUserLoggedOutSuccessfully() {
        userAccount = null;
        versionedAddressBook.clearState();
    }

    @Override
    public void commiteUserChangedPasswordSuccessfully(String newPassword) {
        userAccount = new Account(userAccount.getUserName(), newPassword, userAccount.getRole());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredContacts.equals(other.filteredContacts);
    }

}
