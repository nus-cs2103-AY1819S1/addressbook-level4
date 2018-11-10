package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.logic.commands.DependencyCommand;

public class DependenciesCommandParserTest {
    private DependencyCommandParser parser = new DependencyCommandParser();
    @Test
    public void parse_validArgs_returnsCompleteCommand() {
        assertParseSuccess(parser, " 1 2", new DependencyCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " a b", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DependencyCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DependencyCommand.MESSAGE_USAGE));
    }
}
