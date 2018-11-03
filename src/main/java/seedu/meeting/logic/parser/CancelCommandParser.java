package seedu.meeting.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.meeting.logic.commands.CancelCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;

// @@author NyxF4ll
/**
 * Parses input arguments and creates a new CancelCommand Object
 */
public class CancelCommandParser implements Parser<CancelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CancelCommand
     * and returns an CancelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CancelCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);

        Group group;

        try {
            Title title = ParserUtil.parseTitle(argumentMultimap.getPreamble());
            group = new Group(title);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelCommand.MESSAGE_USAGE), pe);
        }

        return new CancelCommand(group);
    }
}
