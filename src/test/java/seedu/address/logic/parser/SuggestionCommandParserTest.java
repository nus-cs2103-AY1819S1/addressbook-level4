package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.commands.SuggestionCommand;

import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class SuggestionCommandParserTest {
    private SuggestionCommandParser parser = new SuggestionCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "  ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SuggestionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        SuggestionCommand expectedFindCommand =
                new SuggestionCommand("Alice");
        assertParseSuccess(parser, "Alice", expectedFindCommand);


    }
}
