package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_VALUES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Priority;
import seedu.address.model.event.RepeatType;
import seedu.address.model.event.Venue;

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
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_START_DATE_TIME,
                        PREFIX_END_DATE_TIME, PREFIX_DESCRIPTION, PREFIX_PRIORITY,
                        PREFIX_VENUE, PREFIX_REPEAT_TYPE, PREFIX_REPEAT_UNTIL_DATE_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());

        DateTime startDateTime = argMultimap.getValue(PREFIX_START_DATE_TIME).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME).get())
                : new DateTime(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1));
        DateTime endDateTime = argMultimap.getValue(PREFIX_END_DATE_TIME).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME).get())
                : new DateTime(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(2));
        Description description = argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()
                ? ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get())
                : new Description("");
        Priority priority = argMultimap.getValue(PREFIX_PRIORITY).isPresent()
                ? ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get())
                : Priority.NONE;
        Venue venue = argMultimap.getValue(PREFIX_VENUE).isPresent()
                ? ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get())
                : new Venue("");
        RepeatType repeatType = argMultimap.getValue(PREFIX_REPEAT_TYPE).isPresent()
                ? ParserUtil.parseRepeatType(argMultimap.getValue(PREFIX_REPEAT_TYPE).get())
                : RepeatType.NONE;
        DateTime repeatUntilDateTime = argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).get())
                : endDateTime;

        if (!Event.isValidEventDateTime(startDateTime, endDateTime)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_VALUES, Event.MESSAGE_DATETIME_CONSTRAINTS));
        }

        Event event = new Event(UUID.randomUUID(), eventName, startDateTime, endDateTime, description, priority,
                venue, repeatType, repeatUntilDateTime);

        return new AddCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
