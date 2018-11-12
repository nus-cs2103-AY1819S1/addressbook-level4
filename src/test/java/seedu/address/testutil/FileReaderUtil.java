package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.model.filereader.FileReader;

/**
 * A utility class for FileReader.
 */
public class FileReaderUtil {
    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getImportCommand(FileReader fileReader) {
        return ImportContactsCommand.COMMAND_WORD + " " + PREFIX_FILE + fileReader.getCsvFilePath();
    }

    public static String getImportCommandAlias(FileReader fileReader) {
        return ImportContactsCommand.COMMAND_WORD_ALIAS + " " + PREFIX_FILE + fileReader.getCsvFilePath();
    }
}
