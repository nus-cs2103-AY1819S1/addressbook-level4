package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.guest.Email;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.Name;
import seedu.address.model.guest.Phone;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                    PREFIX_EMAIL, PREFIX_TAG, PREFIX_ROOM,
                    PREFIX_DATE_START, PREFIX_DATE_END);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROOM,
                PREFIX_DATE_START,
                PREFIX_DATE_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        RoomNumber roomNumber =
                ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM).get());
        BookingPeriod bookingPeriod =
                ParserUtil.parseBookingPeriod(argMultimap.getValue(PREFIX_DATE_START).get(),
                        argMultimap.getValue(PREFIX_DATE_END).get());
        Guest guest = new Guest(name, phone, email, tagList);

        return new AddCommand(guest, roomNumber, bookingPeriod);
    }

}
