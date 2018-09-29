package seedu.address.logic.parser.eventparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.eventcommands.VoteCommand;
import seedu.address.logic.parser.eventparsers.VoteCommandParser;
import seedu.address.testutil.TypicalIndexes;

public class VoteCommandParserTest {
    private VoteCommandParser parser = new VoteCommandParser();

    @Test
    public void parse_validArgs_returnsVoteCommand() {
        assertParseSuccess(parser, " i/1 o/12 August",
                new VoteCommand(TypicalIndexes.INDEX_FIRST, "12 August"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 o/12 August", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VoteCommand.MESSAGE_USAGE));
    }
}
