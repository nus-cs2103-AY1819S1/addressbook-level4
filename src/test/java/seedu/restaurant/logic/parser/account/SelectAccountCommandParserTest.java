package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.restaurant.logic.commands.account.SelectAccountCommand;

//@@author AZhikai
public class SelectAccountCommandParserTest {

    private SelectAccountCommandParser parser = new SelectAccountCommandParser();

    @Test
    public void parse_validArgs_returnsSelectAccountCommand() {
        assertParseSuccess(parser, "1", new SelectAccountCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // input character
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectAccountCommand.MESSAGE_USAGE));
        // input index 0
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectAccountCommand.MESSAGE_USAGE));
        // input negative index
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectAccountCommand.MESSAGE_USAGE));
        // input empty
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectAccountCommand.MESSAGE_USAGE));
        // input space
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectAccountCommand.MESSAGE_USAGE));

    }
}
