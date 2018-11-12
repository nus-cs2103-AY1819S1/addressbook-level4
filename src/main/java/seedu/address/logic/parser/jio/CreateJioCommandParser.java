package seedu.address.logic.parser.jio;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.stream.Stream;

import seedu.address.logic.commands.jio.CreateJioCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserRestaurantUtil;
import seedu.address.logic.parser.ParserUserUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;

/**
 * Parses input arguments and creates a new CreateJioCommand object
 */
public class CreateJioCommandParser implements Parser<CreateJioCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddJioCommand
     * and returns an AddJioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateJioCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_WEEK, PREFIX_DAY, PREFIX_TIME, PREFIX_ADDRESS,
                        PREFIX_GROUP);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_WEEK, PREFIX_DAY, PREFIX_TIME, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));
        }

        Jio jio;
        Name name = ParserUserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Week week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Address location = ParserRestaurantUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Date date = new Date(week, day, time);

        if (arePrefixesPresent(argMultimap, PREFIX_GROUP)) {
            Name groupName = ParserUserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
            jio = new Jio(name, date, location, groupName);
        } else {
            jio = new Jio(name, date, location);
        }


        return new CreateJioCommand(jio);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
