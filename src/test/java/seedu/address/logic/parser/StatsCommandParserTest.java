package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.StatsCommand;

public class StatsCommandParserTest {
    private StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " n/7 m/m", new StatsCommand(7, "m"));
        assertParseSuccess(parser, " n/6 m/d", new StatsCommand(6, "d"));
    }

    @Test
    public void parse_noParams_success() {
        assertParseSuccess(parser, "", new StatsCommand(7, "d"));
    }

    @Test
    public void parse_fieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE);

        // missing mode prefix
        assertParseFailure(parser, " n/7", expectedMessage);

        // missing number
        assertParseFailure(parser, " m/d", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE);

        // number less than 0
        assertParseFailure(parser, " n/0 m/m", expectedMessage);

        // mode not d or m
        assertParseFailure(parser, " n/1 m/p", expectedMessage);

        // both params invalid
        assertParseFailure(parser, " n/0 m/p", expectedMessage);
    }
}
