package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VOLUNTEER;

import org.junit.Test;

import seedu.address.logic.commands.ExportCertCommand;

public class ExportCertCommandParserTest {
    private ExportCertCommandParser parser = new ExportCertCommandParser();

    @Test
    public void parse_validArgs_returnsExportCertCommand() {
        String userInput = Integer.toString(INDEX_FIRST_VOLUNTEER.getOneBased());

        assertParseSuccess(parser, userInput, new ExportCertCommand(INDEX_FIRST_VOLUNTEER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "";

        // no argument provided --> fail
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCertCommand.MESSAGE_USAGE));

        // alphabet given instead of number --> fail
        userInput = " a";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCertCommand.MESSAGE_USAGE));
    }
}
