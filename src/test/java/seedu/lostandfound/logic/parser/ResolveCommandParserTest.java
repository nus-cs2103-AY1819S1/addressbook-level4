package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_POWERBANK;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.lostandfound.logic.commands.ResolveCommand;

public class ResolveCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResolveCommand.MESSAGE_USAGE);

    private ResolveCommandParser parser = new ResolveCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_POWERBANK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ResolveCommand.MESSAGE_NOT_RESOLVED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_POWERBANK, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_POWERBANK, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

}
