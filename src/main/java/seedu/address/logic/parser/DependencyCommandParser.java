package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DependencyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DependencyCommand object
 */
public class DependencyCommandParser implements Parser<DependencyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DependencyCommand
     * and returns an CompleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DependencyCommand parse(String args) throws ParseException {
        try {
            //TODO: Improve this part of the code to handle empty dependency
            String[] parts = args.split(" ");
            Index dependeeIndex = ParserUtil.parseIndex(parts[1]);
            Index dependantIndex = ParserUtil.parseIndex(parts[2]);
            return new DependencyCommand(dependeeIndex, dependantIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DependencyCommand.MESSAGE_USAGE), pe);
        }
    }
}
