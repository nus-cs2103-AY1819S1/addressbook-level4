package seedu.meeting.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.meeting.logic.commands.DeleteGroupCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;


// @@author Derek-Hardy
/**
 * Parses input arguments and creates a new DeleteGroupCommand object
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGroupCommand
     * and returns an DeleteGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Title title = ParserUtil.parseTitle(args);
            Group group = new Group(title);
            return new DeleteGroupCommand(group);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE), pe);
        }
    }
}
