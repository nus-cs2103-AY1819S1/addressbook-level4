package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MeetingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;

//@@author AyushChatto
/**
 * Parses input arguments and creates a new MeetingsCommand object.
 */
public class MeetingsCommandParser implements Parser<MeetingsCommand> {

    public MeetingsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        Meeting meeting;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MeetingsCommand.MESSAGE_USAGE);
            )
        }

        String paddedArgs = args + "0000";

        try {
             meeting =
        }
    }
}
