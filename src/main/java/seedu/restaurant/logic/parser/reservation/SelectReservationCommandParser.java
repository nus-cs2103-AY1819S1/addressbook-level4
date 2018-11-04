package seedu.restaurant.logic.parser.reservation;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.reservation.SelectReservationCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author m4dkip
/**
 * Parses input arguments and creates a new SelectReservationCommand object
 */
public class SelectReservationCommandParser implements Parser<SelectReservationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectReservationCommand
     * and returns an SelectReservationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectReservationCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectReservationCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectReservationCommand.MESSAGE_USAGE), pe);
        }
    }
}
