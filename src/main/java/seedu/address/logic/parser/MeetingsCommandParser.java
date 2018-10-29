package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MeetingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.SameMeetingDayPredicate;

//@@author AyushChatto
/**
 * Parses input arguments and creates a new MeetingsCommand object.
 */
public class MeetingsCommandParser implements Parser<MeetingsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MeetingsCommand
     * and returns an MeetingsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MeetingsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Meeting meeting;

        if (trimmedArgs.isEmpty()) {
            return new MeetingsCommand(new SameMeetingDayPredicate(new Meeting(Meeting.NO_MEETING)));
        }

        String paddedArgs = args + "0000";

        try {
            meeting = ParserUtil.parseMeeting(paddedArgs);
            return new MeetingsCommand(new SameMeetingDayPredicate(meeting));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingsCommand.MESSAGE_USAGE), pe);
        }
    }
}
