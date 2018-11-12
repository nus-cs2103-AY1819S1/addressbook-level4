package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.TypicalAccount;
import seedu.address.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
    }

    @Test
    public void execute_newClient_success() {
        Contact validContact = new ClientBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.addContact(validContact);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validContact), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validContact.getType(), validContact), expectedModel);
    }

    @Test
    public void execute_newServiceProvider_success() {
        Contact validContact = new VendorBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), TypicalAccount.ROOTACCOUNT);
        expectedModel.addContact(validContact);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validContact), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validContact.getType(), validContact), expectedModel);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        assertCommandFailure(new AddCommand(contactInList), model, commandHistory,
                String.format(AddCommand.MESSAGE_DUPLICATE_CONTACT, contactInList.getType()));
    }

    @Test
    public void execute_duplicateServiceProvider_throwsCommandException() {
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        assertCommandFailure(new AddCommand(contactInList), model, commandHistory,
                String.format(AddCommand.MESSAGE_DUPLICATE_CONTACT, contactInList.getType()));
    }

    /**
     * Tries to add a duplicate client while only the service providers are shown. In heartsquare, a client cannot be
     * a vendor and vice versa, so this should still throw a command exception
     */
    @Test
    public void execute_duplicateServiceProviderWhileClientListInFocus_throwsCommandException() {
        model.updateFilteredContactList(ContactType.CLIENT.getFilter());
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        model.updateFilteredContactList(ContactType.VENDOR.getFilter());
        assertCommandFailure(new AddCommand(contactInList), model, commandHistory,
                String.format(AddCommand.MESSAGE_DUPLICATE_CONTACT, contactInList.getType()));
    }

}
