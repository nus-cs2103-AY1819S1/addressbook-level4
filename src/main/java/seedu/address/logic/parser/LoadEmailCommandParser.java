package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoadEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Subject;

//@@author EatOrBeEaten
/**
 * Parses input arguments and creates a new LoadEmailCommand object
 */
public class LoadEmailCommandParser implements Parser<LoadEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoadEmailCommand
     * and returns an LoadEmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadEmailCommand parse(String args) throws ParseException {
        try {
            Subject subject = ParserUtil.parseSubject(args);
            return new LoadEmailCommand(subject);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadEmailCommand.MESSAGE_USAGE), pe);
        }
    }
}
