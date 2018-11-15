package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewGroupAllAvailableTimeslotCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewGroupAllAvailableTimeSlotCommand object
 */
public class ViewGroupAllAvailableTimeslotCommandParser implements Parser<ViewGroupAllAvailableTimeslotCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupAllAvailableTimeSlotCommand
     * and returns an ViewGroupAllAvailableTimeSlotCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewGroupAllAvailableTimeslotCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewGroupAllAvailableTimeslotCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new ViewGroupAllAvailableTimeslotCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
