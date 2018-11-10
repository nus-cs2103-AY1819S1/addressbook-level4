package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * .
 */
public class SaveCommandParser implements Parser<SaveCommand> {

    private ArrayList<String> formats = new ArrayList<>(
            Arrays.asList("jpg", "JPG", "jpeg", "JPEG", "tiff", "TIFF", "gif", "GIF", "png", "PNG"));

    /**
     * Parses the given {@code String} of arguments in the context of the ExampleCommand
     * and returns an ExampleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveCommand parse(String args) throws ParseException {
        String[] all = args.split(" ");
        if (all.length == 1 && args.trim().equals("")) {
            return new SaveCommand();
        }
        if (all.length != 2 || isFormatValid(all[1])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
        return new SaveCommand(all[1]);
    }

    private boolean isFormatValid(String fileName) {
        String[] parts = fileName.split("\\.");
        return parts.length != 2 || !formats.contains(parts[1]);
    }
}
