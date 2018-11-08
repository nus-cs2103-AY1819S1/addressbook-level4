package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventAddressCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;

/**
 * Parses input arguments and creates a new EditEventAddressCommand object
 */
public class EditEventAddressCommandParser implements Parser<EditEventAddressCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventAddressCommand
     * and returns an EditEventAddressCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditEventAddressCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_INDEX, PREFIX_ADDRESS);

        // check for mandatory fields, and that no other data is entered between the command and first argument prefix
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_INDEX, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventAddressCommand.MESSAGE_USAGE));
        }

        EventDate eventDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_DATE).get());
        Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        EventAddress eventAddress = ParserUtil.parseEventAddress(argMultimap.getValue(PREFIX_ADDRESS).get());

        return new EditEventAddressCommand(eventDate, eventIndex, eventAddress);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
