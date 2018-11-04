package seedu.restaurant.logic.parser.reservation;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATETIME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PAX;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.restaurant.logic.commands.reservation.AddReservationCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Remark;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.tag.Tag;

//@@author m4dkip
/**
 * Parses input arguments and creates a new AddReservationCommand object
 */
public class AddReservationCommandParser implements Parser<AddReservationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReservationCommand
     * and returns an AddReservationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReservationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PAX, PREFIX_DATETIME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PAX, PREFIX_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddReservationCommand.MESSAGE_USAGE));
        }

        Name name = ReservationParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Pax pax = ReservationParserUtil.parsePax(argMultimap.getValue(PREFIX_PAX).get());
        LocalDateTime dateTime = ReservationParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());

        Remark remark = new Remark("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Reservation reservation = new Reservation(name, pax, dateTime, remark, tagList);

        return new AddReservationCommand(reservation);
    }
}
