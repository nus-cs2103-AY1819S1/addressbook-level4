package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_VALUES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE_TIME;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE_TIME,
                        PREFIX_EVENT_END_DATE_TIME, PREFIX_EVENT_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());

        DateTime startDateTime = argMultimap.getValue(PREFIX_EVENT_START_DATE_TIME).isPresent() ?
                ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START_DATE_TIME).get()) :
                new DateTime(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1));
        DateTime endDateTime = argMultimap.getValue(PREFIX_EVENT_END_DATE_TIME).isPresent() ?
                ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_END_DATE_TIME).get()) :
                new DateTime(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(2));
        Description description = argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).isPresent() ?
                ParserUtil.parseEventDescription(argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).get()) :
                new Description("");

        if (!Event.isValidEventDateTime(startDateTime, endDateTime)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_VALUES, Event.MESSAGE_DATETIME_CONSTRAINTS));
        }

        Event event = new Event(eventName, startDateTime, endDateTime, description);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
