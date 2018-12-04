package seedu.thanepark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.OpenCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new OpenCommand object
 */

public class OpenCommandParser implements Parser<OpenCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns an OpenCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public OpenCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE), pe);
        }
        return new OpenCommand(index);
    }
}
