package seedu.address.logic.parser.timetable;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.stream.Stream;

import seedu.address.logic.commands.timetable.FindDatesCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUserUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.timetable.Week;


/**
 * Parses input arguments and creates a new ListScheduleCommand object.
 */
public class FindDatesCommandParser implements Parser<FindDatesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListScheduleCommand
     * and returns a ListScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindDatesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEEK, PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEEK, PREFIX_GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDatesCommand.MESSAGE_USAGE));
        }

        Name groupName = ParserUserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        Week week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());

        return new FindDatesCommand(groupName, week);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
