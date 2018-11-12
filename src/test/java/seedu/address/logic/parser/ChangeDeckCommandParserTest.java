package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.logic.commands.ChangeDeckCommand;

public class ChangeDeckCommandParserTest {

    private ChangeDeckCommandParser parser = new ChangeDeckCommandParser();

    @Test
    public void parse_validArgs_returnsCdCommand() {
        assertParseSuccess(parser, "1", new ChangeDeckCommand(INDEX_FIRST_DECK));
    }

    @Test
    public void parse_validExit_returnsCdCommand() {
        assertParseSuccess(parser, ChangeDeckCommand.EXIT_DECK_ARGS, new ChangeDeckCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeDeckCommand.MESSAGE_USAGE));
    }
}
