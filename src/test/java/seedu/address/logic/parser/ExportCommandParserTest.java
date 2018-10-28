package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {
    private ExportCommandParser parser = new ExportCommandParser();
    
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    
    private static final String NO_USER_INPUT = "";
    private static final String NO_DOT_CSV = "contacts";
    private static final String PROPER_CSV = "contacts.csv";


    @Test
    public void parse_no_input() {
        // no export file name given
        assertParseFailure(parser, NO_USER_INPUT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_no_csv() {
        // export file name has no .csv
        assertParseFailure(parser, NO_DOT_CSV, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_proper_format() {
        // proper export file name given: contacts.csv
        String correct = "contacts.csv";
        ExportCommand expectedCommand = new ExportCommand(correct);
        assertParseSuccess(parser, PROPER_CSV, expectedCommand);
    }
}