package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deliveryman.DeliverymanSelectCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeliverymanSelectCommand object.
 */
public class DeliverymanSelectCommandParser implements Parser<DeliverymanSelectCommand> {
    /**
     * Parses the fiven {@code string} of srgumants in the context of the DeliverymanSelectCommand
     * and returns a DeliverymanSelectCommand object for execution.
     */
    public DeliverymanSelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeliverymanSelectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliverymanSelectCommand.MESSAGE_USAGE), pe);
        }
    }
}
