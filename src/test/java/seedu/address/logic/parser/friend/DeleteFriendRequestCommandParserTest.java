package seedu.address.logic.parser.friend;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_1;

import org.junit.Test;

import seedu.address.logic.commands.friend.DeleteFriendRequestCommand;
import seedu.address.model.user.Username;

public class DeleteFriendRequestCommandParserTest {
    private DeleteFriendRequestCommandParser parser = new DeleteFriendRequestCommandParser();

    /**
     * Test for successful execution
     */
    @Test
    public void parse_validArgs_returnsDeleteFriendCommand() {
        final String usernameString = " " + PREFIX_USERNAME + FRIENDSHIP_1.getFriendUsername().toString();
        final Username usernameToDelete = FRIENDSHIP_1.getFriendUser().getUsername();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + usernameString,
                new DeleteFriendRequestCommand(usernameToDelete));
    }

    /**
     * Test failure with wrong field
     */
    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteFriendRequestCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "hello", expectedMessage);
    }
}
