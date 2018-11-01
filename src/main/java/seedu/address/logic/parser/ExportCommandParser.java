//@@author chantca95
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses the user's given file name, then creates a new ExportCommand with the given file name
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    /**
     * Checks if the user's given file name contains the .csv suffix, and displays an error message if it does not.
     */
    public ExportCommand parse(String fileName) throws ParseException {

        int length = fileName.length();
        //checks whether the user's input ends with .csv
        if (length < 5) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        if (!fileName.substring(length - 4, length).equals(".csv")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        String trimmedFileName = fileName.trim();
        File temp = new File(trimmedFileName);
        if (temp.exists()) {
            throw new ParseException(ExportCommand.MESSAGE_DUPLICATE_FILE);
        }
        return new ExportCommand(trimmedFileName);
    }
}
