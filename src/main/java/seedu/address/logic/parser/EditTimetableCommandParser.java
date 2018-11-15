package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTimetableCommand object
 */
public class EditTimetableCommandParser implements Parser<EditTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTimetableCommand and
     * returns an EditTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTimetableCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer
                .tokenize(args, PREFIX_DAY, PREFIX_TIMING, PREFIX_DETAILS);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTimetableCommand.MESSAGE_USAGE),
                pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_TIMING)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTimetableCommand.MESSAGE_USAGE));
        }
        ParserUtil.checkBothDayAndTiming(argMultimap.getValue(PREFIX_DAY).get(),
            argMultimap.getValue(PREFIX_TIMING).get());
        String day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        String timing = ParserUtil.parseTiming(argMultimap.getValue(PREFIX_TIMING).get());
        String details = " ";
        if (argMultimap.getValue(PREFIX_DETAILS).isPresent()) {
            details = ParserUtil.parseDetails(argMultimap.getValue(PREFIX_DETAILS).get());
        }
        return new EditTimetableCommand(index, day, timing, details);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
