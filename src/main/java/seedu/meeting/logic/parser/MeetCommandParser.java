package seedu.meeting.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.stream.Stream;

import seedu.meeting.logic.commands.MeetCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.meeting.TimeStamp;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;

// @@author NyxF4ll
/**
 * Parses input arguments and creates a new MeetCommand Object
 */
public class MeetCommandParser implements Parser<MeetCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the MeetCommand
     * and returns an MeetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MeetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_NAME,
                PREFIX_LOCATION, PREFIX_TIMESTAMP);

        Group group;

        try {
            Title title = ParserUtil.parseTitle(argumentMultimap.getPreamble());
            group = new Group(title);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE), pe);
        }

        if (noPrefixesPresent(argumentMultimap, PREFIX_DESCRIPTION, PREFIX_NAME,
                PREFIX_LOCATION, PREFIX_TIMESTAMP)) {
            return new MeetCommand(group, null);
        }

        if (!arePrefixesPresent(argumentMultimap, PREFIX_DESCRIPTION, PREFIX_NAME,
                PREFIX_LOCATION, PREFIX_TIMESTAMP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argumentMultimap.getValue(PREFIX_NAME).get());
        TimeStamp time = ParserUtil.parseTimeStamp(argumentMultimap.getValue(PREFIX_TIMESTAMP).get());
        Address location = ParserUtil.parseAddress(argumentMultimap.getValue(PREFIX_LOCATION).get());
        Description description = ParserUtil.parseDescription(argumentMultimap.getValue(PREFIX_DESCRIPTION).get());

        Meeting meeting = new Meeting(title, time, location, description);

        return new MeetCommand(group, meeting);
    }

    /**
     * Returns true if all of the prefixes does not appear in the {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).noneMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    // @@author

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
