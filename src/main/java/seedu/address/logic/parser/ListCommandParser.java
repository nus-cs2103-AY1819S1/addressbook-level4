package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 * {@author jeffreyooi}
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmed = args.trim();

        ListCommand.ListCommandType listCommandType;

        switch (trimmed) {
        case ListCommand.COMMAND_PARAM_GROUP:
        case ListCommand.COMMAND_PARAM_GROUP_SHORT:
            listCommandType = ListCommand.ListCommandType.GROUP;
            break;
        case ListCommand.COMMAND_PARAM_PERSON:
        case ListCommand.COMMAND_PARAM_PERSON_SHORT:
            listCommandType = ListCommand.ListCommandType.PERSON;
            break;
        case ListCommand.COMMAND_PARAM_MEETING:
        case ListCommand.COMMAND_PARAM_MEETING_SHORT:
            listCommandType = ListCommand.ListCommandType.MEETING;
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand(listCommandType);
    }
}
