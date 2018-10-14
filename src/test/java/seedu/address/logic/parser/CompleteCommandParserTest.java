package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.CompleteCommand;
import seedu.address.model.task.LabelMatchesKeywordPredicate;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CompleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the CompleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CompleteCommandParserTest {

    private CompleteCommandParser parser = new CompleteCommandParser();
    private final LabelMatchesKeywordPredicate FRIENDS_LABEL = new LabelMatchesKeywordPredicate("friends");

    @Test
    public void parse_validArgs_returnsCompleteCommand() {
        // Arguments with no whitespaces
        assertParseSuccess(parser, "1", new CompleteCommand(INDEX_FIRST_TASK));

        // Arguments with leading whitepaces
        assertParseSuccess(parser, " 1", new CompleteCommand(INDEX_FIRST_TASK));
        assertParseSuccess(parser, " l/friends", new CompleteCommand(FRIENDS_LABEL));

        // Arguments with leading and trailing whitespaces
        assertParseSuccess(parser, "  1  ", new CompleteCommand(INDEX_FIRST_TASK));
        assertParseSuccess(parser, "  l/ friends  ", new CompleteCommand(FRIENDS_LABEL));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // No valid symbols
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteCommand.MESSAGE_USAGE));

        // No whitespace before label
        assertParseFailure(parser, "l/friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));

        // Mix of Label and Index symbols
        assertParseFailure(parser, "1 l/friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));

        // Multiple Index symbols
        assertParseFailure(parser, "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));

        // Improper Label symbol
        assertParseFailure(parser, " l\\friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));
    }

}
