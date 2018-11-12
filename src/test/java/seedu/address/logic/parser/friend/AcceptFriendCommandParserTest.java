package seedu.address.logic.parser.friend;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFriendships.FRIENDSHIP_1;

import org.junit.Test;

import seedu.address.logic.commands.friend.AcceptFriendCommand;
import seedu.address.model.user.Username;

public class AcceptFriendCommandParserTest {
    private AcceptFriendCommandParser parser = new AcceptFriendCommandParser();

    /**
     * Test for successful execution
     */
    @Test
    public void parse_allFieldsPresent_success() {
        final String usernameString = " " + PREFIX_USERNAME + FRIENDSHIP_1.getFriendUsername().toString();
        final Username usernameToAccept = FRIENDSHIP_1.getFriendUser().getUsername();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + usernameString,
                new AcceptFriendCommand(usernameToAccept));
    }

    /**
     * Test failure with wrong field
     */
    @Test
    public void parse_compulsoryFieldWrong_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AcceptFriendCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "hello", expectedMessage);
    }
}
