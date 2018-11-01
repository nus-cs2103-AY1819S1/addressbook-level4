package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.commands.CompleteIndexCommand;
import seedu.address.logic.commands.CompleteLabelCommand;
import seedu.address.model.task.LabelMatchesKeywordPredicate;

/**
 * Tests the CompleteCommandParser's ability to handle Index(es) and Labels
 * and parse accordingly.
 */
public class CompleteCommandParserTest {

    private static final LabelMatchesKeywordPredicate LABEL_FRIENDS = new LabelMatchesKeywordPredicate("friends");
    private CompleteCommandParser parser = new CompleteCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteCommand() {
        // Arguments with no whitespaces
        assertParseSuccess(parser, "1", new CompleteIndexCommand(INDEX_FIRST_TASK));

        // Arguments with leading whitepaces
        assertParseSuccess(parser, " 1", new CompleteIndexCommand(INDEX_FIRST_TASK));
        assertParseSuccess(parser, " l/friends", new CompleteLabelCommand(LABEL_FRIENDS));

        // Arguments with leading and trailing whitespaces
        assertParseSuccess(parser, "  1  ", new CompleteIndexCommand(INDEX_FIRST_TASK));
        assertParseSuccess(parser, "  l/ friends  ", new CompleteLabelCommand(LABEL_FRIENDS));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // No valid symbols
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));

        // No whitespace before label
        assertParseFailure(parser, "l/friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));

        // Mix of Label and Index symbols (check both permutation of symbols)
        assertParseFailure(parser, "1 l/friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "l/friends 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CompleteCommand.MESSAGE_USAGE));

        // Multiple Index symbols
        assertParseFailure(parser, "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));

        // Improper Label symbol
        assertParseFailure(parser, " l\\friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            CompleteCommand.MESSAGE_USAGE));
    }

}
