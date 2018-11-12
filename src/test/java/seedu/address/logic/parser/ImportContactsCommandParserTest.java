package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_READER_INVALID_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.FileReaderBuilder.DEFAULT_CSV_FILE_PATH;
import static seedu.address.testutil.FileReaderBuilder.EMPTY_CSV_FILE_PATH;
import static seedu.address.testutil.FileReaderBuilder.FILE_DO_NOT_EXIST_PATH;
import static seedu.address.testutil.FileReaderBuilder.HEADER_ONLY_CSV_FILE_PATH;
import static seedu.address.testutil.FileReaderBuilder.INVALID_FILE;

import org.junit.Test;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.model.filereader.FilePath;
import seedu.address.model.filereader.FileReader;
import seedu.address.testutil.FileReaderBuilder;

public class ImportContactsCommandParserTest {
    private ImportContactsCommandParser parser = new ImportContactsCommandParser();

    @Test
    public void parse_fieldPresent_success() {
        FileReader fileReader = new FileReaderBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_DESC + DEFAULT_CSV_FILE_PATH,
                new ImportContactsCommand(fileReader));

        // multiple files - last file accepted
        assertParseSuccess(parser, FILE_DESC + EMPTY_CSV_FILE_PATH
                + FILE_DESC + DEFAULT_CSV_FILE_PATH, new ImportContactsCommand(fileReader));
    }

    @Test
    public void parse_fieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportContactsCommand.MESSAGE_USAGE);

        // missing file prefix
        assertParseFailure(parser, DEFAULT_CSV_FILE_PATH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_FILE_READER_INVALID_FORMAT,
                ImportContactsCommand.MESSAGE_WRONG_FILE_FORMAT);
        FileReader fileReader = new FileReaderBuilder().headerOnly().build();

        // invalid file, header only csv
        assertParseSuccess(parser, FILE_DESC + HEADER_ONLY_CSV_FILE_PATH,
                new ImportContactsCommand(fileReader));

        // invalid file, wrong header format
        assertParseFailure(parser, FILE_DESC + INVALID_FILE, expectedMessage);

        // empty file
        assertParseFailure(parser, FILE_DESC + EMPTY_CSV_FILE_PATH, expectedMessage);

        // non existent file
        assertParseFailure(parser, FILE_DESC + FILE_DO_NOT_EXIST_PATH, FilePath.MESSAGE_FILEPATH_CONSTRAINTS);
    }
}
