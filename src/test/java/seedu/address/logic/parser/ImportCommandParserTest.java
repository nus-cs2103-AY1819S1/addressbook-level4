package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.ImportFileUtil.TYPICAL_FILE;

import org.junit.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.model.portation.ImportFile;
import seedu.address.testutil.ImportFileUtil;


public class ImportCommandParserTest {
    private static final String INVALID_PATH =
            "A://MY COMPUTER/123\0/file.txt"; // illegal character "\0"
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ImportFile typicalFile = ImportFileUtil.getTypicalImportFile();

        // normal
        assertParseSuccess(parser, TYPICAL_FILE.toString(), new ImportCommand(typicalFile));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPICAL_FILE.toString(), new ImportCommand(typicalFile));
    }

    @Test
    public void parse_invalidValue_failure() {
        // empty string
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // illegal character
        assertParseFailure(parser, INVALID_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
