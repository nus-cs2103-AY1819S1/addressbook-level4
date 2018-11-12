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
import seedu.address.logic.commands.exceptions.LackOfPrivilegeException;
import seedu.address.model.AddressBook;
import seedu.address.model.AutoMatchResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.account.Account;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.TypicalAccount;
import seedu.address.testutil.VendorBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullContact_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Contact validContact = new ClientBuilder().build();

        CommandResult commandResult = new AddCommand(validContact).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validContact.getType(), validContact),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validContact), modelStub.contactsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_serviceProviderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Contact validContact = new VendorBuilder().build();

        CommandResult commandResult = new AddCommand(validContact).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validContact.getType(), validContact),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validContact), modelStub.contactsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() throws Exception {
        Contact validContact = new ClientBuilder().build();
        AddCommand addCommand = new AddCommand(validContact);
        ModelStub modelStub = new ModelStubWithContact(validContact);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddCommand.MESSAGE_DUPLICATE_CONTACT, validContact.getType()));
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_duplicateServiceProvider_throwsCommandException() throws Exception {
        Contact validContact = new VendorBuilder().build();
        AddCommand addCommand = new AddCommand(validContact);
        ModelStub modelStub = new ModelStubWithContact(validContact);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddCommand.MESSAGE_DUPLICATE_CONTACT, validContact.getType()));
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_noWritePrivilege_throwsLackOfPrivilegeException()
            throws CommandException, LackOfPrivilegeException {
        Contact validContact = new ClientBuilder().build();
        AddCommand addCommand = new AddCommand(validContact);
        ModelStub modelStub = new ModelStubWithoutWritePrivilege();

        thrown.expect(LackOfPrivilegeException.class);
        thrown.expectMessage(String.format(AddCommand.COMMAND_WORD_GENERAL, validContact.getType()));
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Contact alice = new ClientBuilder().withName("Alice").build();
        Contact bob = new ClientBuilder().withName("Bob").build();
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

        // different client -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addContact(Contact contact) {
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
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
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
        public void commitUserLoggedInSuccessfully(Account account) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Account getUserAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUserLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitUserLoggedOutSuccessfully() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commiteUserChangedPasswordSuccessfully(String newPassword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAutoMatchResult(AutoMatchResult newResults) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AutoMatchResult getAutoMatchResult() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single contact.
     */
    private class ModelStubWithContact extends ModelStub {
        private final Contact contact;

        ModelStubWithContact(Contact contact) {
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return this.contact.isSameContact(contact);
        }

        @Override
        public Account getUserAccount() {
            return TypicalAccount.ROOTACCOUNT;
        }
    }

    /**
     * A Model stub that does NOT have write privilege.
     */
    private class ModelStubWithoutWritePrivilege extends ModelStub {
        @Override
        public Account getUserAccount() {
            return TypicalAccount.READ_ONLY_USER_ACCOUNT;
        }
    }

    /**
     * A Model stub that always accept the client being added.
     */
    private class ModelStubAcceptingContactAdded extends ModelStub {
        final ArrayList<Contact> contactsAdded = new ArrayList<>();

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return contactsAdded.stream().anyMatch(contact::isSameContact);
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            contactsAdded.add(contact);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public Account getUserAccount() {
            return TypicalAccount.ROOTACCOUNT;
        }
    }

}
