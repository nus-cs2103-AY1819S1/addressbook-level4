package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.OpenEndedTestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.topic.Topic;

/**
 * Parses input arguments and creates a new OpenEndedTestCommand object
 */
public class OpenEndedTestCommandParser implements Parser<OpenEndedTestCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the OpenEndedTestCommand
     * and returns an estCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenEndedTestCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenEndedTestCommand.MESSAGE_USAGE));
        }

        Topic tag = ParserUtil.parseTopic(args);

        return new OpenEndedTestCommand(tag);
    }
}
