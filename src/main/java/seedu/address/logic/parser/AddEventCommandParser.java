package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    public static final String MESSAGE_INVALID_START_END_TIME = "Invalid start and end time! %1$s %2$s";

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EVENT_DESCRIPTION, PREFIX_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_ADDRESS, PREFIX_INDEX, PREFIX_TAG);

        // check for mandatory fields, and that no other data is entered between the command and first argument prefix
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EVENT_DESCRIPTION, PREFIX_DATE, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_ADDRESS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        EventDescription eventDesc =
                ParserUtil.parseEventDescription(argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).get());
        EventDate eventDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_DATE).get());
        EventTime eventStartTime = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_START_TIME).get());
        EventTime eventEndTime = ParserUtil.parseEventTime(argMultimap.getValue(PREFIX_END_TIME).get());
        EventAddress eventAddress = ParserUtil.parseEventAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Index> contactIndexList = ParserUtil.parseIndices(argMultimap.getAllValues(PREFIX_INDEX));
        Set<Tag> eventTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (eventEndTime.compareTo(eventStartTime) < 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_START_END_TIME, eventStartTime.toString(),
                    eventEndTime.toString()));
        }

        Event newEvent = new Event(eventName, eventDesc, eventDate, eventStartTime, eventEndTime,
                eventAddress, eventTagList);
        return new AddEventCommand(newEvent, contactIndexList);
    }
}
