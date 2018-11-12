package systemtests.friend;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import org.junit.Test;

import seedu.address.logic.commands.friend.AddFriendCommand;
import seedu.address.model.Model;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.TypicalUsers;
import seedu.address.testutil.UserBuilder;
import systemtests.AddressBookSystemTest;

public class AddFriendCommandSystemTest extends AddressBookSystemTest {
    private static final String VALID_USERNAME_A = TypicalUsers.getTypicalUsers().get(3).getUsername().toString();
    private static final String VALID_USERNAME_B = TypicalUsers.getTypicalUsers().get(4).getUsername().toString();
    private static final String INVALID_USERNAME = "Invalid";

    private User currentUser = TypicalUsers.getTypicalUsers().get(0);
    private User otherUserA = TypicalUsers.getTypicalUsers().get(3);
    private User otherUserB = TypicalUsers.getTypicalUsers().get(4);

    private Username userA = TypicalUsers.getTypicalUsers().get(3).getUsername();
    private Username userB = TypicalUsers.getTypicalUsers().get(4).getUsername();

    @Test
    public void add() {

        addUser(otherUserA);
        addUser(otherUserB);
        addUser(currentUser);
        login(currentUser);
        Model model = getModel();
        User userACopy = new UserBuilder().withUsername(otherUserA.getUsername().toString())
                .withPhone(otherUserA.getPhone().toString())
                .withPassword(otherUserA.getPassword().toString())
                .withName(otherUserA.getName().toString())
                .withEmail(otherUserA.getEmail().toString())
                .build();
        User userBCopy = new UserBuilder().withUsername(otherUserB.getUsername().toString())
                .withPhone(otherUserB.getPhone().toString())
                .withPassword(otherUserB.getPassword().toString())
                .withName(otherUserB.getName().toString())
                .withEmail(otherUserB.getEmail().toString())
                .build();
        User currentUserCopy = new UserBuilder().withUsername(currentUser.getUsername().toString())
                .withPhone(currentUser.getPhone().toString())
                .withPassword(currentUser.getPassword().toString())
                .withName(currentUser.getName().toString())
                .withEmail(currentUser.getEmail().toString())
                .build();

        model.addUser(userACopy);
        model.addUser(userBCopy);
        model.addUser(currentUserCopy);
        model.loginUser(currentUserCopy);

        String command = AddFriendCommand.COMMAND_WORD + " " + PREFIX_USERNAME + VALID_USERNAME_A;
        assertCommandSuccess(command, model, userA);

        command = AddFriendCommand.COMMAND_WORD + " " + PREFIX_USERNAME + VALID_USERNAME_B;
        assertCommandSuccess(command, model, userB);

        logout();
        model.logoutUser();
        assertCommandFailure(command, model, AddFriendCommand.MESSAGE_NOT_LOGGED_IN);

        login(userACopy);
        model.loginUser(userA);

        assertCommandSuccess(command, model, userB);
        assertCommandFailure(command, model, AddFriendCommand.MESSAGE_DUPLICATE_FRIEND_REQUEST);

        command = AddFriendCommand.COMMAND_WORD + " " + VALID_USERNAME_B;
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddFriendCommand.MESSAGE_USAGE));

        command = AddFriendCommand.COMMAND_WORD + " " + PREFIX_USERNAME + VALID_USERNAME_A;
        assertCommandFailure(command, model, AddFriendCommand.MESSAGE_CANNOT_ADD_ONESELF);

        logout();
        model.logoutUser();
        login(userBCopy);
        model.loginUser(userB);

        assertCommandFailure(command, model, AddFriendCommand.MESSAGE_ACCEPT_EXISTING_REQUEST);

        command = AddFriendCommand.COMMAND_WORD + " " + PREFIX_USERNAME + INVALID_USERNAME;
        assertCommandFailure(command, model, AddFriendCommand.MESSAGE_NO_SUCH_USER);
    }

    private void assertCommandSuccess(String command, Model expectedModel, Username toAdd) {
        expectedModel.addFriend(toAdd);
        String expectedResultMessage = String.format(AddFriendCommand.MESSAGE_SUCCESS, toAdd);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as
     * {@code assertCommandSuccess(String, Model, Username, Amount)} except asserts that the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} equal to the corresponding components in {@code expectedModel}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} remain unchanged.
     */
    private void assertCommandFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
    }

}
