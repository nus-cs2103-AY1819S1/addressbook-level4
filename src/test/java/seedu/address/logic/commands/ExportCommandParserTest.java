package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.parser.ExportCommandParser;
import seedu.address.logic.parser.ParserUtil;


public class ExportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no export file type specified
        assertParseFailure(parser, "exported.xml", MESSAGE_INVALID_FORMAT);

        // no filepath specified
        assertParseFailure(parser, "--xml", MESSAGE_INVALID_FORMAT);

        // no export file type and filepath specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validXmlFileExport_success() {
        String userInput = "--xml exported.xml";
        String path = "exported.xml";
        ExportXmlCommand expectedCommand =
                new ExportXmlCommand(ParserUtil.parseFilePath(path), ExportCommand.FileType.XML);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validTxtFileExport_success() {
        String userInput = "--txt exported.txt";
        String path = "exported.txt";
        ExportTxtCommand expectedCommand =
                new ExportTxtCommand(ParserUtil.parseFilePath(path), ExportCommand.FileType.TXT);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
