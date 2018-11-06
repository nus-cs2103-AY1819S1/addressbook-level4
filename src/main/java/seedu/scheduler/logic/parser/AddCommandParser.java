package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;

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
                        PREFIX_END_DATE_TIME, PREFIX_DESCRIPTION, PREFIX_VENUE,
                        PREFIX_REPEAT_TYPE, PREFIX_REPEAT_UNTIL_DATE_TIME, PREFIX_TAG, PREFIX_EVENT_REMINDER_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get());

        DateTime startDateTime = argMultimap.getValue(PREFIX_START_DATE_TIME).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE_TIME).get())
                : new DateTime(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1));
        DateTime endDateTime = argMultimap.getValue(PREFIX_END_DATE_TIME).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE_TIME).get())
                : new DateTime(startDateTime.value.plusHours(1));
        Description description = argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()
                ? ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get())
                : new Description("");
        Venue venue = argMultimap.getValue(PREFIX_VENUE).isPresent()
                ? ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get())
                : new Venue("");
        RepeatType repeatType = argMultimap.getValue(PREFIX_REPEAT_TYPE).isPresent()
                ? ParserUtil.parseRepeatType(argMultimap.getValue(PREFIX_REPEAT_TYPE).get())
                : RepeatType.NONE;
        DateTime repeatUntilDateTime = argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).isPresent()
                ? ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_REPEAT_UNTIL_DATE_TIME).get())
                : endDateTime;
        Set<Tag> tags = argMultimap.getValue(PREFIX_TAG).isPresent()
                ? ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG))
                : Collections.emptySet();
        ReminderDurationList reminderDurationList = ParserUtil.parseReminderDurations(
                argMultimap.getAllValues(PREFIX_EVENT_REMINDER_DURATION));

        if (!Event.isValidEventDateTime(startDateTime, endDateTime)) {
            throw new ParseException(Event.MESSAGE_START_END_DATETIME_CONSTRAINTS);
        }

        if (!Event.isValidEventDateTime(endDateTime, repeatUntilDateTime)) {
            throw new ParseException(Event.MESSAGE_END_REPEAT_UNTIL_DATETIME_CONSTRAINTS);
        }

        Event event = new Event(UUID.randomUUID(), eventName, startDateTime, endDateTime, description,
                venue, repeatType, repeatUntilDateTime, tags, reminderDurationList);

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
