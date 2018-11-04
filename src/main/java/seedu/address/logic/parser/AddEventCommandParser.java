package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Location;
import seedu.address.model.event.Name;
import seedu.address.model.event.Time;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_LOCATION, PREFIX_EVENT_START_DATE,
                        PREFIX_EVENT_END_DATE, PREFIX_EVENT_START_TIME, PREFIX_EVENT_END_TIME, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_LOCATION, PREFIX_EVENT_START_DATE,
                        PREFIX_EVENT_END_DATE, PREFIX_EVENT_START_TIME, PREFIX_EVENT_END_TIME, PREFIX_EVENT_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Name name = ParserEventUtil.parseName(argMultimap.getValue(PREFIX_EVENT_NAME).get());
        Location location = ParserEventUtil.parseLocation(argMultimap.getValue(PREFIX_EVENT_LOCATION).get());
        Date startDate = ParserEventUtil.parseDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
        Date endDate = ParserEventUtil.parseDate(argMultimap.getValue(PREFIX_EVENT_END_DATE).get());
        ParserEventUtil.parseStartEndDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get(),
                                                                argMultimap.getValue(PREFIX_EVENT_END_DATE).get());
        Time startTime = ParserEventUtil.parseTime(argMultimap.getValue(PREFIX_EVENT_START_TIME).get());
        Time endTime = ParserEventUtil.parseTime(argMultimap.getValue(PREFIX_EVENT_END_TIME).get());
        ParserEventUtil.parseStartEndTime(argMultimap.getValue(PREFIX_EVENT_START_TIME).get(),
                                                                argMultimap.getValue(PREFIX_EVENT_END_TIME).get());
        Description description = ParserEventUtil.parseDescription(argMultimap.getValue(PREFIX_EVENT_DESCRIPTION)
                                                                                                            .get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event person = new Event(name, location, startDate, endDate, startTime, endTime, description, tagList);

        return new AddEventCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
