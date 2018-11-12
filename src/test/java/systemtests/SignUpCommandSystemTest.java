package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_NAME_IDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_PASSWORD_IDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_HOON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_USERNAME_IDA;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_ALREADY_LOGGED_IN;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_REDIRECT_TO_LOGOUT;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_DUPLICATE_USER;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_LOGGED_IN;
import static seedu.address.logic.commands.SignUpCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.model.common.Name.MESSAGE_NAME_CONSTRAINTS;
import static seedu.address.model.common.Password.MESSAGE_PASSWORD_CONSTRAINTS;
import static seedu.address.model.common.Username.MESSAGE_USERNAME_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.SignUpCommand;
import seedu.address.model.Model;
import seedu.address.model.user.User;
import seedu.address.testutil.user.UserBuilder;

public class SignUpCommandSystemTest extends OrderBookSystemTest {

    private String signupCommand = SignUpCommand.COMMAND_WORD + " ";

    @Test
    public void cannotSignupAfterSignup() {
        Model expectedModel = getModel();
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_IDA
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_IDA
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_IDA;

        User ida = new UserBuilder()
                .withName(VALID_MANAGER_NAME_IDA)
                .withUsername(VALID_MANAGER_USERNAME_IDA)
                .withPassword(VALID_MANAGER_PASSWORD_IDA)
                .build();

        String expectedResultMessage = String.format(MESSAGE_SUCCESS, ida)
                + "\n"
                + MESSAGE_LOGGED_IN;


        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_HOON
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_HOON
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_HOON;

        User hoon = new UserBuilder()
                .withName(VALID_MANAGER_NAME_HOON)
                .withUsername(VALID_MANAGER_USERNAME_HOON)
                .withPassword(VALID_MANAGER_PASSWORD_HOON)
                .build();

        expectedResultMessage = String.format(MESSAGE_ALREADY_LOGGED_IN, ida.getUsername())
                + "\n"
                + MESSAGE_REDIRECT_TO_LOGOUT;
        assertCommandFailure(command, expectedResultMessage);
    }

    @Test
    public void signup_exisitingUser_failure() {
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_ALICE
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;

        assertCommandFailure(command, MESSAGE_DUPLICATE_USER);
    }

    @Test
    public void signup_invalidName_failure() {
        String command = signupCommand + PREFIX_NAME + INVALID_NAME_DESC
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;

        assertCommandFailure(command, MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void signup_invalidUsername_failure() {
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_ALICE
                + " " + PREFIX_USERNAME + INVALID_USERNAME_DESC
                + " " + PREFIX_PASSWORD + VALID_MANAGER_PASSWORD_ALICE;

        assertCommandFailure(command, MESSAGE_USERNAME_CONSTRAINTS);
    }

    @Test
    public void signup_invalidPassword_failure() {
        String command = signupCommand + PREFIX_NAME + VALID_MANAGER_NAME_ALICE
                + " " + PREFIX_USERNAME + VALID_MANAGER_USERNAME_ALICE
                + " " + PREFIX_PASSWORD + INVALID_PASSWORD_DESC;

        assertCommandFailure(command, MESSAGE_PASSWORD_CONSTRAINTS);
    }


    /**
     * Assert that signup command succeed. Command box style should be default.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Assert that signup command fails. Command box style should change.
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
