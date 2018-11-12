package seedu.address.logic.commands.friend;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserData;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.TypicalUsers;
import seedu.address.testutil.UserBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddFriendCommand}.
 */
public class AddFriendCommandIntegrationTest {
    private Model model;

    private CommandHistory commandHistory = new CommandHistory();

    private Username validUsernameA = TypicalUsers.getTypicalUsers().get(3).getUsername();

    private User validUserA = TypicalUsers.getTypicalUsers().get(3);

    private Username invalidUser = new Username("NOTAUSER");

    private Username currentUserName = TypicalUsers.getTypicalUsers().get(1).getUsername();

    private User currentUser = TypicalUsers.getTypicalUsers().get(1);

    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs(),
                TypicalUsers.getTypicalUserData(), TypicalUsers.getTypicalUsers().get(1));
    }

    @Test
    public void execute_newAddFriend_success() throws CommandException {
        // need to create copies of the users to prevent users in model and expectedModel
        // to have the same reference and thus accidentally carrying out methods on them
        User userACopy = new UserBuilder().withEmail(validUserA.getEmail().toString())
                .withName(validUserA.getName().toString())
                .withPassword(validUserA.getPassword().toString())
                .withPhone(validUserA.getPhone().toString())
                .withUsername(validUserA.getUsername().toString())
                .build();
        User currentUserCopy = new UserBuilder().withEmail(currentUser.getEmail().toString())
                .withName(currentUser.getName().toString())
                .withPassword(currentUser.getPassword().toString())
                .withPhone(currentUser.getPhone().toString())
                .withUsername(currentUser.getUsername().toString())
                .build();
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                new UserData(), currentUserCopy);
        expectedModel.addUser(currentUserCopy);
        expectedModel.addUser(userACopy);
        expectedModel.addFriend(userACopy.getUsername());
        assertCommandSuccess(new AddFriendCommand(validUsernameA), model, commandHistory,
                String.format(AddFriendCommand.MESSAGE_SUCCESS, validUsernameA), expectedModel);
    }

    /**
     * Throw exception if no user is currently logged in
     */
    @Test
    public void execute_notLoggedIn() {
        Model modelNotLoggedIn = new ModelManager(new AddressBook(), new UserPrefs(),
                TypicalUsers.getTypicalUserData());
        assertCommandFailure(new AddFriendCommand(validUsernameA), modelNotLoggedIn, commandHistory,
                String.format(AddFriendCommand.MESSAGE_NOT_LOGGED_IN, AddFriendCommand.COMMAND_WORD));
    }

    /**
     * Throw exception if the user to send friend request to is not a valid user (in model)
     */
    @Test
    public void execute_userNotInModel() {
        assertCommandFailure(new AddFriendCommand(invalidUser), model, commandHistory,
                AddFriendCommand.MESSAGE_NO_SUCH_USER);
    }

    /**
     * Throw exception if user tries to add oneself as a friend
     */
    @Test
    public void execute_isSameUser() {
        assertCommandFailure(new AddFriendCommand(currentUserName), model, commandHistory,
                AddFriendCommand.MESSAGE_CANNOT_ADD_ONESELF);
    }

    /**
     * Throw exception if current user tries to send multiple friend requests to the same user
     */
    @Test
    public void execute_sameAddFriend_success() {
        model.addFriend(validUsernameA);
        assertCommandFailure(new AddFriendCommand(validUsernameA), model, commandHistory,
                AddFriendCommand.MESSAGE_DUPLICATE_FRIEND_REQUEST);
    }

}
