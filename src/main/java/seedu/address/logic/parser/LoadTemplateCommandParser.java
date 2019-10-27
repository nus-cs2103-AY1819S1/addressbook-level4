package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Paths;

import seedu.address.logic.commands.LoadTemplateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class LoadTemplateCommandParser implements Parser<LoadTemplateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoadTemplateCommand
     * and returns a LoadTemplate object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadTemplateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadTemplateCommand.MESSAGE_USAGE));
        }

        String[] fileName = trimmedArgs.split("\\s+");

        return new LoadTemplateCommand(Paths.get(fileName[0]));
    }
}
