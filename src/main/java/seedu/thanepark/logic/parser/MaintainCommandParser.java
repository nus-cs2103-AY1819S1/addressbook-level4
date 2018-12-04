package seedu.thanepark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.MaintainCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MaintainCommand object
 */
public class MaintainCommandParser implements Parser<MaintainCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MaintainCommand
     * and returns an MaintainCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MaintainCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaintainCommand.MESSAGE_USAGE), pe);
        }
        return new MaintainCommand(index);
    }
}
