package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deliveryman.DeliverymanDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments to produce a DeliverymanDeleteCommand.
 */
public class DeliverymanDeleteCommandParser implements Parser<DeliverymanDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments and returns a DeliverymanDeleteCommand
     * object for execution.
     * @throws ParseException if the input does not conform to the expected format
     */
    public DeliverymanDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeliverymanDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliverymanDeleteCommand.MESSAGE_USAGE), pe
            );
        }
    }
}
