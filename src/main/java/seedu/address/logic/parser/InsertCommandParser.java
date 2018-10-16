package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InsertCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class InsertCommandParser implements Parser<InsertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InsertCommand parse(String args) throws ParseException {

        try {
            String[] splitArgs = args.split(" ");
            if (splitArgs.length != 3) {
                throw new ParseException("");
            }

            Index index = ParserUtil.parseIndex(splitArgs[1]);
            Index position = ParserUtil.parseIndex(splitArgs[2]);
            return new InsertCommand(index, position);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertCommand.MESSAGE_USAGE), pe);
        }
    }
}
