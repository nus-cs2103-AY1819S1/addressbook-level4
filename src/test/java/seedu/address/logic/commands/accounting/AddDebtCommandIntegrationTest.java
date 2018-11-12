package seedu.address.logic.commands.accounting;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserData;
import seedu.address.model.UserPrefs;
import seedu.address.model.accounting.Amount;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.TypicalUsers;
import seedu.address.testutil.UserBuilder;

public class AddDebtCommandIntegrationTest {

    private Model model;

    private CommandHistory commandHistory = new CommandHistory();

    private Username validUserA = TypicalUsers.getTypicalUsers().get(0).getUsername();

    private Username invalidUser = new Username("NOTAUSER");

    private Username currentUserName = TypicalUsers.getTypicalUsers().get(1).getUsername();

    private User userA = TypicalUsers.getTypicalUsers().get(0);

    private User currentUser = TypicalUsers.getTypicalUsers().get(1);

    private Amount validAmountA = new Amount("17");

    private Amount invalidAmountA = new Amount("0");

    private Amount invalidAmountB = new Amount("999999999");

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs(),
                TypicalUsers.getTypicalUserData(), currentUser);
    }

    /**
     *Test for successful execution of AddDebtCommand.
     */
    @Test
    public void execute_newDebt_success() throws CommandException {
        //Create expected model
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                new UserData());
        //Create copy of user to prevent referencing
        User userACopy = new UserBuilder().withEmail(userA.getEmail().toString())
                .withName(userA.getName().toString())
                .withPassword(userA.getPassword().toString())
                .withPhone(userA.getPhone().toString())
                .withUsername(userA.getUsername().toString())
                .build();
        User currentUserCopy = new UserBuilder().withEmail(currentUser.getEmail().toString())
                .withName(currentUser.getName().toString())
                .withPassword(currentUser.getPassword().toString())
                .withPhone(currentUser.getPhone().toString())
                .withUsername(currentUser.getUsername().toString())
                .build();
        //Add to user to the expected model
        expectedModel.addUser(userACopy);
        expectedModel.addUser(currentUserCopy);
        //Add a current user to expected model(Login)
        expectedModel.loginUser(currentUser);
        //Add the expected debt to expected model
        expectedModel.addDebt(validUserA, validAmountA);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertCommandSuccess(new AddDebtCommand(validUserA, validAmountA), model, commandHistory,
                String.format(AddDebtCommand.MESSAGE_SUCCESS, validUserA, validAmountA.toDouble()), expectedModel);
    }

    /**
     * Test the failure when there are no login user in the model.
     */
    @Test
    public void execute_notLoggedIn() {
        //Remove the current user from model(Logout)
        model.logoutUser();
        assertCommandFailure(new AddDebtCommand(validUserA, validAmountA), model, commandHistory,
                String.format(Messages.MESSAGE_USER_NOT_LOGGED_IN_FOR_COMMAND, AddDebtCommand.COMMAND_WORD));
        //Add the current user back for the following test
        model.loginUser(currentUser);
    }

    /**
     * Test the failure when the input debtor is not in the model.
     */
    @Test
    public void execute_userNotInModel() {
        assertCommandFailure(new AddDebtCommand(invalidUser, validAmountA), model, commandHistory,
                AddDebtCommand.MESSAGE_NO_SUCH_USER);
    }

    /**
     * Test the failure when there the input debtor is the login user.
     */
    @Test
    public void execute_isSameUser() {
        assertCommandFailure(new AddDebtCommand(currentUserName, validAmountA), model, commandHistory,
                AddDebtCommand.MESSAGE_CANNOT_ADD_DEBT_TO_ONESELF);
    }

    /**
     * Test the failure when input amount is 0.
     */
    @Test
    public void execute_amountZero_throwsCommandException() {
        assertCommandFailure(new AddDebtCommand(validUserA, invalidAmountA), model, commandHistory,
                AddDebtCommand.MESSAGE_INVALID_AMOUNT);
    }

    /**
     * Test the failure when input amount is larger than 100000000.
     */
    @Test
    public void execute_amountTooLarge_throwsCommandException() {
        assertCommandFailure(new AddDebtCommand(validUserA, invalidAmountB), model, commandHistory,
                AddDebtCommand.MESSAGE_INVALID_AMOUNT);
    }

}
