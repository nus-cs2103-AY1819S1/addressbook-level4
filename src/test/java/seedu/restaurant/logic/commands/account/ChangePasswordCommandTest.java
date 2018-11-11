package seedu.restaurant.logic.commands.account;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.accounts.LoginEvent;
import seedu.restaurant.commons.events.ui.accounts.LogoutEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.account.ChangePasswordCommand.EditAccountDescriptor;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.testutil.account.EditAccountDescriptorBuilder;

//@@author AZhiKai
public class ChangePasswordCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // Logs into the first account in the account list with a hashed password
        EventsCenter.getInstance().post(new LoginEvent(model.getFilteredAccountList().get(0)));
    }

    @After
    public void reset() {
        EventsCenter.getInstance().post(new LogoutEvent());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Account editedAccount = new AccountBuilder().build();
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(
                new EditAccountDescriptorBuilder(editedAccount).build());

        String expectedMessage = String.format(ChangePasswordCommand.MESSAGE_SUCCESS, editedAccount);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateAccount(model.getFilteredAccountList().get(0), editedAccount);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(changePasswordCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(new EditAccountDescriptor());
        Account editedAccount = model.getFilteredAccountList().get(0);

        String expectedMessage = String.format(ChangePasswordCommand.MESSAGE_SUCCESS, editedAccount);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(changePasswordCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Account accountInFilteredList = model.getFilteredAccountList().get(0);
        Account editedAccount = new AccountBuilder(accountInFilteredList).withPassword("newpassword").build();
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(
                new EditAccountDescriptorBuilder().withPassword("newpassword").build());

        String expectedMessage = String.format(ChangePasswordCommand.MESSAGE_SUCCESS, editedAccount);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateAccount(model.getFilteredAccountList().get(0), editedAccount);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(changePasswordCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
